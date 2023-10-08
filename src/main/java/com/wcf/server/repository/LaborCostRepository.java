package com.wcf.server.repository;

import com.wcf.server.model.LaborCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaborCostRepository extends JpaRepository<LaborCost, Long> {
    @Query("SELECT lc FROM LaborCost lc " +
            "WHERE (lc.departmentId, lc.action, lc.effectiveDate) IN " +
            "(SELECT lc2.departmentId, lc2.action, MAX(lc2.effectiveDate) FROM LaborCost lc2 " +
            "GROUP BY lc2.departmentId, lc2.action)")
    List<LaborCost> findEffective();
}
