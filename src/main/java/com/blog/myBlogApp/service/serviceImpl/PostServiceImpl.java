package com.blog.myBlogApp.service.serviceImpl;

import com.blog.myBlogApp.exception.ResourceNotFoundException;
import com.blog.myBlogApp.model.Post;
import com.blog.myBlogApp.payload.PostDTO;
import com.blog.myBlogApp.payload.PostResponse;
import com.blog.myBlogApp.repository.PostRepository;
import com.blog.myBlogApp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = mapPostDTOToPost(postDTO);
        postRepository.save(post);
        return mapPostToPostDTO(post);
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortDir).ascending()
                : Sort.by(sortDir).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> postPages = postRepository.findAll(pageable);

        List<Post> pageContent = postPages.getContent();

        List<PostDTO> content = pageContent.stream().map(this::mapPostToPostDTO).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContents(content);
        postResponse.setPageNo(postPages.getNumber());
        postResponse.setPageSize(postPages.getSize());
        postResponse.setTotalElements(postPages.getTotalElements());
        postResponse.setTotalPages(postPages.getTotalPages());
        postResponse.setLast(postPages.isLast());

        return postResponse;

    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        return mapPostToPostDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatedPost = postRepository.save(post);
        return mapPostToPostDTO(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        postRepository.delete(post);
    }


    private PostDTO mapPostToPostDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());

        return postDTO;
    }

    private Post mapPostDTOToPost(PostDTO postDTO){
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        return post;
    }


}
