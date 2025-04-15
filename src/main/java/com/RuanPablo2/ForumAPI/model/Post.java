package com.RuanPablo2.ForumAPI.model;

import com.RuanPablo2.ForumAPI.dtos.response.AuthorResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "posts")
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    public String id;
    private Instant date;
    private String body;
    private AuthorResponseDTO author;
    private int totalComments;
    private boolean active = true;

    public Post(String id, Instant date, String body, AuthorResponseDTO author, int totalComments, boolean active) {
        this.id = id;
        this.date = date;
        this.body = body;
        this.author = author;
        this.totalComments = totalComments;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}