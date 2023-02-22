package com.increff.pos.helper;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderInvoiceData;
import com.increff.pos.model.data.OrderItemsData;
import com.increff.pos.model.data.OrdersData;
import com.increff.pos.model.form.OrderItemsForm;
import com.increff.pos.model.xml.OrderInvoiceXmlList;
import com.increff.pos.pojo.OrderItemsPojo;
import com.increff.pos.pojo.OrdersPojo;
import com.increff.pos.pojo.ProductPojo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdersHelper {
    public static OrdersPojo convertOrdersFormtoOrdersPojo() {
        OrdersPojo ordersPojo = new OrdersPojo();
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        ordersPojo.setTime(date);
        ordersPojo.setStatus("Not Invoiced");
        return ordersPojo;
    }

    public static OrdersData convertOrdersPojoToOrdersData(OrdersPojo ordersPojo) throws ApiException {
        OrdersData ordersData = new OrdersData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ordersData.setTime(ordersPojo.getTime().format(formatter));
        ordersData.setId(ordersPojo.getId());
        ordersData.setStatus(ordersPojo.getStatus());
        return ordersData;
    }

    public static OrderInvoiceXmlList convertToInvoiceDataList(List<OrderItemsPojo> orderItemPojoList, Map<OrderItemsPojo, ProductPojo> productPojoList, ZonedDateTime dateTime) {
        List<OrderInvoiceData> orderInvoiceDataList = new ArrayList<>();
        for (OrderItemsPojo orderItemPojo : orderItemPojoList) {
            OrderInvoiceData orderInvoiceData = new OrderInvoiceData();
            orderInvoiceData.setMrp(orderItemPojo.getSellingPrice());
            orderInvoiceData.setName(productPojoList.get(orderItemPojo).getName());
            orderInvoiceData.setQuantity(orderItemPojo.getQuantity());
            orderInvoiceDataList.add(orderInvoiceData);
        }
        OrderInvoiceXmlList orderInvoiceXmlList = new OrderInvoiceXmlList();
        orderInvoiceXmlList.setOrderInvoiceData(orderInvoiceDataList);
        orderInvoiceXmlList.setOrder_id(orderItemPojoList.get(0).getOrderId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        orderInvoiceXmlList.setDatetime(dateTime.format(formatter));
        double total = calculateTotal(orderInvoiceXmlList);
        orderInvoiceXmlList.setTotal(total);
        return orderInvoiceXmlList;
    }

    private static double calculateTotal(OrderInvoiceXmlList orderInvoiceXmlList) {
        double total = 0;
        for (OrderInvoiceData orderInvoiceData : orderInvoiceXmlList.getOrderInvoiceData()) {
            total += (orderInvoiceData.getMrp() * orderInvoiceData.getQuantity());
        }
        return total;
    }

    public static OrderItemsPojo convertOrderItemsFormtoOrderItemsPojo(OrderItemsForm orderItemsForm) {
        OrderItemsPojo orderItemsPojo = new OrderItemsPojo();
        orderItemsPojo.setSellingPrice(orderItemsForm.getSellingPrice());
        orderItemsPojo.setQuantity(orderItemsForm.getQuantity());
        orderItemsPojo.setOrderId(orderItemsForm.getOrderId());
        return orderItemsPojo;
    }

    public static OrderItemsData convertOrderItemsPojoToOrderItemsData(OrderItemsPojo orderItemsPojo, ProductPojo productPojo) {
        OrderItemsData orderItemsData = new OrderItemsData();
        orderItemsData.setQuantity(orderItemsPojo.getQuantity());
        orderItemsData.setSellingPrice(orderItemsPojo.getSellingPrice());
        orderItemsData.setOrderId(orderItemsPojo.getOrderId());
        orderItemsData.setId(orderItemsPojo.getId());
        orderItemsData.setBarcode(productPojo.getBarcode());
        orderItemsData.setProductName(productPojo.getName());
        return orderItemsData;
    }
}
