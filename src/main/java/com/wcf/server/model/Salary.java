package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bill_date", nullable = false)
    private Date billDate;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "salary_config_id")
    private Long salaryConfigId;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SalaryConfig.SalaryType type;

    @Column(name = "cycle")
    private Integer cycle;

    @Enumerated(EnumType.STRING)
    @Column(name = "cycle_unit")
    private SalaryConfig.CycleUnit cycleUnit;

    @Column(name = "is_daily_conversion")
    private Boolean isDailyConversion;

    @Column(name = "labor_days")
    private Integer laborDays;

    @Column(name = "is_real_time")
    private Boolean isRealTime;

    @Column(name = "amount", nullable = false, precision = 7)
    private BigDecimal amount;

    @Column(name = "notes")
    private String notes;

    @Column(name = "operator_id")
    private Long operatorId;

    @Column(name = "operator", nullable = false)
    private String operator;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    public String getUser() {
        return user.getName();
    }

    public User getUserEntity() {
        return user;
    }
}
