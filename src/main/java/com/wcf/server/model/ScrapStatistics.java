package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "scrap_statistics")
public class ScrapStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "total_weight_kg", precision = 10, scale = 3)
    private BigDecimal totalWeightKg;

    @Column(name = "total_package")
    private int totalPackage;

    @Column(name = "package_weight", precision = 10, scale = 3)
    private BigDecimal packageWeight;

    @Column(name = "total_package_weight", precision = 10, scale = 3)
    private BigDecimal totalPackageWeight;

    @Column(name = "net_weight_kg", precision = 10, scale = 3)
    private BigDecimal netWeightKg;

    @Column(name = "unit_price", precision = 10, scale = 4)
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 10, scale = 4)
    private BigDecimal totalPrice;

    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}
