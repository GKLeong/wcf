package com.wcf.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "product")
    private String product;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "quantity", precision = 7)
    private BigDecimal quantity;
    @Column(name = "price", precision = 5, scale = 3)
    private BigDecimal price;
    @Column(name = "amount", precision = 8, scale = 3)
    private BigDecimal amount;
    @Column(name = "operator_id")
    private Long operatorId;
    @Column(name = "operator")
    private String operator;
    @Column(name = "effective")
    private Boolean effective;
    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;
    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}
