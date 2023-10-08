package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.model.Dormitory;
import com.wcf.server.model.DormitoryOccupancy;
import com.wcf.server.model.User;
import com.wcf.server.repository.DormitoryOccupancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DormitoryOccupancyService {
    private final DormitoryOccupancyRepository dormitoryOccupancyRepository;
    private final DormitoryService dormitoryService;
    private final UserService userService;

    @Autowired
    public DormitoryOccupancyService(DormitoryOccupancyRepository dormitoryOccupancyRepository,
                                     DormitoryService dormitoryService,
                                     UserService userService) {
        this.dormitoryOccupancyRepository = dormitoryOccupancyRepository;
        this.dormitoryService = dormitoryService;
        this.userService=userService;
    }

    public List<DormitoryOccupancy> findAll() {
        List<DormitoryOccupancy> dormitoryOccupancyList = dormitoryOccupancyRepository.findAll();
        if (dormitoryOccupancyList.size() > 0) {
            Map<Long, String> dormitoryMap = dormitoryService.findAll().stream().collect(Collectors.toMap(Dormitory::getId, Dormitory::getRoomNumber));
            Map<Long, String> userMap = userService.findAllIncludeDeleted().stream().collect(Collectors.toMap(User::getId, User::getName));
            dormitoryOccupancyList.forEach(data->{
                data.setDormitory(dormitoryMap.get(data.getDormitoryId()));
                data.setUser(userMap.get(data.getUserId()));
            });
        }
        return dormitoryOccupancyList;
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
