package com.RuanPablo2.ForumAPI.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "post_likes")
public class PostLike implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String postId;
    private String userId;
    private Instant likedAt;

    public PostLike(String postId, String userId) {
        this.postId = postId;
        this.userId = userId;
        this.likedAt = Instant.now();
    }
}