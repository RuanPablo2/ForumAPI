package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.dtos.request.ReportRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.ReportResponseDTO;
import com.RuanPablo2.ForumAPI.exception.ResourceNotFoundException;
import com.RuanPablo2.ForumAPI.model.Comment;
import com.RuanPablo2.ForumAPI.model.Post;
import com.RuanPablo2.ForumAPI.model.Report;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.model.enums.ReportType;
import com.RuanPablo2.ForumAPI.repositories.CommentRepository;
import com.RuanPablo2.ForumAPI.repositories.PostRepository;
import com.RuanPablo2.ForumAPI.repositories.ReportRepository;
import com.RuanPablo2.ForumAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModerationService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public ReportResponseDTO report(ReportRequestDTO dto) {
        User reporter = userService.getAuthenticatedUser();

        Report report = new Report(dto.getTargetId(), dto.getType(), dto.getReason(), reporter);
        report = reportRepository.save(report);

        return new ReportResponseDTO(report.getId(), report.getTargetId(), report.getType(), report.getReason(),
                reporter.getEmail(), report.getCreatedAt());
    }

    public void banContent(String targetId, ReportType type) {
        switch (type) {
            case POST -> {
                Post post = postRepository.findById(targetId)
                        .orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));
                post.setActive(false);
                postRepository.save(post);
            }
            case COMMENT -> {
                Comment comment = commentRepository.findById(targetId)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment not found", "CMT-404"));
                comment.setActive(false);
                commentRepository.save(comment);
            }
            case USER -> {
                User user = userRepository.findById(targetId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found", "USR-404"));
                user.setActive(false);
                userRepository.save(user);
            }
        }
    }

    public List<ReportResponseDTO> getReportsByType(ReportType type) {
        return reportRepository.findByType(type).stream()
                .map(r -> new ReportResponseDTO(r.getId(), r.getTargetId(), r.getType(), r.getReason(),
                        r.getReporter().getEmail(), r.getCreatedAt()))
                .collect(Collectors.toList());
    }
}