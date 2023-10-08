package com.wcf.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 2)
    private String name;
    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;
    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}
