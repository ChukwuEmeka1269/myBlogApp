package com.blog.myBlogApp;


import com.blog.myBlogApp.payload.PostDTO;

import com.blog.myBlogApp.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Rollback(false)
public class PostRepositoryTest {


    private PostService postService;

    @Autowired
    public PostRepositoryTest(PostService postService) {
        this.postService = postService;
    }

    @Test
    public void createPostTest(){
        PostDTO post = new PostDTO();
        post.setTitle("Testing");
        post.setDescription("Unit Testing my post repository");
        post.setContent("I hope this works well. Unit testing....");

        postService.createPost(post);

        PostDTO postById = postService.getPostById(post.getId());

        assertThat(postById.equals(post));
    }
}
