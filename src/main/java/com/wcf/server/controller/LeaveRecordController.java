package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.LeaveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.wcf.server.utils.DateUtils.dateFormat;

@RestController
@RequestMapping("/api/leave/record")
public class LeaveRecordController {
    private final LeaveRecordService leaveRecordService;

    @Autowired
    public LeaveRecordController(LeaveRecordService leaveRecordService) {
        this.leaveRecordService = leaveRecordService;
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(leaveRecordService.findAll());
    }

    @PostMapping
    public ResultBody add(@RequestParam("userId") Long userId,
                          @RequestParam("startDate") String startDate,
                          @RequestParam("endDate") String endDate,
                          @RequestParam("reason") String reason) {
        return ResultBody.success(leaveRecordService.add(userId, dateFormat(startDate), dateFormat(endDate), reason));
    }

    @PutMapping("/{id}")
    public ResultBody update(@PathVariable Long id,
                             @RequestParam("startDate") String startDate,
                             @RequestParam("endDate") String endDate,
                             @RequestParam("reason") String reason) {
        return ResultBody.success(leaveRecordService.update(id, dateFormat(startDate), dateFormat(endDate), reason));
    }
}
