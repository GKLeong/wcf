package com.wcf.server.repository;

import com.wcf.server.model.DormitoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DormitoryRecordRepository extends JpaRepository<DormitoryRecord, Long> {
}
