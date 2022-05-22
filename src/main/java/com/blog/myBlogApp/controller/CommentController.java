package com.blog.myBlogApp.controller;

import com.blog.myBlogApp.payload.ApiOpResponse;
import com.blog.myBlogApp.payload.CommentDTO;
import com.blog.myBlogApp.service.CommentService;
import com.blog.myBlogApp.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@Api(value = "REST API CRUD operations for Comments")
@RestController
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "REST API for users to create COMMENT on a POST")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "REST API to get all COMMENT on a POST")
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPost(@PathVariable Long postId){
        return commentService.getPostComments(postId);
    }

    @ApiOperation(value = "REST API for to get a COMMENT on a POST")
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentByPost(@PathVariable Long postId,
                                                       @PathVariable Long commentId){
        return new ResponseEntity<>(commentService.getCommentByPost(postId, commentId), HttpStatus.OK);
    }


    @ApiOperation(value = "REST API to edit a COMMENT on a POST")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> editPostComment(@PathVariable Long postId,
                                                           @PathVariable Long commentId,
                                                            @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDTO), HttpStatus.OK);

    }

    @ApiOperation(value ="REST API to delete a COMMENT from a POST")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiOpResponse> deletePostComment(@PathVariable Long postId, @PathVariable Long commentId){
        ApiOpResponse apiOpResponse = new ApiOpResponse();
        apiOpResponse.setMessage(AppConstants.DELETE_COMMENT_MESSAGE);

        commentService.deletePostComment(postId, commentId);
        return  new ResponseEntity<>(apiOpResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "REST API to delete all COMMENT on a POST")
    @DeleteMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiOpResponse> deleteAllPostComment(@PathVariable Long postId){
        ApiOpResponse apiOpResponse = new ApiOpResponse();
        apiOpResponse.setMessage(AppConstants.DELETE_ALL_COMMENT_MESSAGE);

        commentService.deleteAllPostComment(postId);

        return new ResponseEntity<>(apiOpResponse, HttpStatus.OK);
    }
}
