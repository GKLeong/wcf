package com.wcf.server.service;

import com.wcf.server.model.SalarySummary;
import com.wcf.server.repository.SalarySummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalarySummaryService {
    private final SalarySummaryRepository salarySummaryRepository;

    @Autowired
    public SalarySummaryService(SalarySummaryRepository salarySummaryRepository) {
        this.salarySummaryRepository = salarySummaryRepository;
    }

    public List<SalarySummary> findAllByBillDate(Date billDate) {
        return salarySummaryRepository.findAllByBillDate(billDate);
    }
}
