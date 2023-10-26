package com.wcf.server.repository;

import com.wcf.server.model.SalarySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SalarySummaryRepository extends JpaRepository<SalarySummary, Long> {
    List<SalarySummary> findAllByBillDate(Date billDate);

    List<SalarySummary> findAllByUserId(Long userId);

    List<SalarySummary> findAllByBillDateAndUserId(Date billDate, Long userId);

    void deleteAllByBillDateAndOperatorIdAndOperator(Date billDate, Long operatorId, String operator);
}
