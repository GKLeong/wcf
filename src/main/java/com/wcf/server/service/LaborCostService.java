package com.wcf.server.service;

import com.wcf.server.model.LaborCost;
import com.wcf.server.repository.LaborCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LaborCostService {
    private final LaborCostRepository laborCostRepository;

    @Autowired
    public LaborCostService(LaborCostRepository laborCostRepository) {
        this.laborCostRepository = laborCostRepository;
    }

    public List<LaborCost> findAll() {
        return laborCostRepository.findAll();
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

    public void deleteById(Long id) {
        laborCostRepository.deleteById(id);
    }
}
