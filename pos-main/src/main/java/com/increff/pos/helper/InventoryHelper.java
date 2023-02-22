package com.increff.pos.helper;

import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;

public class InventoryHelper {
    public static InventoryPojo convertInventoryFormtoInventoryPojo(InventoryForm inventoryForm) {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }

    public static InventoryData convertInventoryPojoToInventoryData(InventoryPojo inventoryPojo, ProductPojo productPojo) {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setBarcode(productPojo.getBarcode());
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        inventoryData.setId(inventoryPojo.getId());
        inventoryData.setName(productPojo.getName());
        return inventoryData;
    }

}
