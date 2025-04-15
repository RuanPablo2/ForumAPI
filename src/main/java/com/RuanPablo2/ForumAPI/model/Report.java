package com.RuanPablo2.ForumAPI.model;

import com.RuanPablo2.ForumAPI.model.enums.ReportType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "reports")
public class Report {

    @Id
    private String id;

    private String targetId; // Pode ser postId, commentId ou userId
    private ReportType type; // Enum: POST, COMMENT, USER
    private String reason;
    private Instant createdAt;

    @DBRef
    private User reporter;

    public Report(String targetId, ReportType type, String reason, User reporter) {
        this.targetId = targetId;
        this.type = type;
        this.reason = reason;
        this.reporter = reporter;
        this.createdAt = Instant.now();
    }
}