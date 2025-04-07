package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.exception.ResourceNotFoundException;
import com.RuanPablo2.ForumAPI.model.PostLike;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.repositories.PostLikeRepository;
import com.RuanPablo2.ForumAPI.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public void likePost(String postId) {
        User user = userService.getAuthenticatedUser();

        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));

        boolean alreadyLiked = postLikeRepository.findByPostIdAndUserId(postId, user.getId()).isPresent();

        if (!alreadyLiked) {
            postLikeRepository.save(new PostLike(postId, user.getId()));
        }
    }

    public void unlikePost(String postId) {
        User user = userService.getAuthenticatedUser();

        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));

        postLikeRepository.deleteByPostIdAndUserId(postId, user.getId());
    }

    public long countLikes(String postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));

        return postLikeRepository.countByPostId(postId);
    }
}