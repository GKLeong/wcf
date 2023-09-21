package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.ScrapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/scrapy")
public class ScrapyController {
    private ScrapyService scrapyService;

    @Autowired
    private void autowired(ScrapyService scrapyService) {
        this.scrapyService = scrapyService;
    }

    @PostMapping()
    public ResultBody parseExcel(@RequestParam("file") MultipartFile file) throws IOException {
        scrapyService.uploadExcel(file);
        return ResultBody.success();
    }

    @GetMapping
    public ResultBody findByArchive(@RequestParam("archive") boolean archive) {
        return ResultBody.success(scrapyService.findByArchive(archive));
    }
}
