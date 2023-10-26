package com.wcf.server.repository;

import com.wcf.server.model.SalaryConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryConfigRepository extends JpaRepository<SalaryConfig, Long> {
    List<SalaryConfig> findAllByUserId(Long userId);

    @Query("SELECT sc FROM SalaryConfig sc WHERE sc.userId=:userId AND sc.name=:name AND sc.isEffective = TRUE AND sc.effectiveDate<=CURRENT_DATE ORDER BY sc.effectiveDate DESC LIMIT 1")
    SalaryConfig findLatestEffective(Long userId, String name);

    @Modifying
    @Query("UPDATE SalaryConfig sc SET sc.isEffective = FALSE WHERE sc.userId=:userId AND sc.name=:name")
    void disableAll(Long userId, String name);

    @Query("SELECT sc.userId,COUNT(sc.id) FROM SalaryConfig sc WHERE sc.isEffective = TRUE GROUP BY sc.userId")
    List<Object[]> getCountGroupByUserId();

    List<SalaryConfig> findAllByUserIdInAndIsEffectiveIsTrue(List<Long> userIds);

    List<SalaryConfig> findAllByNameAndIsEffectiveIsTrue(String name);
}
