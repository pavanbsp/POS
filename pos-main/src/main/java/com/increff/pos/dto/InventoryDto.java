package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.flow.InventoryFlow;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.increff.pos.helper.InventoryHelper.convertInventoryFormtoInventoryPojo;
import static com.increff.pos.util.NormalizeFormUtil.normalizeInventoryForm;
import static com.increff.pos.util.ValidateFormUtil.validateInventoryForm;

@Service
public class InventoryDto {
    @Autowired
    private InventoryFlow inventoryFlow;

    public void addInventory(InventoryForm inventoryForm) throws ApiException {
        validateInventoryForm(inventoryForm);
        normalizeInventoryForm(inventoryForm);
        InventoryPojo inventoryPojo = convertInventoryFormtoInventoryPojo(inventoryForm);
        inventoryFlow.addInventory(inventoryPojo, inventoryForm.getBarcode());
    }

    public InventoryData getInventory(int id) throws ApiException {
        return inventoryFlow.getInventory(id);
    }

    public int getInventoryByBarcode(String barcode) throws ApiException {
        return inventoryFlow.getInventoryByBarcode(barcode);
    }

    public List<InventoryData> getAllInventory() throws ApiException {
        return inventoryFlow.getAllInventory();
    }

    public void updateInventory(int id, InventoryForm inventoryForm) throws ApiException {
        validateInventoryForm(inventoryForm);
        normalizeInventoryForm(inventoryForm);
        InventoryPojo inventoryPojo = convertInventoryFormtoInventoryPojo(inventoryForm);
        inventoryFlow.updateInventory(id, inventoryPojo, inventoryForm.getBarcode());
    }

}
