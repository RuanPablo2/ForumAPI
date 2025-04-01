package com.RuanPablo2.ForumAPI.model;

import com.RuanPablo2.ForumAPI.dtos.response.AuthorResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String text;
    private Instant date;
    private AuthorResponseDTO author;
    private String postId;
}