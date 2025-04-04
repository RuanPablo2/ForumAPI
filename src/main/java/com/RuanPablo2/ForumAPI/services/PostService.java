package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.dtos.request.PostRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.AuthorResponseDTO;
import com.RuanPablo2.ForumAPI.dtos.response.PageResponseDTO;
import com.RuanPablo2.ForumAPI.dtos.response.PostResponseDTO;
import com.RuanPablo2.ForumAPI.exception.BusinessException;
import com.RuanPablo2.ForumAPI.exception.ResourceNotFoundException;
import com.RuanPablo2.ForumAPI.exception.UnauthorizedException;
import com.RuanPablo2.ForumAPI.model.Post;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public Page<PostResponseDTO> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostResponseDTO::new);
    }

    public PostResponseDTO createPost(PostRequestDTO dto) {
        User loggedUser = userService.getAuthenticatedUser();

        if (dto.getBody() == null || dto.getBody().trim().isEmpty()) {
            throw new BusinessException("Post body cannot be empty or contain only spaces.", "PST-001");
        }

        Post post = new Post(null, Instant.now(), dto.getBody().trim(), new AuthorResponseDTO(loggedUser), 0);
        post = postRepository.save(post);

        return new PostResponseDTO(post);
    }

    public Post findById(String id){
        Post result = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));
        return result;
    }

    public Page<PostResponseDTO> searchPosts(String query, Pageable pageable) {
        Page<Post> posts = postRepository.findByBodyContainingIgnoreCase(query, pageable);
        return posts.map(PostResponseDTO::new);
    }

    public PostResponseDTO updatePost(String postId, PostRequestDTO dto) {
        User loggedUser = userService.getAuthenticatedUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));

        if (!post.getAuthor().getId().equals(loggedUser.getId())) {
            throw new UnauthorizedException("You do not have permission to edit this post.", "AUTH-002");
        }

        post.setBody(dto.getBody());
        post = postRepository.save(post);

        return new PostResponseDTO(post);
    }

    public void deletePost(String postId) {
        User loggedUser = userService.getAuthenticatedUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));

        if (!post.getAuthor().getId().equals(loggedUser.getId())) {
            throw new UnauthorizedException("You do not have permission to delete this post.", "AUTH-003");
        }

        postRepository.delete(post);
    }

}