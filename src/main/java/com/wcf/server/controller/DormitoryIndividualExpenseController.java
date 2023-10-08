package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.DormitoryIndividualExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dormitory/iExpense")
public class DormitoryIndividualExpenseController {
    private final DormitoryIndividualExpenseService dormitoryIndividualExpenseService;

    @Autowired
    public DormitoryIndividualExpenseController(DormitoryIndividualExpenseService dormitoryIndividualExpenseService) {
        this.dormitoryIndividualExpenseService = dormitoryIndividualExpenseService;
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(dormitoryIndividualExpenseService.findAll());
    }
}
