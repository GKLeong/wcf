package com.wcf.server.repository;

import com.wcf.server.model.ScrapStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapStatisticsRepository extends JpaRepository<ScrapStatistics, Long> {
}
