package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.model.LeaveRecord;
import com.wcf.server.repository.LeaveRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LeaveRecordService {
    private final LeaveRecordRepository leaveRecordRepository;

    @Autowired
    public LeaveRecordService(LeaveRecordRepository leaveRecordRepository) {
        this.leaveRecordRepository = leaveRecordRepository;
    }

    public List<LeaveRecord> findAll() {
        return leaveRecordRepository.findAll();
    }

    public LeaveRecord add(Long userId, Date startDate, Date endDate, String reason) {
        LeaveRecord leaveRecord = new LeaveRecord();
        leaveRecord.setUserId(userId);
        leaveRecord.setStartDate(startDate);
        leaveRecord.setEndDate(endDate);
        leaveRecord.setReason(reason);
        return leaveRecordRepository.save(leaveRecord);
    }

    public LeaveRecord update(Long id, Date startDate, Date endDate, String reason) {
        LeaveRecord leaveRecord = leaveRecordRepository.findById(id).orElseThrow(() -> new BizException("记录不存在: " + id));
        leaveRecord.setStartDate(startDate);
        leaveRecord.setEndDate(endDate);
        leaveRecord.setReason(reason);
        return leaveRecordRepository.save(leaveRecord);
    }
}
