package com.wcf.server.model;

import com.wcf.server.utils.DateUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Entity
@Data
@Table(name = "dormitory_individual_expenses")
public class DormitoryIndividualExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bill_month", nullable = false)
    private Date billMonth;

    @Column(name = "dormitory_id", nullable = false)
    private Long dormitoryId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "check_in_date")
    private Date checkInDate;

    @Column(name = "check_out_date")
    private Date checkOutDate;

    @Column(name = "days_resided", nullable = false)
    private Integer daysResided;

    @Column(name = "subsidy", precision = 10, scale = 2)
    private BigDecimal subsidy;

    @Column(name = "dormitory_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal dormitoryCost;

    @Column(name = "dormitory_due", nullable = false, precision = 10, scale = 2)
    private BigDecimal dormitoryDue;

    @Column(name = "individual_share", nullable = false, precision = 10, scale = 2)
    private BigDecimal individualShare;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    public void setDaysResided() {
        if (checkInDate == null && checkOutDate == null) {
            daysResided = 30;
        } else if (checkOutDate == null) {
            Date finalDay = DateUtils.getNextMonthFirstDay(checkInDate);
            daysResided = DateUtils.calculateDaysDifference(checkInDate, finalDay);
        } else if (checkInDate == null) {
            Date firstDay = DateUtils.getMonthFirstDay(checkOutDate);
            daysResided = DateUtils.calculateDaysDifference(firstDay, checkOutDate);
        } else {
            daysResided = DateUtils.calculateDaysDifference(checkInDate, checkOutDate);
        }

        if (daysResided > 30) daysResided = 30;
    }

    public void setSubsidy() {
        subsidy = new BigDecimal("50");
        subsidy = subsidy.multiply(new BigDecimal(daysResided.toString()));
        subsidy = subsidy.divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP);
    }
}
