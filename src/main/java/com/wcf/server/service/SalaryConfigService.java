package com.wcf.server.service;

import com.wcf.server.model.SalaryConfig;
import com.wcf.server.repository.SalaryConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryConfigService {
    private final SalaryConfigRepository salaryConfigRepository;

    @Autowired
    public SalaryConfigService(SalaryConfigRepository salaryConfigRepository) {
        this.salaryConfigRepository = salaryConfigRepository;
    }

    public List<SalaryConfig> findAll() {
        return salaryConfigRepository.findAll();
    }
}
