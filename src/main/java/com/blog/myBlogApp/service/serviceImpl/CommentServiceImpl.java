package com.blog.myBlogApp.service.serviceImpl;

import com.blog.myBlogApp.exception.BlogApiException;
import com.blog.myBlogApp.exception.ResourceNotFoundException;
import com.blog.myBlogApp.model.Comment;
import com.blog.myBlogApp.model.Post;
import com.blog.myBlogApp.payload.CommentDTO;
import com.blog.myBlogApp.repository.CommentRepository;
import com.blog.myBlogApp.repository.PostRepository;
import com.blog.myBlogApp.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Comment comment = mapCommentDTOToComment(commentDTO);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        comment.setPost(post);
        commentRepository.save(comment);

        return mapCommentToCommentDTO(comment);
    }

    @Override
    public List<CommentDTO> getPostComments(Long postId) {
        List<Comment> postComments = commentRepository.findByPostId(postId);
        return postComments.stream().map(this::mapCommentToCommentDTO).collect(Collectors.toList());

    }

    @Override
    public CommentDTO getCommentByPost(Long id, Long commentId) {
        Comment comment = findCommentByPost(id, commentId);

        return mapCommentToCommentDTO(comment);

    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Comment comment = findCommentByPost(postId, commentId);

        comment.setBody(commentDTO.getBody());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());

        Comment updatedComment = commentRepository.save(comment);

        return mapCommentToCommentDTO(updatedComment);
    }

    @Override
    public void deletePostComment(Long postId, Long commentId) {
        Comment commentOnPost = findCommentByPost(postId, commentId);
        commentRepository.delete(commentOnPost);
    }

    @Override
    public void deleteAllPostComment(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        comments.forEach(commentRepository::delete);
    }

    /*---------------------------Private Methods ------------------------------------------*/

    private Comment findCommentByPost(Long id, Long commentId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", id));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
        }

        return  comment;
    }

    private CommentDTO mapCommentToCommentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setBody(comment.getBody());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setName(comment.getName());

        return commentDTO;
    }

    private Comment mapCommentDTOToComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());

        return comment;
    }
}
