package com.wcf.server.service;

import com.wcf.server.model.Scrapy;
import com.wcf.server.repository.ScrapyRepository;
import com.wcf.server.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ScrapyService {
    private ScrapyRepository scrapyRepository;

    @Autowired
    private void autowired(ScrapyRepository scrapyRepository) {
        this.scrapyRepository = scrapyRepository;
    }

    public void uploadExcel(MultipartFile file) throws IOException {
        List<HashMap<String, Object>> excelDataList = ExcelUtils.parse(file);
        List<Scrapy> dataList = new ArrayList<>();
        Scrapy data;

        for (HashMap<String, Object> excelData : excelDataList) {
            data = new Scrapy();

            // 提取日期
            LocalDateTime localDateTime = (LocalDateTime) excelData.get("日期");
            ZoneId zoneId = ZoneId.systemDefault();
            Date date = Date.from(localDateTime.atZone(zoneId).toInstant());

            data.setDateRecorded(date);
            data.setPackageNumber(((Double) excelData.get("编号")).intValue());
            data.setWeightKg(BigDecimal.valueOf((double) excelData.get("重量")));
            data.setTotalPackage(((Double) excelData.get("袋数")).intValue());
            data.setComments((String) excelData.get("备注"));
            dataList.add(data);
        }

        scrapyRepository.saveAll(dataList);
    }
}
