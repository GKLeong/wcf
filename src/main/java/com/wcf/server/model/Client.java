package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "contact_person", length = 15, nullable = false)
    private String contactPerson;
    @Column(name = "phone", length = 15)
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "payment_percentage", precision = 3, nullable = false)
    private BigDecimal paymentPercentage;
    @Column(name = "comments")
    private String comments;
    @Column(name = "effective")
    private Boolean effective;
    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;
    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}
