package com.wcf.server.repository;

import com.wcf.server.model.Scrapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapyRepository extends JpaRepository<Scrapy, Long> {
    List<Scrapy> findByArchive(boolean archive);
}
