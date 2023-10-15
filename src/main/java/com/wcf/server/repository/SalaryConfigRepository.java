package com.wcf.server.repository;

import com.wcf.server.model.SalaryConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryConfigRepository extends JpaRepository<SalaryConfig, Long> {
}
