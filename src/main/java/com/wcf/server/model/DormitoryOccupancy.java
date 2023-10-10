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

    @Column(name = "dormitory_id")
    private Long dormitoryId;

    @ManyToOne
    @JoinColumn(name = "dormitory_id", insertable = false, updatable = false)
    private Dormitory dormitory;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

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

    public String getDormitory() {
        if (dormitory == null) return null;
        return dormitory.getRoomNumber();
    }

    public String getUser() {
        if (user == null) return null;
        return user.getName();
    }
}
