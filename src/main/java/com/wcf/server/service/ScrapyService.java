package com.wcf.server.service;

import com.wcf.server.model.Scrapy;
import com.wcf.server.repository.ScrapyRepository;
import com.wcf.server.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
        List<HashMap<String, ExcelUtils>> excelDataList = ExcelUtils.parse(file);
        List<Scrapy> dataList = new ArrayList<>();
        Scrapy data;

        for (HashMap<String, ExcelUtils> excelData : excelDataList) {
            data = new Scrapy();

            data.setDateRecorded(excelData.get("日期").getDate());
            data.setPackageNumber(excelData.get("编号").getInteger());
            data.setWeightKg(excelData.get("重量").getBigDecimal());
            Integer totalPackage = excelData.get("袋数").getInteger();
            if (totalPackage != null) data.setTotalPackage(totalPackage);
            data.setComments(excelData.get("备注").getString());
            dataList.add(data);
        }

        scrapyRepository.saveAll(dataList);
    }

    public List<Scrapy> findByArchive(boolean archive) {
        return scrapyRepository.findByArchive(archive);
    }
}
