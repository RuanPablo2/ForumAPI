package com.RuanPablo2.ForumAPI.dtos.response;

import com.RuanPablo2.ForumAPI.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private String text;
    private String date;
    private AuthorResponseDTO author;

    public CommentResponseDTO(Comment comment) {
        this.text = comment.getText();
        this.date = formatDate(comment.getDate()); // Formata antes de retornar
        this.author = comment.getAuthor();
    }

    private String formatDate(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZoneOffset.UTC);
        return formatter.format(instant);
    }
}