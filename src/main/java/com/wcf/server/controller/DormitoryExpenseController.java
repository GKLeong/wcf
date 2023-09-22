package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.DormitoryExpenseService;
import com.wcf.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dormitory/expense")
public class DormitoryExpenseController {
    private final DormitoryExpenseService dormitoryExpenseService;

    @Autowired
    public DormitoryExpenseController(DormitoryExpenseService dormitoryExpenseService) {
        this.dormitoryExpenseService = dormitoryExpenseService;
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(dormitoryExpenseService.findAll());
    }

    @PostMapping("generate")
    public ResultBody generate(@RequestParam("date") String date) {
        dormitoryExpenseService.generate(DateUtils.dateFormat(date));
        return ResultBody.success();
    }
}
