package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "pos_day_sales", indexes = {@Index(name = "dateIndex", columnList = "date")})
public class DailyReportPojo extends AbstractPojo {
    @Id
    private String date;
    @NotNull
    @Column(nullable = false)
    private int invoiced_orders_count;
    @NotNull
    @Column(nullable = false)
    private int invoiced_items_count;
    @NotNull
    @Column(nullable = false)
    private double total_revenue;
}
