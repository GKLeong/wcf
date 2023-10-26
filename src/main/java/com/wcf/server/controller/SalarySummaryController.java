package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.SalarySummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.wcf.server.utils.DateUtils.dateFormat;

@RestController
@RequestMapping("/api/salary/summary")
public class SalarySummaryController {
    private final SalarySummaryService salarySummaryService;

    @Autowired
    public SalarySummaryController(SalarySummaryService salarySummaryService) {
        this.salarySummaryService = salarySummaryService;
    }

    @GetMapping
    public ResultBody findAllByBillDate(@RequestParam(value = "billDate", required = false) String billDate,
                                        @RequestParam(value = "name", required = false) String name) {
        return ResultBody.success(salarySummaryService.findAllByBillDate(dateFormat(billDate), name));
    }
}
