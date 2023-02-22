package com.increff.pos.flow;

import com.increff.pos.api.InventoryApi;
import com.increff.pos.api.ProductApi;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.InventoryHelper.convertInventoryPojoToInventoryData;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryFlow {

    @Autowired
    private InventoryApi inventoryApi;
    @Autowired
    private ProductApi productApi;

    public void addInventory(InventoryPojo inventoryPojo, String barcode) throws ApiException {
        ProductPojo productPojo = productApi.getCheckByBarcode(barcode);
        inventoryPojo.setId(productPojo.getId());
        inventoryApi.addInventory(inventoryPojo);
    }

    public InventoryData getInventory(int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryApi.getInventory(id);
        ProductPojo productPojo = productApi.getCheckProductById(id);
        return convertInventoryPojoToInventoryData(inventoryPojo, productPojo);
    }

    public int getInventoryByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productApi.getCheckByBarcode(barcode);
        InventoryPojo inventoryPojo = inventoryApi.getInventory(productPojo.getId());
        return inventoryPojo.getQuantity();
    }

    public List<InventoryData> getAllInventory() throws ApiException {
        List<InventoryPojo> inventoryPojoList = inventoryApi.getAllInventory();
        List<InventoryData> inventoryDataList = new ArrayList<>();
        for (InventoryPojo inventoryPojo : inventoryPojoList) {
            ProductPojo productPojo = productApi.getCheckProductById(inventoryPojo.getId());
            inventoryDataList.add(convertInventoryPojoToInventoryData(inventoryPojo, productPojo));
        }
        return inventoryDataList;
    }

    public void updateInventory(int id, InventoryPojo inventoryPojo, String barcode) throws ApiException {
        ProductPojo productPojo = productApi.getCheckByBarcode(barcode);
        inventoryPojo.setId(productPojo.getId());
        inventoryApi.updateInventory(id, inventoryPojo);
    }
}
