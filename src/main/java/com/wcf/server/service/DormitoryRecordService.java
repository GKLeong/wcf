package com.wcf.server.service;

import com.wcf.server.model.Dormitory;
import com.wcf.server.model.DormitoryRecord;
import com.wcf.server.repository.DormitoryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wcf.server.utils.DateUtils.generateBillDate;

@Service
public class DormitoryRecordService {
    private final DormitoryRecordRepository dormitoryRecordRepository;
    private final DormitoryService dormitoryService;

    @Autowired
    public DormitoryRecordService(DormitoryRecordRepository dormitoryRecordRepository,
                                  DormitoryService dormitoryService) {
        this.dormitoryRecordRepository = dormitoryRecordRepository;
        this.dormitoryService = dormitoryService;
    }

    public DormitoryRecord add(Long dormitoryId, Date date, BigDecimal water, BigDecimal electricity) {
        DormitoryRecord dormitoryRecord = new DormitoryRecord();
        dormitoryRecord.setDormitoryId(dormitoryId);
        dormitoryRecord.setBillDate(generateBillDate(date));
        dormitoryRecord.setDate(date);
        dormitoryRecord.setWater(water);
        dormitoryRecord.setElectricity(electricity);

        return dormitoryRecordRepository.save(dormitoryRecord);
    }

    public List<DormitoryRecord> findAll() {
        List<DormitoryRecord> dormitoryRecordList = dormitoryRecordRepository.findAll();

        // 插入宿舍房号
        if (dormitoryRecordList.size() > 0) {
            Map<Long, String> dormitoryMap = dormitoryService.findAll().stream().collect(Collectors.toMap(Dormitory::getId, Dormitory::getRoomNumber));
            dormitoryRecordList.forEach(data -> {
                data.setDormitory(dormitoryMap.get(data.getDormitoryId()));
            });
        }

        return dormitoryRecordList;
    }

    public DormitoryRecord findByBillDate(Long dormitoryId, Date billDate) {
        return dormitoryRecordRepository.findByDormitoryIdAndBillDate(dormitoryId, billDate);
    }
}
