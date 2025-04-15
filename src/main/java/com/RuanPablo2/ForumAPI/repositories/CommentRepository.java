package com.RuanPablo2.ForumAPI.repositories;

import com.RuanPablo2.ForumAPI.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
    Page<Comment> findByPostIdAndActiveTrue(String postId, Pageable pageable);
    Page<Comment> findByAuthorIdAndActiveTrue(String authorId, Pageable pageable);
}