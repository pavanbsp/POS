package com.increff.pos.dto;

import com.increff.pos.api.OrdersApi;
import com.increff.pos.exception.ApiException;
import com.increff.pos.flow.OrdersFlow;
import com.increff.pos.model.data.OrderItemsData;
import com.increff.pos.model.data.OrdersData;
import com.increff.pos.model.form.OrderItemsForm;
import com.increff.pos.model.xml.OrderInvoiceXmlList;
import com.increff.pos.pojo.OrderItemsPojo;
import com.increff.pos.pojo.OrdersPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.OrdersHelper.*;
import static com.increff.pos.util.NormalizeFormUtil.normalizeOrderItemsForm;
import static com.increff.pos.util.ValidateFormUtil.validateOrderItemsForm;

@Service
public class OrdersDto {
    @Autowired
    private OrdersFlow ordersFlow;

    @Autowired
    private OrdersApi ordersApi;

    public OrdersData addOrder(List<OrderItemsForm> orderItemsFormList) throws ApiException {
        OrdersPojo ordersPojo = convertOrdersFormtoOrdersPojo();
        for (OrderItemsForm orderItemsForm : orderItemsFormList) {
            validateOrderItemsForm(orderItemsForm);
            normalizeOrderItemsForm(orderItemsForm);
        }
        return ordersFlow.addOrder(ordersPojo, orderItemsFormList);
    }

    public OrdersData getOrder(int id) throws ApiException {
        return convertOrdersPojoToOrdersData(ordersApi.getOrder(id));
    }

    public List<OrdersData> getAllOrders() throws ApiException {
        List<OrdersPojo> ordersPojoList = ordersApi.getAllOrders();
        List<OrdersData> ordersDataList = new ArrayList<>();
        for (OrdersPojo ordersPojo : ordersPojoList) {
            ordersDataList.add(convertOrdersPojoToOrdersData(ordersPojo));
        }
        return ordersDataList;
    }

    public String generateInvoicePdf(int orderId) throws ApiException {
        OrderInvoiceXmlList orderInvoiceXmlList = generateInvoiceList(orderId);
        return ordersApi.generateInvoicePdf(orderInvoiceXmlList);
    }

    public OrderInvoiceXmlList generateInvoiceList(int orderId) throws ApiException {
        return ordersFlow.generateInvoiceList(orderId);
    }

    public void addOrderItem(OrderItemsForm orderItemsForm) throws ApiException {
        validateOrderItemsForm(orderItemsForm);
        normalizeOrderItemsForm(orderItemsForm);
        OrderItemsPojo orderItemsPojo = convertOrderItemsFormtoOrderItemsPojo(orderItemsForm);
        ordersFlow.addOrderItem(orderItemsPojo, orderItemsForm.getBarcode());
    }

    public void deleteOrderItem(int id) throws ApiException {
        ordersFlow.deleteOrderItem(id);
    }

    public OrderItemsData getOrderItem(int id) throws ApiException {
        return ordersFlow.getOrderItem(id);
    }

    public List<OrderItemsData> getOrderItemByOrderId(int orderId) throws ApiException {
        return ordersFlow.getOrderItemByOrderId(orderId);
    }

    public List<OrderItemsData> getAllOrderItems() throws ApiException {
        return ordersFlow.getAllOrderItems();
    }

    public void updateOrderItem(int id, OrderItemsForm orderItemsForm) throws ApiException {
        validateOrderItemsForm(orderItemsForm);
        normalizeOrderItemsForm(orderItemsForm);
        OrderItemsPojo orderItemsPojo = convertOrderItemsFormtoOrderItemsPojo(orderItemsForm);
        ordersFlow.updateOrderItem(id, orderItemsPojo, orderItemsForm.getBarcode());
    }
}
