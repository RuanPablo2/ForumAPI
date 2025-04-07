package com.RuanPablo2.ForumAPI.repositories;

import com.RuanPablo2.ForumAPI.model.PostLike;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostLikeRepository extends MongoRepository<PostLike, String> {
    Optional<PostLike> findByPostIdAndUserId(String postId, String userId);
    void deleteByPostIdAndUserId(String postId, String userId);
    long countByPostId(String postId);
}