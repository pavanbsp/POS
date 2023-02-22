package com.increff.pos.controller;

import com.increff.pos.dto.InventoryDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.form.InventoryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class InventoryApiController {
    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value = "Adds an inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void addInventory(@RequestBody InventoryForm inventoryForm) throws ApiException {
        inventoryDto.addInventory(inventoryForm);
    }

    @ApiOperation(value = "Gets an inventory by ID")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData getInventory(@PathVariable int id) throws ApiException {
        return inventoryDto.getInventory(id);
    }

    @ApiOperation(value = "Gets an inventory by ID")
    @RequestMapping(path = "/api/inventory/barcode/{barcode}", method = RequestMethod.GET)
    public int getInventoryByBarcode(@PathVariable String barcode) throws ApiException {
        return inventoryDto.getInventoryByBarcode(barcode);
    }

    @ApiOperation(value = "Gets list of all items in inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAllInventory() throws ApiException {
        return inventoryDto.getAllInventory();
    }

    @ApiOperation(value = "Updates a inventory")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void updateInventory(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
        inventoryDto.updateInventory(id, f);
    }
}
