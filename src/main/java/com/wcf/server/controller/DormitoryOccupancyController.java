package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.DormitoryOccupancyService;
import com.wcf.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dormitory/occupancy")
public class DormitoryOccupancyController {
    private final DormitoryOccupancyService dormitoryOccupancyService;

    @Autowired
    public DormitoryOccupancyController(DormitoryOccupancyService dormitoryOccupancyService) {
        this.dormitoryOccupancyService = dormitoryOccupancyService;
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(dormitoryOccupancyService.findAll());
    }

    @PostMapping
    public ResultBody add(@RequestParam("dormitoryId") Long dormitoryId,
                          @RequestParam("userId") Long userId,
                          @RequestParam("checkInDate") String checkInDate) {
        return ResultBody.success(dormitoryOccupancyService.add(dormitoryId, userId, DateUtils.dateFormat(checkInDate)));
    }

    @PutMapping("/{id}")
    public ResultBody checkOut(@PathVariable Long id, @RequestParam("checkOutDate") String checkOutDate) {
        return ResultBody.success(dormitoryOccupancyService.checkOut(id, DateUtils.dateFormat(checkOutDate)));
    }
}
