package com.RuanPablo2.ForumAPI.repositories;

import com.RuanPablo2.ForumAPI.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

}