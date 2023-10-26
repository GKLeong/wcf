package com.wcf.server.service;

import com.wcf.server.model.Salary;
import com.wcf.server.model.SalaryConfig;
import com.wcf.server.model.SalarySummary;
import com.wcf.server.model.User;
import com.wcf.server.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SalaryService {
    private final SalaryRepository salaryRepository;
    private final SalaryConfigService salaryConfigService;
    private final UserService userService;
    private final SalarySummaryService salarySummaryService;
    private final LaborDataService laborDataService;

    @Autowired
    public SalaryService(SalaryRepository salaryRepository,
                         SalaryConfigService salaryConfigService,
                         UserService userService,
                         SalarySummaryService salarySummaryService,
                         LaborDataService laborDataService) {
        this.laborDataService = laborDataService;
        this.salarySummaryService = salarySummaryService;
        this.salaryRepository = salaryRepository;
        this.salaryConfigService = salaryConfigService;
        this.userService = userService;
    }

    @Transactional
    public Salary addByUser(Date billDate, Long userId, String name, String type, BigDecimal amount, String notes) {
        Salary salary = new Salary();
        salary.setBillDate(billDate);
        salary.setUserId(userId);
        User user = userService.findByIdAndDeletedIsFalse(userId);
        salary.setUser(user);
        salary.setName(name);
        salary.setType(SalaryConfig.SalaryType.valueOf(type));
        salary.setAmount(amount);
        salary.setNotes(notes);
        User operator = userService.getCurrentUser();
        salary.setOperatorId(operator.getId());
        salary.setOperator(operator.getName());
        return salaryRepository.save(salary);
    }

    private void deleteSystemDataBySalaryConfigIdAndBillDate(Long salaryConfigId, Date billDate) {
        salaryRepository.deleteAllBySalaryConfigIdAndBillDateAndOperatorIdAndOperator(salaryConfigId, billDate, null, "system");
    }

    public void addBySystem(SalaryConfig salaryConfig, Date billDate, Integer laborDays) {
        // 创建前先删除同账单下的旧数据
        deleteSystemDataBySalaryConfigIdAndBillDate(salaryConfig.getId(), billDate);

        Salary salary = new Salary();
        salary.setBillDate(billDate);
        salary.setUserId(salaryConfig.getUserId());
        salary.setUser(salaryConfig.getUserEntity());
        salary.setSalaryConfigId(salaryConfig.getId());
        salary.setName(salaryConfig.getName());
        salary.setType(salaryConfig.getType());
        salary.setCycle(salaryConfig.getCycle());
        salary.setCycleUnit(salaryConfig.getCycleUnit());
        salary.setIsDailyConversion(salaryConfig.getIsDailyConversion());
        salary.setLaborDays(laborDays);
        salary.setIsRealTime(salaryConfig.getIsRealTime());
        BigDecimal amount = calculateAmount(salaryConfig, laborDays);
        if (salaryConfig.getName().equalsIgnoreCase("计件工资")) {
            amount = laborDataService.sumByUserIdAndBillDate(salaryConfig.getUserId(), billDate);
        }
        salary.setAmount(amount);
        salary.setOperator("system");
        salaryRepository.save(salary);
    }

    @Transactional
    public void autoGenerate(Date billDate, Integer laborDays) {
        // 生成工资详情
        List<User> users = userService.findAllByDeletedIsFalse();
        List<Long> userIds = users.stream().map(User::getId).toList();
        List<SalaryConfig> salaryConfigs = salaryConfigService.findAllByUserIdInAndIsEffectiveIsTrue(userIds);
        for (SalaryConfig salaryConfig : salaryConfigs) {
            if (salaryConfig.getName().equalsIgnoreCase("保底工资")) continue;
            addBySystem(salaryConfig, billDate, laborDays);
        }

        // 生成工资表
        List<Salary> salaries = salaryRepository.findAllByBillDate(billDate);
        salarySummaryService.generate(billDate, salaries);

        // 处理保底工资
        List<SalarySummary> salarySummaryList = salarySummaryService.findAllByBillDate(billDate);
        HashMap<Long, SalaryConfig> guaranteedSalaryMap = salaryConfigService.findAllGuaranteedSalaryByIsEffectiveIsTrue();
        for (SalarySummary salarySummary : salarySummaryList) {
            SalaryConfig guaranteedSalary = guaranteedSalaryMap.get(salarySummary.getUserId());
            manageGuaranteed(guaranteedSalary, salarySummary);
        }
    }

    private BigDecimal calculateAmount(SalaryConfig salaryConfig, Integer laborDays) {
        BigDecimal amount = salaryConfig.getAmount();
        if (salaryConfig.getIsDailyConversion()) { // 按天折算
            BigDecimal shouldLaborDays = new BigDecimal("1");
            if (salaryConfig.getCycleUnit() == SalaryConfig.CycleUnit.month) shouldLaborDays = new BigDecimal("30");
            else if (salaryConfig.getCycleUnit() == SalaryConfig.CycleUnit.year)
                shouldLaborDays = new BigDecimal("365");
            shouldLaborDays = shouldLaborDays.multiply(new BigDecimal(salaryConfig.getCycle()));

            amount = amount
                    .multiply(new BigDecimal(laborDays))
                    .divide(shouldLaborDays, 0, RoundingMode.HALF_UP);
        }
        return amount;
    }

    public void manageGuaranteed(SalaryConfig guaranteed, SalarySummary salarySummary) {
        if (guaranteed == null) return;

        BigDecimal guaranteedAmount = calculateAmount(guaranteed, salarySummary.getLaborDays());
        if (salarySummary.getAmount().compareTo(guaranteedAmount) < 0) { // 当前工资小于保底工资
            BigDecimal allowance = guaranteedAmount.subtract(salarySummary.getAmount());
            // 创建前先删除同账单下的旧数据
            deleteSystemDataBySalaryConfigIdAndBillDate(guaranteed.getId(), salarySummary.getBillDate());

            Salary salary = new Salary();
            salary.setBillDate(salarySummary.getBillDate());
            salary.setUserId(guaranteed.getUserId());
            salary.setUser(guaranteed.getUserEntity());
            salary.setSalaryConfigId(guaranteed.getId());
            salary.setName(guaranteed.getName());
            salary.setType(guaranteed.getType());
            salary.setCycle(guaranteed.getCycle());
            salary.setCycleUnit(guaranteed.getCycleUnit());
            salary.setIsDailyConversion(guaranteed.getIsDailyConversion());
            salary.setLaborDays(salarySummary.getLaborDays());
            salary.setIsRealTime(guaranteed.getIsRealTime());
            salary.setAmount(allowance);
            salary.setOperator("system");
            salaryRepository.save(salary);
        }
    }
}
