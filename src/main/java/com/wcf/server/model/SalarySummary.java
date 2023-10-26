package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "salary_summary")
public class SalarySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bill_date", nullable = false)
    private Date billDate;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "account_number", length = 50)
    private String accountNumber;

    @Column(name = "labor_days")
    private Integer laborDays;

    @Column(name = "base_salary", precision = 8, nullable = false)
    private BigDecimal baseSalary;

    @Column(name = "piece_salary", precision = 8, nullable = false)
    private BigDecimal pieceSalary;

    @Column(name = "allowance", precision = 8, nullable = false)
    private BigDecimal allowance;

    @Column(name = "deduction", precision = 8, nullable = false)
    private BigDecimal deduction;

    @Column(name = "amount", precision = 8, nullable = false)
    private BigDecimal amount;

    @Column(name = "paid", precision = 8, nullable = false)
    private BigDecimal paid;

    @Column(name = "finish")
    private Boolean finish;

    @Column(name = "notes")
    private String notes;

    @Column(name = "operator_id")
    private Long operatorId;

    @Column(name = "operator")
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
}
