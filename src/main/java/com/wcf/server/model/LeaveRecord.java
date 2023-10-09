package com.wcf.server.model;

import com.wcf.server.utils.DateUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "leave_records")
public class LeaveRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "reason")
    private String reason;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;

    public void setEndDate(Date date) {
        endDate = date;
        if (startDate != null) duration = DateUtils.calculateDaysDifference(startDate, endDate);
    }

    public String getUser() {
        return user.getName();
    }
}
