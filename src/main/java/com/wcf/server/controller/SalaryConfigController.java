package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.SalaryConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.wcf.server.utils.DateUtils.dateFormat;

@RestController
@RequestMapping("/api/salary/config")
public class SalaryConfigController {
    private final SalaryConfigService salaryConfigService;

    @Autowired
    public SalaryConfigController(SalaryConfigService salaryConfigService) {
        this.salaryConfigService = salaryConfigService;
    }

    @GetMapping("/index")
    public ResultBody getIndex() {
        return ResultBody.success(salaryConfigService.getIndex());
    }

    @GetMapping("/users/{userId}")
    public ResultBody findAllByUserId(@PathVariable Long userId) {
        return ResultBody.success(salaryConfigService.findAllByUserId(userId));
    }

    @PostMapping
    public ResultBody add(@RequestParam Long userId,
                          @RequestParam String name,
                          @RequestParam String type,
                          @RequestParam Integer cycle,
                          @RequestParam String cycleUnit,
                          @RequestParam BigDecimal amount,
                          @RequestParam Boolean isDailyConversion,
                          @RequestParam Boolean isRealTime,
                          @RequestParam String effectiveDate,
                          @RequestParam(value = "notes", required = false) String notes) {
        salaryConfigService.add(userId, name, type, cycle, cycleUnit, amount, isDailyConversion, isRealTime, dateFormat(effectiveDate), notes);
        return ResultBody.success();
    }
}
