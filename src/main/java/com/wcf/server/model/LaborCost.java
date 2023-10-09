package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "labor_cost")
public class LaborCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "price", nullable = false, precision = 10, scale = 4)
    private BigDecimal price;

    @Column(name = "comments")
    private String comments;

    @Column(name = "effective_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @ManyToOne
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;

    public String getDepartment() {
        return department.getName();
    }
}

