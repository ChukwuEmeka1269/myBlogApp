package com.blog.myBlogApp.controller;

import com.blog.myBlogApp.payload.ApiOpResponse;
import com.blog.myBlogApp.payload.PostDTO;

import com.blog.myBlogApp.payload.PostResponse;
import com.blog.myBlogApp.service.PostService;
import com.blog.myBlogApp.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(value = "REST API for Post CRUD operations")
@RestController
@RequestMapping()
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation(value="REST API for Admin user to create a Post")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }


    @ApiOperation(value = "REST API to get all Post")
    @GetMapping("/api/v1/posts")
    public ResponseEntity<PostResponse> getPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @ApiOperation(value="REST API to get POST by ID")
    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity<PostDTO> getPostByIdV1(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }


    @ApiOperation(value = "REST API for Admin to edit a Post")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/{postId}")
    public ResponseEntity<PostDTO> editPost(@PathVariable Long postId, @Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.updatePost(postDTO, postId), HttpStatus.OK);
    }

    @ApiOperation(value="REST API for Admin to delete a Post by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{postId}")
    public ResponseEntity<ApiOpResponse> deletePost(@PathVariable Long postId){
        ApiOpResponse apiOpResponse = new ApiOpResponse();
        apiOpResponse.setMessage(AppConstants.DELETE_POST_MESSAGE);
        postService.deletePost(postId);
        return  new ResponseEntity<>(apiOpResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "REST API for Admin to delete All Posts")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts")
    public ResponseEntity<ApiOpResponse> deleteAllPost(){
        postService.deletePosts();
        ApiOpResponse message = new ApiOpResponse();
        message.setMessage(AppConstants.DELETE_ALL_POST_MESSAGE);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
