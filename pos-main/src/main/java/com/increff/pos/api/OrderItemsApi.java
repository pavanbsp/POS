package com.increff.pos.api;

import com.increff.pos.dao.OrderItemsDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.OrderItemsPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderItemsApi {

    @Autowired
    private OrderItemsDao orderItemsDao;

    public void addOrderItem(OrderItemsPojo orderItemsPojo) throws ApiException {
        OrderItemsPojo orderItemsPojoExists = orderItemsDao.selectOrderItemByOrderId(orderItemsPojo.getOrderId(), orderItemsPojo.getProductId());
        if (orderItemsPojoExists != null) {
            if (orderItemsPojoExists.getSellingPrice() != orderItemsPojo.getSellingPrice()) {
                throw new ApiException("Order Item already exists with different selling price");
            }
            orderItemsPojoExists.setQuantity(orderItemsPojo.getQuantity() + orderItemsPojoExists.getQuantity());
        } else {
            orderItemsDao.insertOrderItem(orderItemsPojo);
        }
    }

    public void deleteOrderItem(int id) throws ApiException {
        orderItemsDao.deleteOrderItem(id);
    }

    public OrderItemsPojo getOrderItem(int id) throws ApiException {
        return getCheckOrderItem(id);
    }

    public List<OrderItemsPojo> getAllOrderItems() throws ApiException {
        return orderItemsDao.selectAllOrderItems();
    }

    public List<OrderItemsPojo> getFromOrderIdList(List<Integer> orderIdList) throws ApiException {
        return orderItemsDao.selectFromOrderIdList(orderIdList);
    }

    @Transactional
    public List<OrderItemsPojo> getByOrderId(int orderId) throws ApiException {
        return orderItemsDao.selectByOrderId(orderId);
    }

    public void updateOrderItem(int id, OrderItemsPojo orderItemsPojo) throws ApiException {
        OrderItemsPojo orderItemsPojoUpdated = getCheckOrderItem(id);
        if (orderItemsPojo.getOrderId() != orderItemsPojoUpdated.getOrderId()) {
            throw new ApiException("Cannot update OrderId of the orderItem");
        }
        if (orderItemsPojo.getProductId() != orderItemsPojoUpdated.getProductId()) {
            throw new ApiException("Cannot update barcode of the orderItem");
        }
        orderItemsPojoUpdated.setQuantity(orderItemsPojo.getQuantity());
        orderItemsPojoUpdated.setSellingPrice(orderItemsPojo.getSellingPrice());
    }

    public OrderItemsPojo getCheckOrderItem(int id) throws ApiException {
        OrderItemsPojo orderItemsPojo = orderItemsDao.selectOrderItem(id);
        if (orderItemsPojo == null) {
            throw new ApiException("OrderItems with given ID does not exist, id: " + id);
        }
        return orderItemsPojo;
    }


}
