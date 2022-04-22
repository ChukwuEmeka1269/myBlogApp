package com.blog.myBlogApp.service;

import com.blog.myBlogApp.payload.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(Long postId, CommentDTO commentDTO);
    List<CommentDTO> getPostComments(Long postId);
    CommentDTO getCommentByPost(Long id, Long commentId);

    CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO);

    void deletePostComment(Long postId, Long commentId);

    void deleteAllPostComment(Long postId);
}
