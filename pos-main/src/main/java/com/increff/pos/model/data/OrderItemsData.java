package com.increff.pos.model.data;

import com.increff.pos.model.form.OrderItemsForm;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemsData extends OrderItemsForm {
    private int id;

    private String productName;
}
