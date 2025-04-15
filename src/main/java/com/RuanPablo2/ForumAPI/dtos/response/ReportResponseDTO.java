package com.RuanPablo2.ForumAPI.dtos.response;

import com.RuanPablo2.ForumAPI.model.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class ReportResponseDTO {
    private String id;
    private String targetId;
    private ReportType type;
    private String reason;
    private String reporterEmail;
    private Instant createdAt;
}