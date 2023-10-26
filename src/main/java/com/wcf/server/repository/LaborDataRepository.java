package com.wcf.server.repository;

import com.wcf.server.model.LaborData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface LaborDataRepository extends JpaRepository<LaborData, Long> {
    List<LaborData> findByArchiveDateIsNull();

    @Query("SELECT SUM(ld.amount) FROM LaborData ld WHERE ld.userId = :userId AND ld.date >= :from AND ld.date < :end")
    BigDecimal sumAmountByUserIdAndDate(Long userId, Date from, Date end);
}
