package com.wcf.server.service;

import com.wcf.server.model.DormitoryRecord;
import com.wcf.server.repository.DormitoryRecordRepository;
import com.wcf.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class DormitoryRecordService {
    private final DormitoryRecordRepository dormitoryRecordRepository;

    @Autowired
    public DormitoryRecordService(DormitoryRecordRepository dormitoryRecordRepository) {
        this.dormitoryRecordRepository = dormitoryRecordRepository;
    }

    public DormitoryRecord add(Long dormitoryId, Date date, BigDecimal water, BigDecimal electricity) {
        DormitoryRecord dormitoryRecord = new DormitoryRecord();
        dormitoryRecord.setDormitoryId(dormitoryId);
        dormitoryRecord.setDate(date);
        dormitoryRecord.setWater(water);
        dormitoryRecord.setElectricity(electricity);

        return dormitoryRecordRepository.save(dormitoryRecord);
    }

    public List<DormitoryRecord> findAll() {
        return dormitoryRecordRepository.findAll();
    }
}
