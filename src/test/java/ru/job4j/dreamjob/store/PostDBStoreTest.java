package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostDBStore;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
class PostDBStoreTest {
    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "description", true, new City());
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "description", true, new City());
        store.add(post);
        post.setName("New name");
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenAddTwoPostsThenGetAll() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post1 = new Post(0, "Java", "description", true, new City());
        Post post2 = new Post(1, "Job", "description", true, new City());
        store.add(post1);
        store.add(post2);
        List<Post> posts = store.findAll();
        assertThat(posts).contains(post1, post2);
    }
}