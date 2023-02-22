package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemsPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OrderItemsDao extends AbstractDao {
    public void insertOrderItem(OrderItemsPojo p) {
        em().persist(p);
    }

    public int deleteOrderItem(int id) {
        return delete(OrderItemsPojo.class, id, "id");
    }

    public OrderItemsPojo selectOrderItem(int id) {
        return select(OrderItemsPojo.class, id, "id");
    }

    public OrderItemsPojo selectOrderItemByOrderId(int orderId, int productId) {
        return selectSingle(OrderItemsPojo.class, orderId, "orderId", productId, "productId");
    }

    public List<OrderItemsPojo> selectByOrderId(int orderId) {
        return selectMultiple(OrderItemsPojo.class, orderId, "orderId");
    }

    public List<OrderItemsPojo> selectAllOrderItems() {
        return selectAll(OrderItemsPojo.class);
    }

    public List<OrderItemsPojo> selectFromOrderIdList(List<Integer> orderIdList) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<OrderItemsPojo> cr = cb.createQuery(OrderItemsPojo.class);
        Root<OrderItemsPojo> root = cr.from(OrderItemsPojo.class);
        cr.select(root).where(root.get("orderId").in(orderIdList));

        TypedQuery<OrderItemsPojo> query = em().createQuery(cr);
        List<OrderItemsPojo> results = query.getResultList();
        return results;
    }
}
