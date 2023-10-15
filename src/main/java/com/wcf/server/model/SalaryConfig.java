package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "salary_config")
public class SalaryConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SalaryType type;

    @Column(name = "cycle", nullable = false)
    private Integer cycle;

    @Enumerated(EnumType.STRING)
    @Column(name = "cycle_unit", nullable = false)
    private CycleUnit cycleUnit;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "is_daily_conversion")
    private Boolean isDailyConversion;

    @Column(name = "is_real_time")
    private Boolean isRealTime;

    @Column(name = "is_effective")
    private Boolean isEffective;

    @Column(name = "effective_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Timestamp createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Timestamp updateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public enum SalaryType {
        INCREASE,
        DECREASE
    }

    public enum CycleUnit {
        DAY,
        MONTH,
        YEAR
    }

    public String getUser() {
        return user.getName();
    }
}