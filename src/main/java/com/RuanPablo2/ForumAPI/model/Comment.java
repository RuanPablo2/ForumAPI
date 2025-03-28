package com.RuanPablo2.ForumAPI.model;

import com.RuanPablo2.ForumAPI.dtos.response.AuthorResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String text;
    private Date date;

    @DBRef
    private AuthorResponseDTO author;

    private String postId;
}