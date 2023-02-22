package com.increff.pos.util;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.form.*;

import java.util.ArrayList;
import java.util.List;

public class DummyFormUtil {
    public static BrandForm GetBrandForm() {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("pavan");
        brandForm.setCategory("boina");
        return brandForm;
    }
    public static BrandForm GetBrandForm(String brand, String category) {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(brand);
        brandForm.setCategory(category);
        return brandForm;
    }

    public static ProductForm GetProductForm() {
        ProductForm productForm = new ProductForm();
        productForm.setBarcode("testBarcode");
        productForm.setBrand("pavan");
        productForm.setCategory("boina");
        productForm.setName("testName");
        productForm.setMrp(123);
        return productForm;
    }

    public static ProductForm GetProductForm(String barcode, String brand, String category, String name, double mrp) {
        ProductForm productForm = new ProductForm();
        productForm.setBarcode(barcode);
        productForm.setBrand(brand);
        productForm.setCategory(category);
        productForm.setName(name);
        productForm.setMrp(mrp);
        return productForm;
    }

    public static InventoryForm GetInventoryForm() {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("testBarcode");
        inventoryForm.setQuantity(12345);
        return inventoryForm;
    }

    public static InventoryForm GetInventoryForm(String barcode, int quantity) {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode(barcode);
        inventoryForm.setQuantity(quantity);
        return inventoryForm;
    }

    public static OrderItemsForm GetOrderItemsForm() {
        OrderItemsForm orderItemsForm = new OrderItemsForm();
        orderItemsForm.setBarcode("testbarcode");
        orderItemsForm.setQuantity(12);
        orderItemsForm.setSellingPrice(11);
        return orderItemsForm;
    }

    public static List<OrderItemsForm> GetOrderItemsFormList() {
        List<OrderItemsForm> orderItemsFormList = new ArrayList<>();
        orderItemsFormList.add(GetOrderItemsForm());
        return orderItemsFormList;
    }

    public static UserForm GetUserForm() {
        UserForm userForm = new UserForm();
        userForm.setEmail("pavanboina@gmail.com");
        userForm.setPassword("secret1234");
        userForm.setRole("admin");
        return userForm;
    }

    public static SalesReportForm GetSalesReportForm() throws ApiException {
        SalesReportForm salesReportForm = new SalesReportForm();
        salesReportForm.setFrom("2023-01-15T00:00:00.000Z");
        salesReportForm.setTo("2023-03-15T00:00:00.000Z");
        return salesReportForm;
    }
}
