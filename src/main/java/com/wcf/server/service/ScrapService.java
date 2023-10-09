package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.base.response.CommonEnum;
import com.wcf.server.dto.ScrapDTO;
import com.wcf.server.model.Scrap;
import com.wcf.server.model.ScrapStatistic;
import com.wcf.server.repository.ScrapRepository;
import com.wcf.server.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final ScrapStatisticService scrapStatisticService;
    private final AttachmentService attachmentService;

    @Autowired
    public ScrapService(ScrapRepository scrapRepository,
                        ScrapStatisticService scrapStatisticService,
                        AttachmentService attachmentService) {
        this.scrapRepository = scrapRepository;
        this.scrapStatisticService = scrapStatisticService;
        this.attachmentService = attachmentService;
    }

    public void uploadExcel(MultipartFile file) throws IOException {
        attachmentService.addExcel(file);
        List<HashMap<String, ExcelUtils>> excelDataList = ExcelUtils.parse(file);
        List<Scrap> dataList = new ArrayList<>();
        Scrap data;

        for (HashMap<String, ExcelUtils> excelData : excelDataList) {
            data = new Scrap();

            data.setDateRecorded(excelData.get("日期").getDate());
            data.setPackageNumber(excelData.get("编号").getInteger());
            data.setWeightKg(excelData.get("重量").getBigDecimal());
            Integer totalPackage = excelData.get("袋数").getInteger();
            if (totalPackage != null) data.setTotalPackage(totalPackage);
            data.setComments(excelData.get("备注").getString());
            dataList.add(data);
        }

        scrapRepository.saveAll(dataList);
    }

    public List<Scrap> findByArchive(boolean archive) {
        return scrapRepository.findByArchive(archive);
    }

    @Transactional
    public void archive(Date from, Date end, BigDecimal packageWeight, BigDecimal unitPrice) {
        ScrapDTO scrapDTO = scrapRepository.sumDataBeforeArchive(from, end);
        if (scrapDTO.getTotalWeightKg() == null) throw new BizException(CommonEnum.NOT_FOUND);
        ScrapStatistic scrapStatistic = new ScrapStatistic();

        scrapStatistic.setStartDate(from);
        scrapStatistic.setEndDate(end);

        BigDecimal totalWeightKg = scrapDTO.getTotalWeightKg();
        scrapStatistic.setTotalWeightKg(totalWeightKg);

        int totalPackage = scrapDTO.getTotalPackage().intValue();
        scrapStatistic.setTotalPackage(totalPackage);

        scrapStatistic.setPackageWeight(packageWeight);

        BigDecimal totalPackageWeight = packageWeight.multiply(BigDecimal.valueOf(totalPackage));
        scrapStatistic.setTotalPackageWeight(totalPackageWeight);

        BigDecimal netWeightKg = totalWeightKg.subtract(totalPackageWeight);
        scrapStatistic.setNetWeightKg(netWeightKg);

        scrapStatistic.setUnitPrice(unitPrice);
        scrapStatistic.setTotalPrice(netWeightKg.multiply(unitPrice));

        scrapRepository.archive(from, end);
        scrapStatisticService.save(scrapStatistic);
    }

    public List<Object[]> sumByRecorded(Date from, Date end) {
        return scrapRepository.sumByRecorded(from, end);
    }
}
