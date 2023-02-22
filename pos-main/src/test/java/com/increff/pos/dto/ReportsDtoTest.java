package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.*;
import com.increff.pos.model.form.*;
import com.increff.pos.config.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.DummyFormUtil.*;
import static com.increff.pos.util.DummyFormUtil.GetInventoryForm;
import static org.junit.Assert.assertEquals;

public class ReportsDtoTest extends AbstractUnitTest {
    @Autowired
    private ReportsDto reportsDto;
    @Autowired
    private OrdersDto orderItemsDto;

    @Autowired
    private ProductDto productDto;

    @Autowired
    private BrandDto brandDto;

    @Autowired
    private InventoryDto inventoryDto;

    @Autowired
    private OrdersDto ordersDto;

    @Test
    public void testDailyReport() throws ApiException {
        DailyReportForm dailyReportForm = new DailyReportForm();
        dailyReportForm.setFrom("2012-02-12");
        dailyReportForm.setTo("2014-02-12");
        try{
            reportsDto.getDailyReport(dailyReportForm);
        }
        catch (ApiException e) {
            assertEquals("No reports exists for the given dates", e.getMessage());
        }
    }

    @Test
    public void testInventoryReportPdf() throws Exception {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        brandForm = GetBrandForm("a", "a");
        brandDto.addBrand(brandForm);

        brandForm = GetBrandForm("b", "b");
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        ProductForm productForm1 = GetProductForm();
        productForm1.setBrand("a");
        productForm1.setCategory("a");
        productForm1.setBarcode("testBarcode2");
        productDto.addProduct(productForm1);

        ProductForm productForm2 = GetProductForm();
        productForm2.setBrand("b");
        productForm2.setCategory("b");
        productForm2.setBarcode("testBarcode3");
        productDto.addProduct(productForm2);

        try{
            List<InventoryReportData> inventoryDataList = reportsDto.getInventoryReport(GetBrandForm("", ""));
        }
        catch (ApiException e) {
            assertEquals("Inventory is empty", e.getMessage());
        }

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        InventoryForm inventoryForm1 = GetInventoryForm();
        inventoryForm1.setBarcode("testBarcode2");
        inventoryForm1.setQuantity(99);
        inventoryDto.addInventory(inventoryForm1);

        InventoryForm inventoryForm2 = GetInventoryForm();
        inventoryForm2.setBarcode("testBarcode3");
        inventoryForm2.setQuantity(991);
        inventoryDto.addInventory(inventoryForm2);

        try {
            reportsDto.getInventoryReportPdf(GetBrandForm("", ""));
        }
        catch (ApiException e) {
            assertEquals("Connection to Invoice App refused", e.getMessage());
        }
    }

    @Test
    public void testBrandReportPdf() throws Exception {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        brandForm = GetBrandForm("a", "a");
        brandDto.addBrand(brandForm);

        brandForm = GetBrandForm("a", "boina");
        brandDto.addBrand(brandForm);

        try {
            reportsDto.getBrandReportPdf(GetBrandForm("", ""));
        }
        catch (ApiException e) {
            assertEquals("Connection to Invoice App refused", e.getMessage());
        }
    }

    @Test
    public void testSalesReportPdf() throws Exception {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        List<OrderItemsForm> orderItemsFormList = GetOrderItemsFormList();
        ordersDto.addOrder(orderItemsFormList);

        OrderItemsForm orderItemsForm = GetOrderItemsForm();
        orderItemsForm.setOrderId(ordersDto.getAllOrders().get(0).getId());
        ordersDto.addOrderItem(orderItemsForm);

        SalesReportForm salesReportForm = GetSalesReportForm();
        salesReportForm.setBrand("");
        salesReportForm.setCategory("");
        try{
            reportsDto.getSalesReportPdf(salesReportForm);
        }
        catch (ApiException e) {
            assertEquals("Connection to Invoice App refused", e.getMessage());
        }
    }

    @Test
    public void testDailyReportPdf() throws Exception {
        DailyReportForm dailyReportForm = new DailyReportForm();
        dailyReportForm.setFrom("2012-02-12");
        dailyReportForm.setTo("2024-02-12");
        try{
            reportsDto.getDailyReportPdf(dailyReportForm);
        }
        catch (Exception e) {
            assertEquals("No reports exists for the given dates", e.getMessage());
        }
    }

