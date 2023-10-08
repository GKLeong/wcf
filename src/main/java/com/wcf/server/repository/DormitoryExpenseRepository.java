package com.wcf.server.repository;

import com.wcf.server.model.DormitoryExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DormitoryExpenseRepository extends JpaRepository<DormitoryExpense, Long> {

    void deleteByBillMonth(Date billDate);
}
