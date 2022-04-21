package com.blog.myBlogApp.service;

import com.blog.myBlogApp.payload.PostDTO;

import java.util.List;


public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getPosts();
    PostDTO getPostById(Long postId);
    PostDTO updatePost(PostDTO postDTO, Long postId);
    void deletePost(Long postId);
}
