package com.wcf.server.service;

import com.wcf.server.model.DormitoryIndividualExpense;
import com.wcf.server.repository.DormitoryIndividualExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormitoryIndividualExpenseService {
    private DormitoryIndividualExpenseRepository dormitoryIndividualExpenseRepository;

    @Autowired
    public DormitoryIndividualExpenseService(DormitoryIndividualExpenseRepository dormitoryIndividualExpenseRepository) {
        this.dormitoryIndividualExpenseRepository = dormitoryIndividualExpenseRepository;
    }

    public List<DormitoryIndividualExpense> findAll() {
        return dormitoryIndividualExpenseRepository.findAll();
    }
}
