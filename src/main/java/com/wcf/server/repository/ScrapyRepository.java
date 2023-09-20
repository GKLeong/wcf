package com.wcf.server.repository;

import com.wcf.server.model.Scrapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapyRepository extends JpaRepository<Scrapy, Long> {
}
