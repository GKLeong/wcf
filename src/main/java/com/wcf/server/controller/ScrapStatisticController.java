package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.ScrapStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scrap/statistics")
public class ScrapStatisticController {
    private final ScrapStatisticService scrapStatisticService;

    @Autowired
    public ScrapStatisticController(ScrapStatisticService scrapStatisticService) {
        this.scrapStatisticService = scrapStatisticService;
    }

    @GetMapping()
    public ResultBody findAll() {
        return ResultBody.success(scrapStatisticService.findAll());
    }
}
