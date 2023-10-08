package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "dormitory_occupancy")
public class DormitoryOccupancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long dormitoryId;

    @Transient
    private String dormitory;

    private Long userId;

    @Transient
    private String user;

    @Column(name = "check_in_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkInDate;

    @Column(name = "check_out_date")
    @Temporal(TemporalType.DATE)
    private Date checkOutDate;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}
