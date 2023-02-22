package com.increff.pos.api;

import com.increff.pos.config.AbstractUnitTest;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.InventoryPojo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import static com.increff.pos.util.DummyPojoUtil.GetInventoryPojo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class InventoryApiTest extends AbstractUnitTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Autowired
    private InventoryApi inventoryApi;

    @Test
    public void testAdd() throws ApiException {
        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryApi.addInventory(inventoryPojo);

        InventoryPojo inventoryPojoAdded = inventoryApi.getAllInventory().get(0);
        assertEquals(inventoryPojo.getId(), inventoryPojoAdded.getId());
        assertEquals(inventoryPojo.getQuantity(), inventoryPojoAdded.getQuantity());

        InventoryPojo inventoryPojoExists = GetInventoryPojo();
        inventoryApi.addInventory(inventoryPojoExists);
    }

    @Test
    public void testGet() throws ApiException {
        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryApi.addInventory(inventoryPojo);

        int id = inventoryApi.getAllInventory().get(0).getId();
        InventoryPojo inventoryPojoAdded = inventoryApi.getInventory(id);
        assertEquals(inventoryPojo.getId(), inventoryPojoAdded.getId());
        assertEquals(inventoryPojo.getQuantity(), inventoryPojoAdded.getQuantity());
    }

    @Test
    public void testUpdateInventory() throws ApiException {
        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryApi.addInventory(inventoryPojo);

        int id = inventoryApi.getAllInventory().get(0).getId();
        InventoryPojo inventoryPojoAdded = GetInventoryPojo();
        inventoryPojo.setQuantity(111);
        inventoryApi.updateInventory(id, inventoryPojoAdded);

        InventoryPojo inventoryPojoUpdated = inventoryApi.getInventory(id);
        assertEquals(inventoryPojoAdded.getQuantity(), inventoryPojoUpdated.getQuantity());
    }

    @Test
    public void testUpdateInventoryBarcode() throws ApiException {
        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryApi.addInventory(inventoryPojo);

        InventoryPojo inventoryPojo1 = GetInventoryPojo();
        inventoryPojo1.setId(2);
        inventoryApi.addInventory(inventoryPojo1);

        int id = inventoryApi.getAllInventory().get(0).getId();
        InventoryPojo inventoryPojoAdded = GetInventoryPojo();
        inventoryPojoAdded.setId(2);

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Cannot update barcode for this product");
        inventoryApi.updateInventory(id, inventoryPojoAdded);
    }

    @Test
    public void testGetCheckInventory() throws ApiException {
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Inventory with given ID does not exist, id: 1");
        inventoryApi.getCheckInventory(1);
    }
}