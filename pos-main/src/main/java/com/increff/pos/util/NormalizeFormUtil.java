package com.increff.pos.util;

import com.increff.pos.model.form.*;

import java.text.DecimalFormat;

public class NormalizeFormUtil {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public static void normalizeBrandForm(BrandForm brandForm) {
        brandForm.setBrand(brandForm.getBrand().trim().toLowerCase());
        brandForm.setCategory(brandForm.getCategory().trim().toLowerCase());
    }

    public static void normalizeInventoryForm(InventoryForm inventoryForm) {
        inventoryForm.setBarcode(inventoryForm.getBarcode().trim().toLowerCase());
    }

    public static void normalizeProductForm(ProductForm productForm) {
        productForm.setName(productForm.getName().trim().toLowerCase());
        productForm.setBarcode(productForm.getBarcode().trim().toLowerCase());
        productForm.setBrand(productForm.getBrand().trim().toLowerCase());
        productForm.setCategory(productForm.getCategory().trim().toLowerCase());
        productForm.setMrp(Double.parseDouble(df.format(productForm.getMrp())));
    }

    public static void normalizeOrderItemsForm(OrderItemsForm orderItemsForm) {
        orderItemsForm.setBarcode(orderItemsForm.getBarcode().trim().toLowerCase());
        orderItemsForm.setSellingPrice(Double.parseDouble(df.format(orderItemsForm.getSellingPrice())));
    }

    public static void normalizeUserForm(UserForm userForm) {
        userForm.setEmail(userForm.getEmail().trim().toLowerCase());
        userForm.setPassword(userForm.getPassword().toLowerCase());
    }

    public static void normalizeSalesReportForm(SalesReportForm salesReportForm) {
        salesReportForm.setBrand(salesReportForm.getBrand().trim().toLowerCase());
        salesReportForm.setCategory(salesReportForm.getCategory().trim().toLowerCase());
    }
}
