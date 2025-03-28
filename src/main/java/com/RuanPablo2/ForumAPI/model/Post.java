package com.RuanPablo2.ForumAPI.model;

import com.RuanPablo2.ForumAPI.dtos.response.AuthorResponseDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    public String id;
    private Date date;
    private String title;
    private String body;
    private AuthorResponseDTO author;
    private int totalComments;

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