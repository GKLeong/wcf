package com.wcf.server.service;

import com.wcf.server.model.Dormitory;
import com.wcf.server.repository.DormitoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class DormitoryService {
    private final DormitoryRepository dormitoryRepository;

    @Autowired
    public DormitoryService(DormitoryRepository dormitoryRepository) {
        this.dormitoryRepository = dormitoryRepository;
    }

    public Dormitory add(String roomNumber, String address, BigDecimal waterPrice, BigDecimal electricityPrice, Date leaseStartDate) {
        Dormitory dormitory = new Dormitory();
        dormitory.setRoomNumber(roomNumber);
        dormitory.setAddress(address);
        dormitory.setWaterPrice(waterPrice);
        dormitory.setElectricityPrice(electricityPrice);
        dormitory.setLeaseStartDate(leaseStartDate);
        return dormitoryRepository.save(dormitory);
    }

    public List<Dormitory> findAll() {
        return dormitoryRepository.findAll();
    }
}
