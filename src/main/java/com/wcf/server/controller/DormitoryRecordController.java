package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.model.DormitoryRecord;
import com.wcf.server.service.DormitoryRecordService;
import com.wcf.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/dormitory/record")
public class DormitoryRecordController {
    private final DormitoryRecordService dormitoryRecordService;

    @Autowired
    public DormitoryRecordController(DormitoryRecordService dormitoryRecordService) {
        this.dormitoryRecordService = dormitoryRecordService;
    }

    @PostMapping
    public ResultBody add(@RequestParam("dormitoryId") Long dormitoryId,
                          @RequestParam("date") String date,
                          @RequestParam("water") BigDecimal water,
                          @RequestParam("electricity") BigDecimal electricity) {
        return ResultBody.success(dormitoryRecordService.add(dormitoryId, DateUtils.dateFormat(date), water, electricity));
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(dormitoryRecordService.findAll());
    }
}
