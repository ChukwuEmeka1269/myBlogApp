package com.blog.myBlogApp.service;

import com.blog.myBlogApp.payload.PostDTO;
import com.blog.myBlogApp.payload.PostResponse;

import java.util.List;


public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO getPostById(Long postId);
    PostDTO updatePost(PostDTO postDTO, Long postId);
    void deletePost(Long postId);
}
