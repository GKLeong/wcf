package com.wcf.server.repository;

import com.wcf.server.model.DormitoryIndividualExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DormitoryIndividualExpenseRepository extends JpaRepository<DormitoryIndividualExpense, Long> {
    void deleteByBillMonth(Date billDate);
}
