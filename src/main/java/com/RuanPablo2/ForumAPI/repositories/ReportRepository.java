package com.RuanPablo2.ForumAPI.repositories;

import com.RuanPablo2.ForumAPI.model.Report;
import com.RuanPablo2.ForumAPI.model.enums.ReportType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<Report, String> {
    List<Report> findByTypeAndTargetId(ReportType type, String targetId);
    List<Report> findByType(ReportType type);
}