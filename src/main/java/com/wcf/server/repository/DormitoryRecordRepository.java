package com.wcf.server.repository;

import com.wcf.server.model.DormitoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DormitoryRecordRepository extends JpaRepository<DormitoryRecord, Long> {
    DormitoryRecord findByDormitoryIdAndBillDate(Long dormitoryId, Date billDate);
}
