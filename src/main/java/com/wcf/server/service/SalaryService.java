package com.wcf.server.service;

import com.wcf.server.model.Salary;
import com.wcf.server.model.SalaryConfig;
import com.wcf.server.model.User;
import com.wcf.server.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class SalaryService {
    private final SalaryRepository salaryRepository;
    private final SalaryConfigService salaryConfigService;
    private final UserService userService;

    @Autowired
    public SalaryService(SalaryRepository salaryRepository,
                         SalaryConfigService salaryConfigService,
                         UserService userService) {
        this.salaryRepository = salaryRepository;
        this.salaryConfigService = salaryConfigService;
        this.userService = userService;
    }

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

    public void addBySystem(SalaryConfig salaryConfig, Date billDate, Integer laborDays) {
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
        salary.setAmount(calculateAmount(salaryConfig, laborDays));
        salary.setOperator("system");
        salaryRepository.save(salary);
    }

    @Transactional
    public void autoGenerate(Date billDate, Integer laborDays) {
        List<User> users = userService.findAllByDeletedIsFalse();
        List<Long> userIds = users.stream().map(User::getId).toList();
        List<SalaryConfig> salaryConfigs = salaryConfigService.findAllByUserIdInAndIsEffectiveIsTrue(userIds);
        for (SalaryConfig salaryConfig : salaryConfigs) {
            addBySystem(salaryConfig, billDate, laborDays);
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
}
