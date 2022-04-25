package com.blog.myBlogApp.controller;

import com.blog.myBlogApp.payload.ApiOpResponse;
import com.blog.myBlogApp.payload.PostDTO;
import com.blog.myBlogApp.payload.PostResponse;
import com.blog.myBlogApp.service.PostService;
import com.blog.myBlogApp.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<PostResponse> getPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> editPost(@PathVariable Long postId, @Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.updatePost(postDTO, postId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiOpResponse> deletePost(@PathVariable Long postId){
        ApiOpResponse apiOpResponse = new ApiOpResponse();
        apiOpResponse.setMessage(AppConstants.DELETE_POST_MESSAGE);
        postService.deletePost(postId);
        return  new ResponseEntity<>(apiOpResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiOpResponse> deleteAllPost(){
        postService.deletePosts();
        ApiOpResponse message = new ApiOpResponse();
        message.setMessage(AppConstants.DELETE_ALL_POST_MESSAGE);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
