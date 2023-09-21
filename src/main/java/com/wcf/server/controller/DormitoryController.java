package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.DormitoryService;
import com.wcf.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/api/dormitories")
public class DormitoryController {
    private final DormitoryService dormitoryService;

    @Autowired
    public DormitoryController(DormitoryService dormitoryService) {
        this.dormitoryService = dormitoryService;
    }

    @PostMapping
    public ResultBody add(@RequestParam("roomNumber") String roomNumber,
                          @RequestParam("address") String address,
                          @RequestParam("waterPrice") BigDecimal waterPrice,
                          @RequestParam("electricityPrice") BigDecimal electricityPrice,
                          @RequestParam("leaseStartDate") String leaseStartDate) {
        return ResultBody.success(dormitoryService.add(roomNumber, address, waterPrice, electricityPrice, DateUtils.dateFormat(leaseStartDate)));
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(dormitoryService.findAll());
    }
}
