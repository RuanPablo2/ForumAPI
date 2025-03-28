package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.exception.ResourceNotFoundException;
import com.RuanPablo2.ForumAPI.model.Post;
import com.RuanPablo2.ForumAPI.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post findById(String id){
        Post result = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));
        return result;
    }
}