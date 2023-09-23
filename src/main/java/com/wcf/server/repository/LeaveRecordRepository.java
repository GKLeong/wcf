package com.wcf.server.repository;

import com.wcf.server.model.LeaveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRecordRepository extends JpaRepository<LeaveRecord, Long> {
}
