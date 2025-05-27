package com.fixmystreet.fixmystreet.repository;

import com.fixmystreet.fixmystreet.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
