package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "BrandPojo", uniqueConstraints = {@UniqueConstraint(columnNames = {"brand", "category"})},
        indexes = {@Index(name = "brandIndex", columnList = "brand"), @Index(name = "categoryIndex", columnList = "category")})
public class BrandPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(nullable = false)
    private String brand;
    @NotNull
    @Column(nullable = false)
    private String category;
}