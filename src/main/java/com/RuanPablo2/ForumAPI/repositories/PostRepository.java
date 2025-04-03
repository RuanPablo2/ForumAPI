package com.RuanPablo2.ForumAPI.repositories;

import com.RuanPablo2.ForumAPI.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findByBodyContainingIgnoreCase(String body);
}