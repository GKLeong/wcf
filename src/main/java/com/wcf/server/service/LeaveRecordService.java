package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.model.LeaveRecord;
import com.wcf.server.model.User;
import com.wcf.server.repository.LeaveRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveRecordService {
    private final LeaveRecordRepository leaveRecordRepository;
    private final UserService userService;

    @Autowired
    public LeaveRecordService(LeaveRecordRepository leaveRecordRepository,
                              UserService userService) {
        this.leaveRecordRepository = leaveRecordRepository;
        this.userService = userService;
    }

    public List<LeaveRecord> findAll() {
        List<LeaveRecord> leaveRecordList = leaveRecordRepository.findAll();
        if (leaveRecordList.size() > 0) {
            Map<Long, String> userMap = userService.findAll().stream().collect(Collectors.toMap(User::getId, User::getName));
            leaveRecordList.forEach(data -> {
                data.setUser(userMap.get(data.getUserId()));
            });
        }
        return leaveRecordList;
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
