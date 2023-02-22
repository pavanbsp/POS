package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.config.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.DummyFormUtil.*;
import static com.increff.pos.util.DummyFormUtil.GetInventoryForm;
import static org.junit.Assert.assertEquals;

public class InventoryDtoTest extends AbstractUnitTest {
    @Autowired
    private InventoryDto inventoryDto;

    @Autowired
    private ProductDto productDto;

    @Autowired
    private BrandDto brandDto;

    @Test
    public void testAddInventory() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        InventoryData inventoryData = inventoryDto.getAllInventory().get(0);
        assertEquals(inventoryForm.getBarcode(), inventoryData.getBarcode());
        assertEquals(inventoryForm.getQuantity(), inventoryData.getQuantity());
    }

    @Test
    public void testGetInventory() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        InventoryData inventoryData = inventoryDto.getAllInventory().get(0);
        InventoryForm inventoryForm1 = inventoryDto.getInventory(inventoryData.getId());
        assertEquals(inventoryForm.getBarcode(), inventoryForm1.getBarcode());
        assertEquals(inventoryForm.getQuantity(), inventoryForm1.getQuantity());
    }

    @Test
    public void testGetInventoryByBarcode() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        InventoryData inventoryData = inventoryDto.getAllInventory().get(0);
        int quantity = inventoryDto.getInventoryByBarcode(inventoryData.getBarcode());
        assertEquals(quantity, inventoryForm.getQuantity());
    }

    @Test
    public void testGetAllInventory() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        ProductForm productForm1 = GetProductForm();
        productForm1.setBarcode("testBarcode2");
        productDto.addProduct(productForm1);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        InventoryForm inventoryForm1 = GetInventoryForm();
        inventoryForm1.setBarcode("testBarcode2");
        inventoryForm1.setQuantity(99);
        inventoryDto.addInventory(inventoryForm1);

        List<InventoryData> inventoryDataList = inventoryDto.getAllInventory();
        assertEquals(2, inventoryDataList.size());
    }

    @Test
    public void testUpdateInventory() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        ProductForm productForm1 = GetProductForm();
        productForm1.setBarcode("testBarcode2");
        productDto.addProduct(productForm1);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        int id = inventoryDto.getAllInventory().get(0).getId();
        inventoryForm.setQuantity(111);

        inventoryDto.updateInventory(id, inventoryForm);
        InventoryData inventoryDataUpdated = inventoryDto.getAllInventory().get(0);
        assertEquals(id, inventoryDataUpdated.getId());
        assertEquals(inventoryForm.getBarcode(), inventoryDataUpdated.getBarcode());
        assertEquals(inventoryForm.getQuantity(), inventoryDataUpdated.getQuantity());
    }

}