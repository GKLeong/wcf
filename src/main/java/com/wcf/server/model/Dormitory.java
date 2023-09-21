package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "dormitories")
public class Dormitory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "water_price", nullable = false)
    private BigDecimal waterPrice;

    @Column(name = "electricity_price", nullable = false)
    private BigDecimal electricityPrice;

    @Column(name = "lease_start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date leaseStartDate;

    @Column(name = "lease_end_date")
    @Temporal(TemporalType.DATE)
    private Date leaseEndDate;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}