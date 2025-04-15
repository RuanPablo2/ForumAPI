package com.RuanPablo2.ForumAPI.dtos.request;

import com.RuanPablo2.ForumAPI.model.enums.ReportType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequestDTO {
    private String targetId;
    private ReportType type;
    private String reason;
}