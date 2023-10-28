package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.model.Department;
import com.wcf.server.model.LaborCost;
import com.wcf.server.model.LaborData;
import com.wcf.server.model.User;
import com.wcf.server.repository.LaborDataRepository;
import com.wcf.server.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.wcf.server.utils.DateUtils.getNextMonthFirstDay;

@Service
public class LaborDataService {
    private final LaborDataRepository laborDataRepository;
    private final AttachmentService attachmentService;
    private final UserService userService;
    private final LaborCostService laborCostService;
    private final DepartmentService departmentService;

    @Autowired
    public LaborDataService(LaborDataRepository laborDataRepository,
                            AttachmentService attachmentService,
                            UserService userService,
                            LaborCostService laborCostService,
                            DepartmentService departmentService) {
        this.laborDataRepository = laborDataRepository;
        this.attachmentService = attachmentService;
        this.userService = userService;
        this.laborCostService = laborCostService;
        this.departmentService = departmentService;
    }

    public List<LaborData> findByArchiveDateIsNull() {
        return laborDataRepository.findByArchiveDateIsNull();
    }

    public List<LaborData> findAll() {
        return laborDataRepository.findAll();
    }

    public LaborData findById(Long id) {
        return laborDataRepository.findById(id).orElseThrow(() -> new BizException("数据 id 不存在: " + id));
    }

    public BigDecimal sumByUserIdAndBillDate(Long userId, Date billDate) {
        Date nextMonthFirstDay = getNextMonthFirstDay(billDate);
        BigDecimal amount = laborDataRepository.sumAmountByUserIdAndDate(userId, billDate, nextMonthFirstDay);
        if (amount == null) amount = new BigDecimal(0);

        return amount;
    }

    public void uploadExcel(MultipartFile file) throws IOException {
        attachmentService.addExcel(file);
        List<HashMap<String, ExcelUtils>> excelDataList = ExcelUtils.parse(file);
        List<LaborData> dataList = new ArrayList<>();
        LaborData data;
        User user;
        Map<String, LaborCost> laborCostEffectiveMap = laborCostService.findEffective()
                .stream()
                .collect(Collectors.toMap(
                        lc -> String.format("%d+%s", lc.getDepartmentId(), lc.getAction()),
                        Function.identity()
                ));
        LaborCost laborCost;
        Map<String, Long> departmentMap = departmentService.findAll().stream().collect(Collectors.toMap(Department::getName, Department::getId));
        Map<String, User> userMap = userService.findAllByDeletedIsFalse()
                .stream().collect(Collectors.toMap(
                        lc -> String.format("%d+%s", lc.getDepartmentId(), lc.getName()),
                        Function.identity()
                ));

        for (HashMap<String, ExcelUtils> excelData : excelDataList) {
            data = new LaborData();
            // orderId
            // productId
            data.setProductName(excelData.get("产品").getIntegerString());
            data.setDate(excelData.get("日期").getDate());
            data.setDepartmentId(departmentMap.get(excelData.get("部门").getString()));
            data.setAction(excelData.get("动作").getString());
            data.setNotes(excelData.get("备注").getString());
            data.setCardGroup(excelData.get("卡片").getString());
            data.setCardNumber(excelData.get("编号").getIntegerString());

            String laborCostMapKey = String.format("%d+%s", data.getDepartmentId(), data.getAction());
            laborCost = laborCostEffectiveMap.get(laborCostMapKey);
            data.setLaborCostId(laborCost.getId());
            data.setUnitPrice(laborCost.getPrice());
            data.setFrequency(excelData.get("步骤").getBigDecimal());
            data.setQuantity(excelData.get("数量").getBigDecimal());
            data.setAmount(data.getQuantity().multiply(data.getFrequency()).multiply(data.getUnitPrice()));

            user = userMap.get(String.format("%d+%s", data.getDepartmentId(), excelData.get("生产者").getString()));
            data.setUserId(user.getId());
            data.setProducer(user.getName());
            dataList.add(data);
        }

        laborDataRepository.saveAll(dataList);
    }

    public LaborData update(Long id,
                            Long orderId,
                            Long productId,
                            Date date,
                            Long laborCostId,
                            BigDecimal quantity,
                            BigDecimal frequency,
                            String notes,
                            Long userId,
                            String cardGroup,
                            String cardNumber) {
        LaborData laborData = findById(id);

        // 需要修改为从实例获取
        laborData.setOrderId(orderId);
        laborData.setProductId(productId);
        laborData.setProductName(null);

        laborData.setDate(date);

        LaborCost laborCost = laborCostService.findById(laborCostId);
        laborData.setLaborCostId(laborCost.getId());

        Department department = departmentService.findById(laborCost.getDepartmentId());
        laborData.setDepartmentId(department.getId());
        laborData.setDepartment(department);

        laborData.setAction(laborCost.getAction());
        laborData.setQuantity(quantity);
        laborData.setFrequency(frequency);
        laborData.setUnitPrice(laborCost.getPrice());
        laborData.setAmount(laborData.getQuantity().multiply(laborData.getFrequency().multiply(laborData.getUnitPrice())));
        laborData.setNotes(notes);

        User labor = userService.findByIdAndDeletedIsFalse(userId);
        laborData.setUserId(labor.getId());
        laborData.setProducer(labor.getName());

        laborData.setCardGroup(cardGroup);
        laborData.setCardNumber(cardNumber);
        return laborDataRepository.save(laborData);
    }
}
