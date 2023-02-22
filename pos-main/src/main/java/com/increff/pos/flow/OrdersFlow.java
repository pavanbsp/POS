package com.increff.pos.flow;

import com.increff.pos.api.InventoryApi;
import com.increff.pos.api.OrderItemsApi;
import com.increff.pos.api.OrdersApi;
import com.increff.pos.api.ProductApi;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderItemsData;
import com.increff.pos.model.data.OrdersData;
import com.increff.pos.model.form.OrderItemsForm;
import com.increff.pos.model.xml.OrderInvoiceXmlList;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemsPojo;
import com.increff.pos.pojo.OrdersPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.increff.pos.helper.OrdersHelper.*;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrdersFlow {
    @Autowired
    private OrdersApi ordersApi;
    @Autowired
    private OrderItemsApi orderItemsApi;
    @Autowired
    private ProductApi productApi;
    @Autowired
    private InventoryApi inventoryApi;

    public OrdersData addOrder(OrdersPojo ordersPojo, List<OrderItemsForm> orderItemsFormList) throws ApiException {
        if (orderItemsFormList.size() == 0) {
            throw new ApiException("Cannot place empty order");
        }
        ordersApi.addOrder(ordersPojo);
        try {
            for (OrderItemsForm orderItemsForm : orderItemsFormList) {
                OrderItemsPojo orderItemsPojo = convertOrderItemsFormtoOrderItemsPojo(orderItemsForm);
                orderItemsPojo.setOrderId(ordersPojo.getId());
                addOrderItem(orderItemsPojo, orderItemsForm.getBarcode());
            }
        } catch (ApiException e) {
            throw new ApiException("Cannot place order, " + e.getMessage());
        }
        return convertOrdersPojoToOrdersData(ordersPojo);
    }


    public OrderInvoiceXmlList generateInvoiceList(int orderId) throws ApiException {
        List<OrderItemsPojo> orderItemPojoList = orderItemsApi.getByOrderId(orderId);
        //Mapping product pojo to orderItem pojo which makes getting name of product easy for helper function
        Map<OrderItemsPojo, ProductPojo> productPojoList = new HashMap<>();
        for (OrderItemsPojo orderItemPojo : orderItemPojoList) {
            ProductPojo productPojo = productApi.getProduct(orderItemPojo.getProductId());
            productPojoList.put(orderItemPojo, productPojo);
        }
        OrderInvoiceXmlList orderInvoiceXmlList = convertToInvoiceDataList(orderItemPojoList, productPojoList, ordersApi.getOrder(orderItemPojoList.get(0).getOrderId()).getTime());
        OrdersPojo orderPojo = ordersApi.getOrder(orderId);
        if (Objects.equals(orderPojo.getStatus(), "Not Invoiced")) {
            orderPojo.setStatus("Invoiced");
            ordersApi.updateOrder(orderId, orderPojo);
        }
        return orderInvoiceXmlList;
    }

    public void addOrderItem(OrderItemsPojo orderItemsPojo, String barcode) throws ApiException {
        if (!Objects.equals(ordersApi.getOrder(orderItemsPojo.getOrderId()).getStatus(), "Not Invoiced")) {
            throw new ApiException("Cannot add order item because the order with given order id is already invoiced");
        }
        ProductPojo productPojo = productApi.getCheckByBarcode(barcode);
        if (orderItemsPojo.getSellingPrice() > productPojo.getMrp()) {
            throw new ApiException("Selling price cannot be greater than Mrp");
        }
        orderItemsPojo.setProductId(productPojo.getId());
        checkUpdateInventory(orderItemsPojo.getProductId(), orderItemsPojo.getQuantity(), 0);
        orderItemsApi.addOrderItem(orderItemsPojo);
    }

    public void deleteOrderItem(int id) throws ApiException {
        OrderItemsPojo orderItemsPojo = orderItemsApi.getOrderItem(id);
        checkUpdateInventory(orderItemsPojo.getProductId(), 0, orderItemsPojo.getQuantity());
        orderItemsApi.deleteOrderItem(id);
        checkDeleteOrder(orderItemsPojo.getOrderId());
    }

    public void checkDeleteOrder(int orderId) throws ApiException {
        List<OrderItemsPojo> orderItemsPojoList = orderItemsApi.getByOrderId(orderId);
        if (orderItemsPojoList.size() == 0) {
            ordersApi.deleteOrder(orderId);
        }
    }

    public OrderItemsData getOrderItem(int id) throws ApiException {
        OrderItemsPojo orderItemsPojo = orderItemsApi.getOrderItem(id);
        ProductPojo productPojo = productApi.getCheckProductById(orderItemsPojo.getProductId());
        return convertOrderItemsPojoToOrderItemsData(orderItemsPojo, productPojo);
    }

    public List<OrderItemsData> getOrderItemByOrderId(int orderId) throws ApiException {
        List<OrderItemsPojo> orderItemsPojoList = orderItemsApi.getByOrderId(orderId);
        List<OrderItemsData> orderItemsDataList = new ArrayList<>();
        for (OrderItemsPojo orderItemsPojo : orderItemsPojoList) {
            ProductPojo productPojo = productApi.getCheckProductById(orderItemsPojo.getProductId());
            orderItemsDataList.add(convertOrderItemsPojoToOrderItemsData(orderItemsPojo, productPojo));
        }
        return orderItemsDataList;
    }

    public List<OrderItemsData> getAllOrderItems() throws ApiException {
        List<OrderItemsPojo> orderItemsPojoList = orderItemsApi.getAllOrderItems();
        List<OrderItemsData> orderItemsDataList = new ArrayList<>();
        for (OrderItemsPojo orderItemsPojo : orderItemsPojoList) {
            ProductPojo productPojo = productApi.getCheckProductById(orderItemsPojo.getProductId());
            orderItemsDataList.add(convertOrderItemsPojoToOrderItemsData(orderItemsPojo, productPojo));
        }
        return orderItemsDataList;
    }

    public void updateOrderItem(int id, OrderItemsPojo orderItemsPojo, String barcode) throws ApiException {
        ProductPojo productPojo = productApi.getCheckByBarcode(barcode);
        if (orderItemsPojo.getSellingPrice() > productPojo.getMrp()) {
            throw new ApiException("Selling price cannot be greater than Mrp");
        }
        OrderItemsPojo orderItemsPojoExists = orderItemsApi.getOrderItem(id);
        orderItemsPojo.setProductId(productPojo.getId());
        checkUpdateInventory(orderItemsPojo.getProductId(), orderItemsPojo.getQuantity(), orderItemsPojoExists.getQuantity());
        orderItemsApi.updateOrderItem(id, orderItemsPojo);
    }


    private void checkUpdateInventory(int productId, int quantity, int current_items) throws ApiException {
        InventoryPojo inventoryPojo = inventoryApi.getInventory(productId);
        if (quantity > inventoryPojo.getQuantity() + current_items) {
            ProductPojo productPojo = productApi.getProduct(productId);
            throw new ApiException("Cannot add " + productPojo.getName() + " because items left in inventory is : " + inventoryPojo.getQuantity());
        }
        int remaining_items = inventoryPojo.getQuantity() - quantity;
        inventoryPojo.setQuantity(remaining_items + current_items);
        inventoryApi.updateInventory(productId, inventoryPojo);
    }
}
