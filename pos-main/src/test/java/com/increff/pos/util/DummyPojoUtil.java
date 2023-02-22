package com.increff.pos.util;

import com.increff.pos.pojo.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DummyPojoUtil {

    public static BrandPojo GetBrandPojo() {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("pavan");
        brandPojo.setCategory("boina");
        return brandPojo;
    }

    public static BrandPojo GetBrandPojo(String brand, String category) {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        return brandPojo;
    }

    public static ProductPojo GetProductPojo() {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode("testBarcode");
        productPojo.setBrand_category_id(1);
        productPojo.setName("testName");
        productPojo.setMrp(123);
        return productPojo;
    }

    public static ProductPojo GetProductPojo(String barcode, int bc_id, String name, double mrp) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode(barcode);
        productPojo.setBrand_category_id(bc_id);
        productPojo.setName(name);
        productPojo.setMrp(mrp);
        return productPojo;
    }

    public static InventoryPojo GetInventoryPojo() {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(12345);
        return inventoryPojo;
    }

    public static InventoryPojo GetInventoryPojo(int id, int quantity) {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(id);
        inventoryPojo.setQuantity(quantity);
        return inventoryPojo;
    }

    public static OrdersPojo GetOrdersPojo() {
        OrdersPojo ordersPojo = new OrdersPojo();
        ordersPojo.setTime(ZonedDateTime.now());
        ordersPojo.setStatus("In");
        return ordersPojo;
    }

    public static OrderItemsPojo GetOrderItemsPojo() {
        OrderItemsPojo orderItemsPojo = new OrderItemsPojo();
        orderItemsPojo.setProductId(1);
        orderItemsPojo.setQuantity(1);
        orderItemsPojo.setSellingPrice(1);
        return orderItemsPojo;
    }

    public static UserPojo GetUserPojo() {
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail("pavanboina@gmail.com");
        userPojo.setPassword("secret");
        userPojo.setRole("admin");
        return userPojo;
    }

    public static DailyReportPojo GetDailyReportPojo() {
        DailyReportPojo dailyReportPojo = new DailyReportPojo();
        dailyReportPojo.setDate("testDate");
        dailyReportPojo.setTotal_revenue(1);
        dailyReportPojo.setInvoiced_orders_count(1);
        dailyReportPojo.setInvoiced_orders_count(1);
        return dailyReportPojo;
    }
}
