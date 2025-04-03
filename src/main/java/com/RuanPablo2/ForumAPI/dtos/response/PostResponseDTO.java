package com.RuanPablo2.ForumAPI.dtos.response;

import com.RuanPablo2.ForumAPI.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Instant date;
    private String body;
    private AuthorResponseDTO author;
    private int totalComments;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.date = post.getDate();
        this.body = post.getBody();
        this.author = post.getAuthor();
        this.totalComments = post.getTotalComments();
    }
}