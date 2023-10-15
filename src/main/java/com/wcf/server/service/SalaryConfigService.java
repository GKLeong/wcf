package com.wcf.server.service;

import com.wcf.server.model.SalaryConfig;
import com.wcf.server.model.User;
import com.wcf.server.repository.SalaryConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.wcf.server.utils.DateUtils.calculateDaysDifference;
import static com.wcf.server.utils.DateUtils.dateFormat;

@Service
public class SalaryConfigService {
    private final SalaryConfigRepository salaryConfigRepository;
    private final UserService userService;

    @Autowired
    public SalaryConfigService(SalaryConfigRepository salaryConfigRepository,
                               UserService userService) {
        this.salaryConfigRepository = salaryConfigRepository;
        this.userService = userService;
    }

    public List<SalaryConfig> findAllByUserId(Long userId) {
        return salaryConfigRepository.findAllByUserId(userId);
    }

    public List<Object> getIndex() {
        HashMap<Object, Object> countMap = salaryConfigRepository.getCountGroupByUserId().stream()
                .collect(HashMap::new, (map, pair) -> map.put(pair[0], pair[1]), HashMap::putAll);
        List<Object> allData = new ArrayList<>();
        userService.findAllByDeletedIsFalse().forEach(user -> {
            HashMap<String, Object> data = new HashMap<>();
            data.put("departmentId", user.getDepartmentId());
            data.put("department", user.getDepartment());
            data.put("userId", user.getId());
            data.put("user", user.getName());
            data.put("count", countMap.get(user.getId()));
            allData.add(data);
        });

        return allData;
    }

    public SalaryConfig add(Long userId, String name, String salaryType, Integer cycle, String cycleUnit, BigDecimal amount, Boolean isDailyConversion, Boolean isRealTime, Date effectiveDate, String notes) {
        SalaryConfig salaryConfig = new SalaryConfig();
        User user = userService.findByIdAndDeletedIsFalse(userId);
        salaryConfig.setUserId(userId);
        salaryConfig.setUser(user);
        salaryConfig.setName(name);
        salaryConfig.setType(SalaryConfig.SalaryType.valueOf(salaryType));
        salaryConfig.setCycle(cycle);
        salaryConfig.setCycleUnit(SalaryConfig.CycleUnit.valueOf(cycleUnit));
        salaryConfig.setAmount(amount);
        salaryConfig.setIsDailyConversion(isDailyConversion);
        salaryConfig.setIsRealTime(isRealTime);
        salaryConfig.setIsEffective(shouldEffective(userId, name, effectiveDate));
        if (salaryConfig.getIsEffective()) disableAll(userId, name); // 当前规则生效前让所有同名规则失效
        salaryConfig.setEffectiveDate(effectiveDate);
        salaryConfig.setNotes(notes);
        return salaryConfigRepository.save(salaryConfig);
    }

    public Boolean shouldEffective(Long userId, String name, Date effectiveDate) {
        Date today = dateFormat(LocalDate.now().toString());
        if (calculateDaysDifference(today, effectiveDate) > 0) return false; // 生效时间在未来

        SalaryConfig latestEffectiveData = salaryConfigRepository.findLatestEffective(userId, name);
        if (latestEffectiveData == null) return true; // 找不到比当前时间早的已生效的数据

        // lastDataEDate - curDataEDate <=0 已生效数据的生效时间比当前数据的生效时间早, 当前数据生效; 时间相同, 默认当前数据为新数据代替老数据, 当前数据生效.
        return calculateDaysDifference(effectiveDate, latestEffectiveData.getEffectiveDate()) <= 0;
    }

    /* 让所有同名规则失效 */
    public void disableAll(Long userId, String name) {
        salaryConfigRepository.disableAll(userId, name);
    }
}
