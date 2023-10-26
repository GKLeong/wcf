package com.wcf.server.repository;

import com.wcf.server.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    void deleteAllBySalaryConfigIdAndBillDateAndOperatorIdAndOperator(Long salaryConfigId, Date billDate, Long operatorId, String operator);

    List<Salary> findAllByBillDate(Date billDate);
}
