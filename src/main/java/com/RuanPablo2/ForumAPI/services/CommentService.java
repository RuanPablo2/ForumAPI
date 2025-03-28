package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.model.Comment;
import com.RuanPablo2.ForumAPI.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public Page<Comment> getCommentsByPost(String postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return commentRepository.findByPostId(postId, pageable);
    }
}