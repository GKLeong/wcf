package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.model.Department;
import com.wcf.server.model.LaborCost;
import com.wcf.server.repository.LaborCostRepository;
import com.wcf.server.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LaborCostService {
    private final LaborCostRepository laborCostRepository;
    private final AttachmentService attachmentService;
    private final DepartmentService departmentService;

    @Autowired
    public LaborCostService(LaborCostRepository laborCostRepository,
                            AttachmentService attachmentService,
                            DepartmentService departmentService) {
        this.laborCostRepository = laborCostRepository;
        this.attachmentService = attachmentService;
        this.departmentService = departmentService;
    }

    public List<LaborCost> findAll() {
        return laborCostRepository.findAll();
    }

    public LaborCost findById(Long id) {
        return laborCostRepository.findById(id).orElseThrow(() -> new BizException("工价数据 id 不存在: " + id));
    }

    public List<LaborCost> findOld() {
        return laborCostRepository.findOld();
    }

    public List<LaborCost> findFuture() {
        return laborCostRepository.findFuture();
    }

    public List<LaborCost> findEffective() {
        return laborCostRepository.findEffective();
    }

    public LaborCost add(Long departmentId, String action, BigDecimal price, String comments, Date effectiveDate) {
        LaborCost laborCost = new LaborCost();
        laborCost.setDepartmentId(departmentId);
        laborCost.setAction(action);
        laborCost.setPrice(price);
        laborCost.setComments(comments);
        laborCost.setEffectiveDate(effectiveDate);
        return laborCostRepository.save(laborCost);
    }

    public void uploadExcel(MultipartFile file) throws IOException {
        attachmentService.addExcel(file);
        List<HashMap<String, ExcelUtils>> excelDataList = ExcelUtils.parse(file);
        List<LaborCost> dataList = new ArrayList<>();
        LaborCost data;
        Map<String, Long> departmentMap = departmentService.findAll().stream().collect(Collectors.toMap(Department::getName, Department::getId));

        for (HashMap<String, ExcelUtils> excelData : excelDataList) {
            data = new LaborCost();
            data.setDepartmentId(departmentMap.get(excelData.get("部门").getString()));
            data.setAction(excelData.get("动作").getString());
            data.setPrice(excelData.get("价格").getBigDecimal());
            data.setEffectiveDate(excelData.get("生效日期").getDate());
            data.setComments(excelData.get("备注").getString());
            dataList.add(data);
        }

        laborCostRepository.saveAll(dataList);
    }

    public void deleteById(Long id) {
        laborCostRepository.deleteById(id);
    }
}
