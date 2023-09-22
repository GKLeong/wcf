package com.wcf.server.service;

import com.wcf.server.model.*;
import com.wcf.server.repository.DormitoryExpenseRepository;
import com.wcf.server.repository.DormitoryIndividualExpenseRepository;
import com.wcf.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DormitoryExpenseService {
    private final DormitoryExpenseRepository dormitoryExpenseRepository;
    private final DormitoryRecordService dormitoryRecordService;
    private final DormitoryService dormitoryService;
    private final DormitoryOccupancyService dormitoryOccupancyService;
    private final DormitoryIndividualExpenseRepository dormitoryIndividualExpenseRepository;

    @Autowired
    public DormitoryExpenseService(DormitoryExpenseRepository dormitoryExpenseRepository,
                                   DormitoryRecordService dormitoryRecordService,
                                   DormitoryService dormitoryService,
                                   DormitoryOccupancyService dormitoryOccupancyService,
                                   DormitoryIndividualExpenseRepository dormitoryIndividualExpenseRepository) {
        this.dormitoryExpenseRepository = dormitoryExpenseRepository;
        this.dormitoryRecordService = dormitoryRecordService;
        this.dormitoryService = dormitoryService;
        this.dormitoryOccupancyService = dormitoryOccupancyService;
        this.dormitoryIndividualExpenseRepository = dormitoryIndividualExpenseRepository;
    }

    public List<DormitoryExpense> findAll() {
        return dormitoryExpenseRepository.findAll();
    }

    @Transactional
    public void generate(Date date) {
        List<DormitoryExpense> allDEList = new ArrayList<>();
        DormitoryExpense dormitoryExpense;

        List<DormitoryIndividualExpense> allDIEList = new ArrayList<>(); // 用来保存所有数据
        List<DormitoryIndividualExpense> dormitoryIndividualExpenseList; // 用来临时保存单个宿舍的数据
        DormitoryIndividualExpense dormitoryIndividualExpense;

        Date billDate = DateUtils.generateBillDate(date);
        Date nextBillDate = DateUtils.getNextMonthFirstDay(billDate);
        List<Dormitory> dormitoryList = dormitoryService.findAllByDeletedIsFalse();

        for (Dormitory d : dormitoryList) {
            DormitoryRecord record = dormitoryRecordService.findByBillDate(d.getId(), billDate);
            DormitoryRecord nextRecord = dormitoryRecordService.findByBillDate(d.getId(), nextBillDate);
            List<DormitoryOccupancy> dormitoryOccupancyList = dormitoryOccupancyService.findAllByDormitoryIdAndBillDate(d.getId(), billDate, nextBillDate);

            // 宿舍费用
            dormitoryExpense = new DormitoryExpense();
            dormitoryExpense.setBillMonth(billDate);
            dormitoryExpense.setDormitoryId(d.getId());
            dormitoryExpense.setOccupants(dormitoryOccupancyList.size());
            dormitoryExpense.setLastMonthWaterReading(record.getWater());
            dormitoryExpense.setCurrentMonthWaterReading(nextRecord.getWater());
            dormitoryExpense.setWaterUsage();
            dormitoryExpense.setWaterPrice(d.getWaterPrice());
            dormitoryExpense.setWaterCost();
            dormitoryExpense.setLastMonthElectricityReading(record.getElectricity());
            dormitoryExpense.setCurrentMonthElectricityReading(nextRecord.getElectricity());
            dormitoryExpense.setElectricityUsage();
            dormitoryExpense.setElectricityPrice(d.getElectricityPrice());
            dormitoryExpense.setElectricityCost();
            dormitoryExpense.setTotalCost();
            allDEList.add(dormitoryExpense);

            //  个人分摊费用
            dormitoryIndividualExpenseList = new ArrayList<>();
            BigDecimal dormitoryDue = new BigDecimal(dormitoryExpense.getTotalCost().toString());
            int daysResided = 0;
            // 初始化个人分摊数据, 并计算所有人总居住天数daysResided和补贴减免后的宿舍费用dormitoryDue
            for (DormitoryOccupancy o : dormitoryOccupancyList) {
                dormitoryIndividualExpense = new DormitoryIndividualExpense();
                dormitoryIndividualExpense.setBillMonth(billDate);
                dormitoryIndividualExpense.setDormitoryId(d.getId());
                dormitoryIndividualExpense.setUserId(o.getUserId());
                if (o.getCheckInDate().compareTo(billDate) >= 0)
                    dormitoryIndividualExpense.setCheckInDate(o.getCheckInDate());
                if (o.getCheckOutDate() != null && o.getCheckOutDate().compareTo(nextBillDate) < 0)
                    dormitoryIndividualExpense.setCheckOutDate(o.getCheckOutDate());
                // 设置好入住日期后自动计算居住天数
                dormitoryIndividualExpense.setDaysResided();
                // 设置好居住天数后自动计算个人补贴
                dormitoryIndividualExpense.setSubsidy();
                dormitoryIndividualExpense.setDormitoryCost(dormitoryExpense.getTotalCost());

                dormitoryIndividualExpenseList.add(dormitoryIndividualExpense);
                daysResided += dormitoryIndividualExpense.getDaysResided();
                dormitoryDue = dormitoryDue.subtract(dormitoryIndividualExpense.getSubsidy());
            }

            // 计算并设置每个人的分摊费用
            for (DormitoryIndividualExpense ie : dormitoryIndividualExpenseList) {
                ie.setDormitoryDue(dormitoryDue);
                // 个人均摊 = 宿舍补贴减免后的费用 * 个人居住天数 / 所有人居住天数
                BigDecimal individualShare = dormitoryDue;
                individualShare = individualShare.multiply(new BigDecimal(ie.getDaysResided()));
                individualShare = individualShare.divide(new BigDecimal(daysResided), 2, RoundingMode.HALF_UP);
                ie.setIndividualShare(individualShare);

                allDIEList.add(ie);
            }
        }

        dormitoryExpenseRepository.saveAll(allDEList);
        dormitoryIndividualExpenseRepository.saveAll(allDIEList);
    }
}
