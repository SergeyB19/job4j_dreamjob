package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.controller.PostController;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PostControllerTest {
    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "description"),
                new Post(2, "New post", "description")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model, mock(HttpSession.class));
        verify(model).addAttribute("posts", posts);
        verify(model).addAttribute("city", cityService.getAllCities());
        assertEquals(page, "posts");
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "New post", "description");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input, mock(Model.class), mock(HttpSession.class));
        verify(postService).add(input);
        assertEquals(page, "redirect:/posts");
    }

    @Test
    public void whenUpdatePost() {
        Post oldPost = new Post(1, "New post", "description");
        Post newPost = new Post(1, "New post", "description");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        postController.createPost(oldPost, mock(Model.class), mock(HttpSession.class));
        String page = postController.updatePost(newPost);
        verify(postService).add(oldPost);
        verify(postService).update(newPost);
        assertEquals(page, "redirect:/posts");
    }

    @Test
    public void whenGetFormUpdatePost() {
        Post input = new Post(1, "New post", "description");
        List<Post> posts = List.of(input);
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        int id = 0;
        when(postService.findById(id)).thenReturn(posts.get(id));
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formUpdatePost(model, id, mock(HttpSession.class));
        verify(model).addAttribute("post", input);
        assertEquals(page, "updatePost");
    }

    @Test
    public void whenGetFormAddPost() {
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formAddPost(mock(Model.class), mock(HttpSession.class));
        assertEquals(page, "addPost");
    }
}