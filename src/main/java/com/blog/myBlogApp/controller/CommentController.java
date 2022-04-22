package com.blog.myBlogApp.controller;

import com.blog.myBlogApp.payload.ApiOpResponse;
import com.blog.myBlogApp.payload.CommentDTO;
import com.blog.myBlogApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPost(@PathVariable Long postId){
        return commentService.getPostComments(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentByPost(@PathVariable Long postId,
                                                       @PathVariable Long commentId){
        return new ResponseEntity<>(commentService.getCommentByPost(postId, commentId), HttpStatus.OK);
    }


    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> editPostComment(@PathVariable Long postId,
                                                           @PathVariable Long commentId,
                                                           @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDTO), HttpStatus.OK);

    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deletePostComment(@PathVariable Long postId, @PathVariable Long commentId){
        commentService.deletePostComment(postId, commentId);
        return ResponseEntity.ok("Comment deleted.");
    }

    @DeleteMapping("/posts/{postId}/comments")
    public ResponseEntity<String> deleteAllPostComment(@PathVariable Long postId){
        commentService.deleteAllPostComment(postId);
        return ResponseEntity.ok("All post comment deleted.");
    }
}
