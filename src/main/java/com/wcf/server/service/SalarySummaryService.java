package com.wcf.server.service;

import com.wcf.server.model.SalarySummary;
import com.wcf.server.model.User;
import com.wcf.server.repository.SalarySummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalarySummaryService {
    private final SalarySummaryRepository salarySummaryRepository;
    private final UserService userService;

    @Autowired
    public SalarySummaryService(SalarySummaryRepository salarySummaryRepository,
                                UserService userService) {
        this.userService = userService;
        this.salarySummaryRepository = salarySummaryRepository;
    }

    public List<SalarySummary> findAllByBillDate(Date billDate, String name) {
        User user = null;
        if (name != null) user = userService.findByUsernameAndDeletedIsFalse(name);

        if (billDate != null && name != null) {
            return salarySummaryRepository.findAllByBillDateAndUserId(billDate, user.getId());
        } else if (billDate != null) {
            return salarySummaryRepository.findAllByBillDate(billDate);
        } else if (name != null) {
            return salarySummaryRepository.findAllByUserId(user.getId());
        } else {
            return salarySummaryRepository.findAll();
        }
    }
}
