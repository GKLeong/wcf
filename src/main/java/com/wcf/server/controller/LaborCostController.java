package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.LaborCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

import static com.wcf.server.utils.DateUtils.dateFormat;

@RestController
@RequestMapping("/api/labor/cost")
public class LaborCostController {
    private final LaborCostService laborCostService;

    @Autowired
    public LaborCostController(LaborCostService laborCostService) {
        this.laborCostService = laborCostService;
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(laborCostService.findAll());
    }

    @GetMapping("/effective")
    public ResultBody findEffective() {
        return ResultBody.success(laborCostService.findEffective());
    }

    @GetMapping("/old")
    public ResultBody findOld() {
        return ResultBody.success(laborCostService.findOld());
    }

    @GetMapping("/future")
    public ResultBody findFuture() {
        return ResultBody.success(laborCostService.findFuture());
    }

    @PostMapping
    public ResultBody add(@RequestParam Long departmentId,
                          @RequestParam String action,
                          @RequestParam BigDecimal price,
                          @RequestParam(value = "comments", required = false) String comments,
                          @RequestParam String effectiveDate) {
        return ResultBody.success(laborCostService.add(departmentId, action, price, comments, dateFormat(effectiveDate)));
    }

    @PostMapping("/excel")
    public ResultBody parseExcel(@RequestParam("file") MultipartFile file) throws IOException {
        laborCostService.uploadExcel(file);
        return ResultBody.success();
    }

    @DeleteMapping("/{id}")
    public ResultBody delete(@PathVariable Long id) {
        laborCostService.deleteById(id);
        return ResultBody.success();
    }
}
