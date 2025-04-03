package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.dtos.request.PostRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.AuthorResponseDTO;
import com.RuanPablo2.ForumAPI.dtos.response.PostResponseDTO;
import com.RuanPablo2.ForumAPI.exception.ResourceNotFoundException;
import com.RuanPablo2.ForumAPI.exception.UnauthorizedException;
import com.RuanPablo2.ForumAPI.model.Post;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public PostResponseDTO createPost(PostRequestDTO dto) {
        User loggedUser = userService.getAuthenticatedUser();

        Post post = new Post(null, Instant.now(), dto.getBody(), new AuthorResponseDTO(loggedUser), 0);
        post = postRepository.save(post);

        return new PostResponseDTO(post);
    }

    public Post findById(String id){
        Post result = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));
        return result;
    }

    public List<PostResponseDTO> searchPosts(String query) {
        List<Post> posts = postRepository.findByBodyContainingIgnoreCase(query);
        return posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());
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
            throw new UnauthorizedException("You do not have permission to delete this post.", "AUTH-002");
        }

        postRepository.delete(post);
    }
}