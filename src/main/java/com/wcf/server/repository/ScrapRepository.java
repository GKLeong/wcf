package com.wcf.server.repository;

import com.wcf.server.dto.ScrapDTO;
import com.wcf.server.model.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findByArchive(boolean archive);

    @Query("SELECT " +
            "NEW com.wcf.server.dto.ScrapDTO(" +
            "SUM(s.weightKg) AS totalWeightKg, " +
            "SUM(s.totalPackage) AS totalPackage" +
            ") " +
            "FROM Scrap s WHERE s.dateRecorded >= :f AND s.dateRecorded <= :e AND s.archive = false")
    ScrapDTO sumDataBeforeArchive(@Param("f") Date f, @Param("e") Date e);

    @Modifying
    @Query("UPDATE Scrap s SET s.archive=true WHERE s.dateRecorded >= :f AND s.dateRecorded <= :e")
    void archive(@Param("f") Date f, @Param("e") Date e);
}
