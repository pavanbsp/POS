package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "OrderItemsPojo", uniqueConstraints = {@UniqueConstraint(columnNames = {"orderId", "productId"})}, indexes = {@Index(name = "orderIdIndex", columnList = "orderId")})
public class OrderItemsPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(nullable = false)
    private int orderId;
    @NotNull
    @Column(nullable = false)
    private int productId;
    @NotNull
    @Column(nullable = false)
    private int quantity;
    @NotNull
    @Column(nullable = false)
    private double sellingPrice;

}
