package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.LoanRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.wcf.server.utils.DateUtils.dateFormat;

@RestController
@RequestMapping("/api/loan/record")
public class LoanRecordController {
    private final LoanRecordService loanRecordService;

    @Autowired
    public LoanRecordController(LoanRecordService loanRecordService) {
        this.loanRecordService = loanRecordService;
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(loanRecordService.findAll());
    }

    @PostMapping
    public ResultBody add(@RequestParam Long userId,
                          @RequestParam BigDecimal amount,
                          @RequestParam String purpose,
                          @RequestParam String paymentDate,
                          @RequestParam String paymentMethod,
                          @RequestParam(value = "note", required = false) String notes) {
        return ResultBody.success(loanRecordService.add(userId, amount, purpose, dateFormat(paymentDate), paymentMethod, notes));
    }

    @PutMapping("/{id}")
    public ResultBody setProcess(@PathVariable Long id,
                                 @RequestParam Boolean isProcessed) {
        return ResultBody.success(loanRecordService.setProcess(id, isProcessed));
    }

    @DeleteMapping("/{id}")
    public ResultBody deleteById(@PathVariable Long id) {
        loanRecordService.deleteById(id);
        return ResultBody.success();
    }
}
