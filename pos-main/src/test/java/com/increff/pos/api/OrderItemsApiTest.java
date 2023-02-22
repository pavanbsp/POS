package com.increff.pos.api;

import com.increff.pos.config.AbstractUnitTest;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.DummyPojoUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OrderItemsApiTest extends AbstractUnitTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Autowired
    private OrderItemsApi orderItemsApi;
    @Autowired
    private InventoryApi inventoryApi;
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private ProductApi productApi;
    @Autowired
    private OrdersApi ordersApi;


    @Test
    public void testAddOrderItem() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryPojo.setId(productApi.getAllProducts().get(0).getId()); //set inventory id as existing product id
        inventoryApi.addInventory(inventoryPojo);

        //add new order item
        OrderItemsPojo orderItemsPojo = GetOrderItemsPojo();
        orderItemsPojo.setOrderId(1);
        orderItemsPojo.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id
        orderItemsApi.addOrderItem(orderItemsPojo);

        //add new order item with same order id
        OrderItemsPojo orderItemsPojo1 = GetOrderItemsPojo();
        orderItemsPojo1.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id
        orderItemsPojo1.setQuantity(123);
        orderItemsPojo1.setOrderId(1);
        orderItemsApi.addOrderItem(orderItemsPojo);
    }

    @Test
    public void testAddOrderItemDifferentSellingPrice() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryPojo.setId(productApi.getAllProducts().get(0).getId()); //set inventory id as existing product id
        inventoryApi.addInventory(inventoryPojo);

        //add new order item
        OrderItemsPojo orderItemsPojo = GetOrderItemsPojo();
        orderItemsPojo.setOrderId(1);
        orderItemsPojo.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id
        orderItemsApi.addOrderItem(orderItemsPojo);

        //add new order item with same order id
        OrderItemsPojo orderItemsPojo1 = GetOrderItemsPojo();
        orderItemsPojo1.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id
        orderItemsPojo1.setQuantity(123);
        orderItemsPojo1.setOrderId(1);
        orderItemsApi.addOrderItem(orderItemsPojo);

        //add order item with a different selling price
        OrderItemsPojo orderItemsPojo3 = GetOrderItemsPojo();
        orderItemsPojo3.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id
        orderItemsPojo3.setSellingPrice(11);
        orderItemsPojo3.setOrderId(1);
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Order Item already exists with different selling price");
        orderItemsApi.addOrderItem(orderItemsPojo3);
    }


    @Test
    public void testDelete() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryPojo.setId(productApi.getAllProducts().get(0).getId()); //set inventory id as existing product id
        inventoryApi.addInventory(inventoryPojo);

        //add new order item
        OrderItemsPojo orderItemsPojo = GetOrderItemsPojo();
        orderItemsPojo.setOrderId(1);
        orderItemsPojo.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id
        orderItemsApi.addOrderItem(orderItemsPojo);

        orderItemsApi.deleteOrderItem(orderItemsApi.getAllOrderItems().get(0).getId());
    }

    @Test
    public void testGet() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryPojo.setId(productApi.getAllProducts().get(0).getId()); //set inventory id as existing product id
        inventoryApi.addInventory(inventoryPojo);

        //add new order item
        OrderItemsPojo orderItemsPojo = GetOrderItemsPojo();
        orderItemsPojo.setOrderId(1);
        orderItemsPojo.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id
        orderItemsApi.addOrderItem(orderItemsPojo);

        int id = orderItemsApi.getAllOrderItems().get(0).getId();
        OrderItemsPojo orderItemsPojoAdded = orderItemsApi.getOrderItem(id);
        assertEquals(orderItemsPojo.getOrderId(), orderItemsPojoAdded.getOrderId());
        assertEquals(orderItemsPojo.getQuantity(), orderItemsPojoAdded.getQuantity());
        assertEquals(orderItemsPojo.getSellingPrice(), orderItemsPojoAdded.getSellingPrice(), 0);
    }

    @Test
    public void testGetCheckOrderItem() throws ApiException {
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("OrderItems with given ID does not exist, id: 1");
        OrderItemsPojo orderItemsPojoAdded = orderItemsApi.getOrderItem(1);
    }

    @Test
    public void testGetFromOrderIdList() throws ApiException {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        orderItemsApi.getFromOrderIdList(list);
    }

    @Test
    public void testGetByOrderId() throws ApiException {
        orderItemsApi.getByOrderId(1);
    }

    @Test
    public void testUpdateOrderItem() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryPojo.setId(productApi.getAllProducts().get(0).getId()); //set inventory id as existing product id
        inventoryApi.addInventory(inventoryPojo);

        //add new order item
        OrderItemsPojo orderItemsPojo = GetOrderItemsPojo();
        orderItemsPojo.setOrderId(1);
        orderItemsPojo.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id
        orderItemsApi.addOrderItem(orderItemsPojo);

        int id = orderItemsApi.getAllOrderItems().get(0).getId();
        OrderItemsPojo orderItemsPojoAdded = orderItemsApi.getOrderItem(id);
        orderItemsPojoAdded.setQuantity(2);
        orderItemsPojoAdded.setSellingPrice(2);
        orderItemsApi.updateOrderItem(id, orderItemsPojoAdded);

        OrderItemsPojo orderItemsPojoUpdated = orderItemsApi.getOrderItem(id);
        assertEquals(orderItemsPojoAdded.getQuantity(), orderItemsPojoUpdated.getQuantity());
        assertEquals(orderItemsPojoAdded.getSellingPrice(), orderItemsPojoUpdated.getSellingPrice(), 0);
    }

    @Test
    public void testUpdateOrderId() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryPojo.setId(productApi.getAllProducts().get(0).getId()); //set inventory id as existing product id
        inventoryApi.addInventory(inventoryPojo);

        //add new order item
        OrderItemsPojo orderItemsPojo = GetOrderItemsPojo();
        orderItemsPojo.setOrderId(1);
        orderItemsPojo.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id
        orderItemsApi.addOrderItem(orderItemsPojo);

        int id = orderItemsApi.getAllOrderItems().get(0).getId();
        OrderItemsPojo orderItemsPojoAdded = new OrderItemsPojo();
        orderItemsPojoAdded.setOrderId(2);
        orderItemsPojoAdded.setQuantity(2);
        orderItemsPojoAdded.setSellingPrice(2);
        orderItemsPojo.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Cannot update OrderId of the orderItem");
        orderItemsApi.updateOrderItem(id, orderItemsPojoAdded);
    }

    @Test
    public void testUpdateBarcode() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        InventoryPojo inventoryPojo = GetInventoryPojo();
        inventoryPojo.setId(productApi.getAllProducts().get(0).getId()); //set inventory id as existing product id
        inventoryApi.addInventory(inventoryPojo);

        //add new order item
        OrderItemsPojo orderItemsPojo = GetOrderItemsPojo();
        orderItemsPojo.setOrderId(1);
        orderItemsPojo.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id
        orderItemsApi.addOrderItem(orderItemsPojo);

        int id = orderItemsApi.getAllOrderItems().get(0).getId();
        OrderItemsPojo orderItemsPojoAdded = new OrderItemsPojo();
        orderItemsPojoAdded.setOrderId(1);
        orderItemsPojoAdded.setQuantity(2);
        orderItemsPojoAdded.setProductId(2);
        orderItemsPojo.setProductId(productApi.getAllProducts().get(0).getId()); //set product id as existing id

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Cannot update barcode of the orderItem");
        orderItemsApi.updateOrderItem(id, orderItemsPojoAdded);
    }
}