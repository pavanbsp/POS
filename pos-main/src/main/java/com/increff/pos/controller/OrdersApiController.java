package com.increff.pos.controller;

import com.increff.pos.dto.OrdersDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderItemsData;
import com.increff.pos.model.data.OrdersData;
import com.increff.pos.model.form.OrderItemsForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api
@RestController
public class OrdersApiController {
    @Autowired
    private OrdersDto ordersDto;

    @ApiOperation(value = "Adds an order")
    @RequestMapping(path = "/api/orders", method = RequestMethod.POST)
    public OrdersData addOrder(@RequestBody List<OrderItemsForm> orderItemsFormList) throws ApiException {
        return ordersDto.addOrder(orderItemsFormList);
    }

    @ApiOperation(value = "Gets an order by ID")
    @RequestMapping(path = "/api/orders/{id}", method = RequestMethod.GET)
    public OrdersData getOrder(@PathVariable int id) throws ApiException {
        return ordersDto.getOrder(id);
    }


    @ApiOperation(value = "Gets list of all orders")
    @RequestMapping(path = "/api/orders", method = RequestMethod.GET)
    public List<OrdersData> getAllOrders() throws ApiException {
        return ordersDto.getAllOrders();
    }

    @ApiOperation(value = "Gets Invoice PDF by id")
    @RequestMapping(path = "/api/orders/invoice/{orderId}", method = RequestMethod.GET)
    public String getInvoice(@PathVariable int orderId, HttpServletResponse response) throws Exception {
        return ordersDto.generateInvoicePdf(orderId);
    }

    //order items
    @ApiOperation(value = "Adds an order-item")
    @RequestMapping(path = "/api/orderitems", method = RequestMethod.POST)
    public void addOrderItem(@RequestBody OrderItemsForm orderItemsForm) throws ApiException {
        ordersDto.addOrderItem(orderItemsForm);
    }

    @ApiOperation(value = "Deletes an orderitems")
    @RequestMapping(path = "/api/orderitems/{id}", method = RequestMethod.DELETE)
    public void deleteOrderItem(@PathVariable int id) throws ApiException {
        ordersDto.deleteOrderItem(id);
    }

    @ApiOperation(value = "Gets an orderitems by ID")
    @RequestMapping(path = "/api/orderitems/{id}", method = RequestMethod.GET)
    public OrderItemsData getOrderItem(@PathVariable int id) throws ApiException {
        return ordersDto.getOrderItem(id);
    }

    @ApiOperation(value = "Gets all orderitems by order ID")
    @RequestMapping(path = "/api/orderitems/order/{orderId}", method = RequestMethod.GET)
    public List<OrderItemsData> getOrderItemByOrderId(@PathVariable int orderId) throws ApiException {
        return ordersDto.getOrderItemByOrderId(orderId);
    }


    @ApiOperation(value = "Gets list of all orderitems")
    @RequestMapping(path = "/api/orderitems", method = RequestMethod.GET)
    public List<OrderItemsData> getAllOrderItems() throws ApiException {
        return ordersDto.getAllOrderItems();
    }

    @ApiOperation(value = "Updates a orderitems")
    @RequestMapping(path = "/api/orderitems/{id}", method = RequestMethod.PUT)
    public void updateOrderItem(@PathVariable int id, @RequestBody OrderItemsForm f) throws ApiException {
        ordersDto.updateOrderItem(id, f);
    }

}
