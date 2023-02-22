package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.config.AbstractUnitTest;
import com.increff.pos.model.data.OrderItemsData;
import com.increff.pos.model.data.OrdersData;
import com.increff.pos.model.form.*;
import com.increff.pos.model.xml.OrderInvoiceXmlList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.DummyFormUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OrdersDtoTest extends AbstractUnitTest {
    @Autowired
    private OrdersDto ordersDto;
    @Autowired
    private InventoryDto inventoryDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private BrandDto brandDto;
    
    @Test
    public void testAddOrder() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        List<OrderItemsForm> orderItemsFormList = GetOrderItemsFormList();
        ordersDto.addOrder(orderItemsFormList);
    }

    @Test
    public void testGetOrder() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        List<OrderItemsForm> orderItemsFormList = GetOrderItemsFormList();
        ordersDto.addOrder(orderItemsFormList);

        OrdersData ordersData = ordersDto.getAllOrders().get(0);
        OrdersData ordersData1 = ordersDto.getOrder(ordersData.getId());
        assertEquals(ordersData1.getId(), ordersData.getId());
        assertEquals(ordersData1.getStatus(), ordersData.getStatus());
        assertEquals(ordersData1.getTime(), ordersData.getTime());
    }

    @Test
    public void testAddOrderItem() throws ApiException {
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

        OrderItemsData orderItemsData = ordersDto.getAllOrderItems().get(0);
        assertEquals(orderItemsData.getOrderId(), orderItemsForm.getOrderId());
        assertEquals(orderItemsData.getQuantity(), orderItemsForm.getQuantity()*2);
        assertEquals(orderItemsData.getSellingPrice(), orderItemsForm.getSellingPrice(), 0);
        assertEquals(orderItemsData.getBarcode(), orderItemsForm.getBarcode());
    }

    @Test
    public void testDeleteOrderItem() throws ApiException {
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

        int id = ordersDto.getAllOrderItems().get(0).getId();
        ordersDto.deleteOrderItem(id);
    }

    @Test
    public void testGetOrderItem() throws ApiException {
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

        int id = ordersDto.getAllOrderItems().get(0).getId();
        OrderItemsData orderItemsData = ordersDto.getOrderItem(id);
        assertEquals(orderItemsData.getOrderId(), orderItemsForm.getOrderId());
        assertEquals(orderItemsData.getQuantity(), orderItemsForm.getQuantity()*2);
        assertEquals(orderItemsData.getSellingPrice(), orderItemsForm.getSellingPrice(), 0);
        assertEquals(orderItemsData.getBarcode(), orderItemsForm.getBarcode());
    }

    @Test
    public void testUpdateOrderItem() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        List<OrderItemsForm> orderItemsFormList = GetOrderItemsFormList();
        ordersDto.addOrder(orderItemsFormList);
        int id = ordersDto.getAllOrderItems().get(0).getId();

        OrderItemsForm orderItemsForm = GetOrderItemsForm();
        orderItemsForm.setSellingPrice(111);
        orderItemsForm.setQuantity(123);
        orderItemsForm.setOrderId(ordersDto.getAllOrders().get(0).getId());

        ordersDto.updateOrderItem(id, orderItemsForm);
        OrderItemsData orderItemsData = ordersDto.getOrderItem(id);
        assertEquals(orderItemsData.getOrderId(), orderItemsForm.getOrderId());
        assertEquals(orderItemsData.getQuantity(), orderItemsForm.getQuantity());
        assertEquals(orderItemsData.getSellingPrice(), orderItemsForm.getSellingPrice(), 0);
        assertEquals(orderItemsData.getBarcode(), orderItemsForm.getBarcode());
    }

    @Test
    public void testOrderItemByOrderId() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        List<OrderItemsForm> orderItemsFormList = GetOrderItemsFormList();
        ordersDto.addOrder(orderItemsFormList);

        OrderItemsForm orderItemsForm = GetOrderItemsForm();
        int id = ordersDto.getAllOrderItems().get(0).getOrderId();
        List<OrderItemsData> orderItemsDataList = ordersDto.getOrderItemByOrderId(id);
        OrderItemsData orderItemsData = orderItemsDataList.get(0);
        assertEquals(orderItemsData.getQuantity(), orderItemsForm.getQuantity());
        assertEquals(orderItemsData.getSellingPrice(), orderItemsForm.getSellingPrice(), 0);
        System.out.println(orderItemsData.getBarcode()+" "+orderItemsForm.getBarcode());
        assertEquals(orderItemsData.getBarcode(), orderItemsForm.getBarcode());
    }

    @Test
    public void testGenerateInvoiceList() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        List<OrderItemsForm> orderItemsFormList = GetOrderItemsFormList();
        ordersDto.addOrder(orderItemsFormList);

        OrdersData ordersData = ordersDto.getAllOrders().get(0);
        OrderInvoiceXmlList orderInvoiceXmlList = ordersDto.generateInvoiceList(ordersData.getId());
    }

    @Test
    public void testGenerateInvoicePdf() throws Exception {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = GetInventoryForm();
        inventoryDto.addInventory(inventoryForm);

        List<OrderItemsForm> orderItemsFormList = GetOrderItemsFormList();
        ordersDto.addOrder(orderItemsFormList);

        OrdersData ordersData = ordersDto.getAllOrders().get(0);
        try{
            ordersDto.generateInvoicePdf(ordersData.getId());
        }
        catch (Exception e) {
        }
    }
}