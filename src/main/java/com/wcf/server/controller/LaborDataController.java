package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.LaborDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
}
