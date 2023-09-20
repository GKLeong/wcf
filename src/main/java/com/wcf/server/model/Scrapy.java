package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "scrapy")
public class Scrapy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_recorded")
    private Date dateRecorded;

    @Column(name = "package_number")
    private int packageNumber;

    @Column(name = "weight_kg", precision = 10, scale = 3)
    private BigDecimal weightKg;

    @Column(name = "total_package")
    private int totalPackage = 1;

    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "archive")
    private boolean archive = false;

    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}

