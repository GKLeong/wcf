package com.wcf.server.service;

import com.wcf.server.model.ScrapStatistic;
import com.wcf.server.repository.ScrapStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapStatisticService {
    private final ScrapStatisticRepository scrapStatisticRepository;

    @Autowired
    public ScrapStatisticService(ScrapStatisticRepository scrapStatisticRepository) {
        this.scrapStatisticRepository = scrapStatisticRepository;
    }

    public void save(ScrapStatistic scrapStatistic) {
        scrapStatisticRepository.save(scrapStatistic);
    }

    public List<ScrapStatistic> findAll() {
        return scrapStatisticRepository.findAll();
    }
}
