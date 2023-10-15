package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.SalaryConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
