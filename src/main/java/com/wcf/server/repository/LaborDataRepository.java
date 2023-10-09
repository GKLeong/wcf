package com.wcf.server.repository;

import com.wcf.server.model.LaborData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaborDataRepository extends JpaRepository<LaborData, Long> {
    List<LaborData> findByArchiveDateIsNull();
}
