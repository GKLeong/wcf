package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "loan_record")
public class LoanRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "purpose", nullable = false)
    private String purpose;

    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "paid")
    private Boolean paid = false;

    @Column(name = "posted")
    private Boolean posted = false;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @ManyToOne
    @JoinColumn(name = "creator_id", insertable = false, updatable = false)
    private User creator;

    @Column(name = "notes")
    private String notes;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;

    public String getUser() {
        if (user == null) return null;
        return user.getName();
    }

    public String getCreator() {
        // if (creator == null) return null; 创建数据的同时也设置了creator, 所以insert数据后creator不存在null的情况, 可以直接调用
        return creator.getName();
    }

}