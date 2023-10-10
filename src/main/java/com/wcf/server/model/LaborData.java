package com.wcf.server.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "labor_data")
public class LaborData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "date")
    private Date date;

    @Column(name = "labor_cost_id", nullable = false)
    private Long laborCostId;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "quantity", precision = 8, nullable = false)
    private BigDecimal quantity;

    @Column(name = "frequency", precision = 1, nullable = false)
    private BigDecimal frequency;

    @Column(name = "unit_price", precision = 6, scale = 4, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "amount", precision = 10, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "producer")
    private String producer;

    @Column(name = "card_group")
    private String cardGroup;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "archive_date")
    private Date archiveDate;

    @ManyToOne
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;

    public String getDepartment() {
        if (department == null) return null;
        return department.getName();
    }

}

