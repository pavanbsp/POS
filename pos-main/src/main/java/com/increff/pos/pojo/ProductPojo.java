package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ProductPojo", uniqueConstraints = {@UniqueConstraint(columnNames = {"barcode"})}, indexes = {@Index(name = "barcodeIndex", columnList = "barcode"), @Index(name = "brand_category_idIndex", columnList = "brand_category_id")})
public class ProductPojo extends AbstractPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(nullable = false)
    private String barcode;
    @NotNull
    @Column(nullable = false)
    private int brand_category_id;
    @NotNull
    @Column(nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false)
    private double mrp;
}
