package com.increff.pos.api;

import com.increff.pos.config.AbstractUnitTest;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.form.OrderItemsForm;
import com.increff.pos.pojo.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.DummyFormUtil.GetOrderItemsFormList;
import static com.increff.pos.util.DummyPojoUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OrdersApiTest extends AbstractUnitTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Autowired
    private OrdersApi ordersApi;

    @Test
    public void testAddOrder() throws ApiException {
        OrdersPojo ordersPojo = GetOrdersPojo();
        ordersApi.addOrder(ordersPojo);
    }

    @Test
    public void testDeleteOrder() throws ApiException {
        ordersApi.deleteOrder(1);
    }

    @Test
    public void testGetOrder() throws ApiException {
        OrdersPojo ordersPojo = GetOrdersPojo();
        ordersApi.addOrder(ordersPojo);

        int id = ordersApi.getAllOrders().get(0).getId();
        OrdersPojo ordersPojoAdded = ordersApi.getOrder(id);
        assertEquals(ordersPojo.getStatus(), ordersPojoAdded.getStatus());
        assertEquals(ordersPojo.getTime(), ordersPojoAdded.getTime());
    }


    @Test
    public void testGetCheckOrder() throws ApiException {
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Orders with given ID does not exist, id: 1");
        ordersApi.getCheckOrder(1);
    }

    @Test
    public void testSelectByFromDateOrder() throws ApiException {
        ZonedDateTime to = ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime from = to.minusMinutes(15);
        ordersApi.selectByFromDate(from, to);
    }
}