package com.wcf.server.repository;

import com.wcf.server.model.DormitoryOccupancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DormitoryOccupancyRepository extends JpaRepository<DormitoryOccupancy, Long> {
    @Query("SELECT d FROM DormitoryOccupancy d WHERE d.dormitoryId=:dormitoryId AND d.checkInDate<=:nextBillDate AND (d.checkOutDate>:billDate OR d.checkOutDate IS NULL)")
    List<DormitoryOccupancy> findAllByDormitoryIdAndBillDate(Long dormitoryId, Date billDate, Date nextBillDate);
}
