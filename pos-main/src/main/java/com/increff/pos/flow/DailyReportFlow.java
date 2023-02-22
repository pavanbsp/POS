package com.increff.pos.flow;

import com.increff.pos.api.DailyReportApi;
import com.increff.pos.api.OrderItemsApi;
import com.increff.pos.api.OrdersApi;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.DailyReportPojo;
import com.increff.pos.pojo.OrderItemsPojo;
import com.increff.pos.pojo.OrdersPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class DailyReportFlow {
    @Autowired
    private OrdersApi ordersApi;
    @Autowired
    private OrderItemsApi orderItemsApi;
    @Autowired
    private DailyReportApi dailyReportApi;

    public void checkUpdateDailyReport(DailyReportPojo dailyReportPojo) throws ApiException {
        List<OrdersPojo> orderPojoList = ordersApi.selectByStatus("Invoiced");
        List<Integer> orderIdList = new ArrayList<>();
        for (OrdersPojo ordersPojo : orderPojoList) {
            orderIdList.add(ordersPojo.getId());
        }
        List<OrderItemsPojo> orderItemsPojoList = new ArrayList<>();
        if (orderIdList.size() > 0) {
            orderItemsPojoList = orderItemsApi.getFromOrderIdList(orderIdList);
        }
        double revenue = 0;
        for (OrderItemsPojo orderItemsPojo : orderItemsPojoList) {
            revenue += orderItemsPojo.getSellingPrice() * orderItemsPojo.getQuantity();
        }
        dailyReportPojo.setInvoiced_orders_count(orderIdList.size());
        dailyReportPojo.setInvoiced_items_count(orderItemsPojoList.size());
        dailyReportPojo.setTotal_revenue(revenue);
        if (dailyReportApi.getDailyReport(dailyReportPojo.getDate()) == null) {
            dailyReportApi.addDailyReport(dailyReportPojo);
        } else {
            dailyReportApi.updateDailyReport(dailyReportPojo.getDate(), dailyReportPojo);
        }
        for (OrdersPojo ordersPojo : orderPojoList) {
            ordersPojo.setStatus("Invoiced and read by scheduler");
            ordersApi.updateOrder(ordersPojo.getId(), ordersPojo);
        }
    }
}
