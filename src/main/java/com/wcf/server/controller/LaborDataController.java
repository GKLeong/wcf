package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.LaborDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

import static com.wcf.server.utils.DateUtils.dateFormat;

@RestController
@RequestMapping("/api/labor/data")
public class LaborDataController {
    private final LaborDataService laborDataService;

    @Autowired
    public LaborDataController(LaborDataService laborDataService) {
        this.laborDataService = laborDataService;
    }

    @GetMapping
    public ResultBody findByArchiveDateIsNull() {
        return ResultBody.success(laborDataService.findByArchiveDateIsNull());
    }

    @GetMapping("/all")
    public ResultBody findAll() {
        return ResultBody.success(laborDataService.findAll());
    }

    @PostMapping()
    public ResultBody parseExcel(@RequestParam("file") MultipartFile file) throws IOException {
        laborDataService.uploadExcel(file);
        return ResultBody.success();
    }

    @PutMapping("/{id}")
    public ResultBody update(@PathVariable Long id,
                             @RequestParam(value = "orderId", required = false) Long orderId,
                             @RequestParam(value = "productId", required = false) Long productId,
                             @RequestParam String productName,
                             @RequestParam String date,
                             @RequestParam Long laborCostId,
                             @RequestParam BigDecimal quantity,
                             @RequestParam BigDecimal frequency,
                             @RequestParam(value = "notes", required = false) String notes,
                             @RequestParam Long userId,
                             @RequestParam(value = "cardGroup", required = false) String cardGroup,
                             @RequestParam(value = "cardNumber", required = false) String cardNumber) {
        return ResultBody.success(laborDataService.update(id, orderId, productId, productName, dateFormat(date), laborCostId, quantity, frequency, notes, userId, cardGroup, cardNumber));
    }

    @GetMapping("/export")
    public ResultBody exportToExcel() throws IOException {
        return ResultBody.success(laborDataService.exportToExcel());
    }
}
