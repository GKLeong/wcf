package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.model.LoanRecord;
import com.wcf.server.repository.LoanRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LoanRecordService {
    private final LoanRecordRepository loanRecordRepository;
    private final UserService userService;

    @Autowired
    public LoanRecordService(LoanRecordRepository loanRecordRepository,
                             UserService userService) {
        this.loanRecordRepository = loanRecordRepository;
        this.userService=userService;
    }

    public List<LoanRecord> findAll() {
        return loanRecordRepository.findAll();
    }

    public LoanRecord add(Long userId, BigDecimal amount, String purpose, Date paymentDate, String paymentMethod, String notes) {
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setUserId(userId);
        loanRecord.setAmount(amount);
        loanRecord.setPurpose(purpose);
        loanRecord.setPaymentDate(paymentDate);
        loanRecord.setPaymentMethod(paymentMethod);
        loanRecord.setCreatorId(userService.getCurrentUser().getId());
        loanRecord.setCreator(userService.getCurrentUser());
        loanRecord.setNotes(notes);
        return loanRecordRepository.save(loanRecord);
    }

    public LoanRecord setPaid(Long id, Boolean paid) {
        LoanRecord loanRecord = loanRecordRepository.findById(id).orElseThrow(() -> new BizException("记录id不存在:" + id));
        loanRecord.setPaid(paid);
        return loanRecordRepository.save(loanRecord);
    }

    public void deleteById(Long id) {
        loanRecordRepository.deleteById(id);
    }
}
