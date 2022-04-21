package com.blog.myBlogApp.repository;

import com.blog.myBlogApp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
