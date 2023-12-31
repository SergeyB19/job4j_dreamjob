package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;


@Controller
public class PostController {

    private final PostService postService;
    private final CityService cityService;

    public PostController(PostService service, CityService cityService) {
        this.postService = service;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        SessionControl.getUserSession(model, session);
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("city", cityService.getAllCities());
        return "posts";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post, Model model, HttpSession session) {
        postService.add(post);
        SessionControl.getUserSession(model, session);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id, HttpSession session) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        SessionControl.getUserSession(model, session);
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        postService.update(post);
        return "redirect:/posts";
    }

    @GetMapping("/formAddPost")
    public String formAddPost(Model model, HttpSession session) {
        model.addAttribute("cities", cityService.getAllCities());
        SessionControl.getUserSession(model, session);
        return "addPost";
    }
}