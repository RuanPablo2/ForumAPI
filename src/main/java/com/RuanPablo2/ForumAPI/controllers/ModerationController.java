package com.RuanPablo2.ForumAPI.controllers;

import com.RuanPablo2.ForumAPI.dtos.request.ReportRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.ReportResponseDTO;
import com.RuanPablo2.ForumAPI.model.enums.ReportType;
import com.RuanPablo2.ForumAPI.services.ModerationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderation")
public class ModerationController {

    @Autowired
    private ModerationService moderationService;

    @PostMapping("/reports")
    public ResponseEntity<ReportResponseDTO> reportContent(@RequestBody @Valid ReportRequestDTO dto) {
        ReportResponseDTO report = moderationService.report(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/ban")
    public ResponseEntity<Void> banContent(@RequestParam String targetId, @RequestParam ReportType type) {
        moderationService.banContent(targetId, type);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reports")
    public ResponseEntity<List<ReportResponseDTO>> getReportsByType(@RequestParam ReportType type) {
        List<ReportResponseDTO> reports = moderationService.getReportsByType(type);
        return ResponseEntity.ok(reports);
    }
}