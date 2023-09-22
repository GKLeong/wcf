package com.wcf.server.repository;

import com.wcf.server.model.DormitoryOccupancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DormitoryOccupancyRepository extends JpaRepository<DormitoryOccupancy, Long> {
}
