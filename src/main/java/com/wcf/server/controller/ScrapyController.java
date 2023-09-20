package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.ScrapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
