package com.increff.pos.api;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryApi {

    @Autowired
    private InventoryDao inventoryDao;

    public void addInventory(InventoryPojo inventoryPojo) throws ApiException {
        InventoryPojo inventoryPojoExists = inventoryDao.selectInventory(inventoryPojo.getId());
        if (inventoryPojoExists != null) {
            inventoryPojoExists.setQuantity(inventoryPojo.getQuantity() + inventoryPojoExists.getQuantity());
        } else {
            inventoryDao.insertInventory(inventoryPojo);
        }
    }

    public InventoryPojo getInventory(int id) throws ApiException {
        return getCheckInventory(id);
    }

    public List<InventoryPojo> getAllInventory() throws ApiException {
        return inventoryDao.selectAllInventory();
    }

    public void updateInventory(int id, InventoryPojo inventoryPojo) throws ApiException {
        InventoryPojo inventoryPojoUpdated = getCheckInventory(id);
        if (inventoryPojo.getId() != inventoryPojoUpdated.getId()) {
            throw new ApiException("Cannot update barcode for this product");
        }
        inventoryPojoUpdated.setQuantity(inventoryPojo.getQuantity());
    }

    public InventoryPojo getCheckInventory(int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.selectInventory(id);
        if (inventoryPojo == null) {
            throw new ApiException("Inventory with given ID does not exist, id: " + id);
        }
        return inventoryPojo;
    }
}
