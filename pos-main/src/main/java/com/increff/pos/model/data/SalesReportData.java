package com.increff.pos.model.data;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Setter
@Getter
public class SalesReportData {
    private String brand;
    private String category;
    private int quantity;
    private double revenue;

    //PARAM CONSTRUCTOR
    public SalesReportData(String brand, String category, Integer quantity, Double revenue) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        this.brand = brand;
        this.category = category;
        this.quantity = quantity;
        this.revenue = revenue;
    }
}
