package com.increff.pos.api;

import com.increff.pos.client.InvoiceClient;
import com.increff.pos.dao.OrdersDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.xml.OrderInvoiceXmlList;
import com.increff.pos.pojo.OrdersPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrdersApi {
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private InvoiceClient invoiceClient;

    public void addOrder(OrdersPojo ordersPojo) throws ApiException {
        ordersDao.insertOrder(ordersPojo);
    }

    public void deleteOrder(int id) {
        ordersDao.deleteOrder(id);
    }

    public OrdersPojo getOrder(int id) throws ApiException {
        return getCheckOrder(id);
    }

    public List<OrdersPojo> getAllOrders() {
        return ordersDao.selectAllOrders();
    }

    public List<OrdersPojo> selectByStatus(String status) {
        return ordersDao.selectByOrderStatus(status);
    }

    public List<OrdersPojo> selectByFromDate(ZonedDateTime from, ZonedDateTime to) {
        return ordersDao.selectOrdersByFromDate(from, to);
    }

    public void updateOrder(int id, OrdersPojo ordersPojo) throws ApiException {
        OrdersPojo ordersPojoUpdated = getCheckOrder(id);
        ordersPojoUpdated.setStatus(ordersPojo.getStatus());
    }

    public OrdersPojo getCheckOrder(int id) throws ApiException {
        OrdersPojo ordersPojo = ordersDao.selectOrder(id);
        if (ordersPojo == null) {
            throw new ApiException("Orders with given ID does not exist, id: " + id);
        }
        return ordersPojo;
    }

    public String generateInvoicePdf(OrderInvoiceXmlList orderInvoiceXmlList) {
        return invoiceClient.generateInvoice(orderInvoiceXmlList);
    }
}
