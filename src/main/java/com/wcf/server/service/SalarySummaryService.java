package com.wcf.server.service;

import com.wcf.server.model.Salary;
import com.wcf.server.model.SalaryConfig;
import com.wcf.server.model.SalarySummary;
import com.wcf.server.model.User;
import com.wcf.server.repository.SalarySummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SalarySummaryService {
    private final SalarySummaryRepository salarySummaryRepository;
    private final UserService userService;

    @Autowired
    public SalarySummaryService(SalarySummaryRepository salarySummaryRepository,
                                UserService userService) {
        this.userService = userService;
        this.salarySummaryRepository = salarySummaryRepository;
    }

    public List<SalarySummary> findAllByBillDate(Date billDate, String name) {
        User user = null;
        if (name != null) user = userService.findByNameAndDeletedIsFalse(name);

        if (billDate != null && name != null) {
            return salarySummaryRepository.findAllByBillDateAndUserId(billDate, user.getId());
        } else if (billDate != null) {
            return salarySummaryRepository.findAllByBillDate(billDate);
        } else if (name != null) {
            return salarySummaryRepository.findAllByUserId(user.getId());
        } else {
            return salarySummaryRepository.findAll();
        }
    }

    public List<SalarySummary> findAllByBillDate(Date billDate) {
        return salarySummaryRepository.findAllByBillDate(billDate);
    }

    private void deleteSystemDataByBillDate(Date billDate) {
        salarySummaryRepository.deleteAllByBillDateAndOperatorIdAndOperator(billDate, null, "system");
    }

    public void generate(Date billDate, List<Salary> salaries) {
        // 删除已有报表
        deleteSystemDataByBillDate(billDate);

        HashMap<Long, SalarySummary> summaryHashMap = new HashMap<>();
        for (Salary salary : salaries) {
            SalarySummary salarySummary = summaryHashMap.get(salary.getUserId());
            if (salarySummary == null) {
                salarySummary = new SalarySummary();
                salarySummary.setBillDate(billDate);
                salarySummary.setUserId(salary.getUserId());
                salarySummary.setUser(salary.getUserEntity());
                salarySummary.setPaymentMethod(null);
                salarySummary.setAccountNumber(null);
                salarySummary.setLaborDays(0);
                salarySummary.setBaseSalary(new BigDecimal(0));
                salarySummary.setPieceSalary(new BigDecimal(0));
                salarySummary.setAllowance(new BigDecimal(0));
                salarySummary.setDeduction(new BigDecimal(0));
                salarySummary.setAmount(new BigDecimal(0));
                salarySummary.setPaid(new BigDecimal(0));
                salarySummary.setFinish(false);
                salarySummary.setNotes(null);
                salarySummary.setOperatorId(null);
                salarySummary.setOperator("system");
                summaryHashMap.put(salary.getUserId(), salarySummary);
            }

            boolean isBaseSalary = salary.getName().equalsIgnoreCase("基本工资");
            if (isBaseSalary) {
                salarySummary.setBaseSalary(salary.getAmount());
                salarySummary.setLaborDays(salary.getLaborDays());
            }
            boolean isPieceSalary = salary.getName().equalsIgnoreCase("计件工资");
            if (isPieceSalary) salarySummary.setPieceSalary(salary.getAmount());
            // 更新补贴
            BigDecimal allowance = salarySummary.getAllowance();
            if (!isBaseSalary && !isPieceSalary && salary.getType() == SalaryConfig.SalaryType.increase) {
                allowance = allowance.add(salary.getAmount());
            }
            salarySummary.setAllowance(allowance);
            // 更新扣除
            BigDecimal deduction = salarySummary.getDeduction();
            if (!isBaseSalary && !isPieceSalary && salary.getType() == SalaryConfig.SalaryType.decrease) {
                deduction = deduction.add(salary.getAmount());
            }
            salarySummary.setDeduction(deduction);
            // 更新总金额
            BigDecimal amount = salarySummary.getAmount();
            if (salary.getType() == SalaryConfig.SalaryType.increase) {
                amount = amount.add(salary.getAmount());
            } else if (salary.getType() == SalaryConfig.SalaryType.decrease) {
                amount = amount.subtract(salary.getAmount());
            }
            salarySummary.setAmount(amount);
        }

        List<SalarySummary> salarySummaryList = summaryHashMap.values().stream().toList();
        salarySummaryRepository.saveAll(salarySummaryList);
    }
}
