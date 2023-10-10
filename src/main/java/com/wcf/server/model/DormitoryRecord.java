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

    @Column(name = "dormitory_id")
    private Long dormitoryId;
    @ManyToOne
    @JoinColumn(name = "dormitory_id", insertable = false, updatable = false)
    private Dormitory dormitory;
    private Date billDate;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "water", precision = 10, scale = 2, nullable = false)
    private BigDecimal water;

    @Column(name = "electricity", precision = 10, scale = 2, nullable = false)
    private BigDecimal electricity;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;

    public String getDormitory() {
        if (dormitory == null) return null;
        return dormitory.getRoomNumber();
    }
}