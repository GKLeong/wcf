package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.model.DormitoryOccupancy;
import com.wcf.server.repository.DormitoryOccupancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DormitoryOccupancyService {
    private final DormitoryOccupancyRepository dormitoryOccupancyRepository;

    @Autowired
    public DormitoryOccupancyService(DormitoryOccupancyRepository dormitoryOccupancyRepository) {
        this.dormitoryOccupancyRepository = dormitoryOccupancyRepository;
    }

    public List<DormitoryOccupancy> findAll() {
        return dormitoryOccupancyRepository.findAll();
    }

    public DormitoryOccupancy add(Long dormitoryId, Long userId, Date checkInDate) {
        DormitoryOccupancy dormitoryOccupancy = new DormitoryOccupancy();
        dormitoryOccupancy.setDormitoryId(dormitoryId);
        dormitoryOccupancy.setUserId(userId);
        dormitoryOccupancy.setCheckInDate(checkInDate);
        return dormitoryOccupancyRepository.save(dormitoryOccupancy);
    }

    public DormitoryOccupancy checkOut(Long dormitoryId, Date checkOutDate) {
        DormitoryOccupancy dormitoryOccupancy = dormitoryOccupancyRepository.findById(dormitoryId).orElseThrow(() -> new BizException("宿舍id不存在: " + dormitoryId));
        dormitoryOccupancy.setCheckOutDate(checkOutDate);
        return dormitoryOccupancyRepository.save(dormitoryOccupancy);
    }

    public List<DormitoryOccupancy> findAllByDormitoryIdAndBillDate(Long dormitoryId, Date billDate, Date nextBillDate) {
        return dormitoryOccupancyRepository.findAllByDormitoryIdAndBillDate(dormitoryId, billDate, nextBillDate);
    }
}
