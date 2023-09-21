package com.wcf.server.controller;

import com.wcf.server.base.response.CommonEnum;
import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.ScrapService;
import com.wcf.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/scrap")
public class ScrapController {
    private final ScrapService scrapService;

    @Autowired
    public ScrapController(ScrapService scrapService) {
        this.scrapService = scrapService;
    }

    @PostMapping()
    public ResultBody parseExcel(@RequestParam("file") MultipartFile file) throws IOException {
        scrapService.uploadExcel(file);
        return ResultBody.success();
    }

    @GetMapping
    public ResultBody findByArchive(@RequestParam("archive") boolean archive) {
        return ResultBody.success(scrapService.findByArchive(archive));
    }

    @PostMapping("archive")
    public ResultBody archive(@RequestParam("from") String from,
                              @RequestParam("end") String end,
                              @RequestParam("packageWeight") BigDecimal packageWeight,
                              @RequestParam("unitPrice") BigDecimal unitPrice
    ) {
        scrapService.archive(DateUtils.dateFormat(from), DateUtils.dateFormat(end), packageWeight, unitPrice);
        return ResultBody.success();
    }
}
