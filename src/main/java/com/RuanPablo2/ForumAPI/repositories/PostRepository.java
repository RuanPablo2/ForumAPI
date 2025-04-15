package com.RuanPablo2.ForumAPI.repositories;

import com.RuanPablo2.ForumAPI.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Page<Post> findByBodyContainingIgnoreCaseAndActiveTrue(String query, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByAuthorIdAndActiveTrue(String authorId, Pageable pageable);
    Page<Post> findByActiveTrue(Pageable pageable);
}