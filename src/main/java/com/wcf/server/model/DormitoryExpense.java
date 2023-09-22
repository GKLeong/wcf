package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "dormitory_expenses")
public class DormitoryExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bill_month", nullable = false)
    private Date billMonth;

    @Column(name = "dormitory_id", nullable = false)
    private Long dormitoryId;

    @Column(name = "occupants", nullable = false)
    private Integer occupants;

    @Column(name = "last_month_water_reading", nullable = false)
    private BigDecimal lastMonthWaterReading;

    @Column(name = "current_month_water_reading", nullable = false)
    private BigDecimal currentMonthWaterReading;

    @Column(name = "water_usage", nullable = false)
    private BigDecimal waterUsage;

    @Column(name = "water_price", nullable = false)
    private BigDecimal waterPrice;

    @Column(name = "water_cost", nullable = false)
    private BigDecimal waterCost;

    @Column(name = "last_month_electricity_reading", nullable = false)
    private BigDecimal lastMonthElectricityReading;

    @Column(name = "current_month_electricity_reading", nullable = false)
    private BigDecimal currentMonthElectricityReading;

    @Column(name = "electricity_usage", nullable = false)
    private BigDecimal electricityUsage;

    @Column(name = "electricity_price", nullable = false)
    private BigDecimal electricityPrice;

    @Column(name = "electricity_cost", nullable = false)
    private BigDecimal electricityCost;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    public void setWaterUsage() {
        waterUsage = currentMonthWaterReading.subtract(lastMonthWaterReading);
    }

    public void setWaterCost() {
        waterCost = waterUsage.multiply(waterPrice);
    }

    public void setElectricityUsage() {
        electricityUsage = currentMonthElectricityReading.subtract(lastMonthElectricityReading);
    }

    public void setElectricityCost() {
        electricityCost = electricityUsage.multiply(electricityPrice);
    }

    public void setTotalCost() {
        totalCost = waterCost.add(electricityCost);
    }
}
