package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "dormitory_record")
public class DormitoryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long dormitoryId;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "water", precision = 10, scale = 2, nullable = false)
    private BigDecimal water;

    @Column(name = "electricity", precision = 10, scale = 2, nullable = false)
    private BigDecimal electricity;
}