    @Test
    public void testInventoryReport() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        brandForm = GetBrandForm("a", "a");
        brandDto.addBrand(brandForm);

        brandForm = GetBrandForm("b", "b");
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        ProductForm productForm1 = GetProductForm();
        productForm1.setBrand("a");
        productForm1.setCategory("a");
        productForm1.setBarcode("testBarcode2");
        productDto.addProduct(productForm1);

        ProductForm productForm2 = GetProductForm();
        productForm2.setBrand("b");
        productForm2.setCategory("b");
        productForm2.setBarcode("testBarcode3");
        productDto.addProduct(productForm2);

        try{
            List<InventoryReportData> inventoryDataList = reportsDto.getInventoryReport(GetBrandForm("", ""));
        }
        catch (ApiException e) {
            assertEquals("Inventory is empty", e.getMessage());
        }

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        InventoryForm inventoryForm1 = GetInventoryForm();
        inventoryForm1.setBarcode("testBarcode2");
        inventoryForm1.setQuantity(99);
        inventoryDto.addInventory(inventoryForm1);

        InventoryForm inventoryForm2 = GetInventoryForm();
        inventoryForm2.setBarcode("testBarcode3");
        inventoryForm2.setQuantity(991);
        inventoryDto.addInventory(inventoryForm2);

        List<InventoryReportData> inventoryDataList = reportsDto.getInventoryReport(GetBrandForm("", ""));
        assertEquals(3, inventoryDataList.size());

        inventoryDataList = reportsDto.getInventoryReport(GetBrandForm("a", ""));
        assertEquals(1, inventoryDataList.size());

        inventoryDataList = reportsDto.getInventoryReport(GetBrandForm("", "b"));
        assertEquals(1, inventoryDataList.size());

        inventoryDataList = reportsDto.getInventoryReport(GetBrandForm("pavan", "boina"));
        assertEquals(1, inventoryDataList.size());
    }

    @Test
    public void testBrandReport() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        brandForm = GetBrandForm("a", "a");
        brandDto.addBrand(brandForm);

        brandForm = GetBrandForm("a", "boina");
        brandDto.addBrand(brandForm);

        List<BrandReportData> brandReportDataList = reportsDto.getBrandReport(GetBrandForm("", ""));
        assertEquals(3, brandReportDataList.size());

        brandReportDataList = reportsDto.getBrandReport(GetBrandForm("a", ""));
        assertEquals(2, brandReportDataList.size());

        brandReportDataList = reportsDto.getBrandReport(GetBrandForm("", "boina"));
        assertEquals(2, brandReportDataList.size());

        brandReportDataList = reportsDto.getBrandReport(GetBrandForm("pavan", "boina"));
        assertEquals(1, brandReportDataList.size());
    }

    @Test
    public void testSalesReport() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        List<OrderItemsForm> orderItemsFormList = GetOrderItemsFormList();
        ordersDto.addOrder(orderItemsFormList);

        OrderItemsForm orderItemsForm = GetOrderItemsForm();
        orderItemsForm.setOrderId(ordersDto.getAllOrders().get(0).getId());
        ordersDto.addOrderItem(orderItemsForm);

        SalesReportForm salesReportForm = GetSalesReportForm();
        salesReportForm.setBrand("");
        salesReportForm.setCategory("");
        List<SalesReportData> salesReportDataList = new ArrayList<>();
        try{
            salesReportDataList = reportsDto.getSalesReport(salesReportForm);
        }
        catch (ApiException e) {
            assertEquals("No Sales for a given range", e.getMessage());
        }

        salesReportForm = GetSalesReportForm();
        salesReportForm.setBrand("pavan");
        salesReportForm.setCategory("");
        salesReportDataList = reportsDto.getSalesReport(salesReportForm);
        assertEquals(1, salesReportDataList.size());

        salesReportForm = GetSalesReportForm();
        salesReportForm.setBrand("");
        salesReportForm.setCategory("boina");
        salesReportDataList = reportsDto.getSalesReport(salesReportForm);
        assertEquals(1, salesReportDataList.size());

        salesReportForm = GetSalesReportForm();
        salesReportForm.setBrand("pavan");
        salesReportForm.setCategory("boina");
        salesReportDataList = reportsDto.getSalesReport(salesReportForm);
        assertEquals(1, salesReportDataList.size());
    }
}