package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemsForm {
    private int orderId;
    private int quantity;
    private String barcode;
    private double sellingPrice;
}
