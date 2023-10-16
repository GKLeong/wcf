package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static com.wcf.server.utils.DateUtils.dateFormat;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {
    private final SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping
    public ResultBody addByUser(@RequestParam String billDate,
                                @RequestParam Long userId,
                                @RequestParam String name,
                                @RequestParam String type,
                                @RequestParam BigDecimal amount,
                                @RequestParam(value = "notes", required = false) String notes) {
        return ResultBody.success(salaryService.addByUser(dateFormat(billDate), userId, name, type, amount, notes));
    }

    @PostMapping("/generate")
    public ResultBody autoGenerate(@RequestParam String billDate,
                                   @RequestParam Integer laborDays) {
        salaryService.autoGenerate(dateFormat(billDate), laborDays);
        return ResultBody.success();
    }
}
