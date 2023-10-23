package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.SalarySummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wcf.server.utils.DateUtils.dateFormat;

@RestController
@RequestMapping("/api/salary/summary")
public class SalarySummaryController {
    private final SalarySummaryService salarySummaryService;

    @Autowired
    public SalarySummaryController(SalarySummaryService salarySummaryService) {
        this.salarySummaryService = salarySummaryService;
    }

    @GetMapping("/{billDate}")
    public ResultBody findAllByBillDate(@PathVariable String billDate) {
        return ResultBody.success(salarySummaryService.findAllByBillDate(dateFormat(billDate)));
    }
}
