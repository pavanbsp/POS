package com.increff.pos.dao;

import com.increff.pos.pojo.OrdersPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OrdersDao extends AbstractDao {
    @Transactional
    public void insertOrder(OrdersPojo p) {
        em().persist(p);
    }

    public int deleteOrder(int id) {
        return delete(OrdersPojo.class, id, "id");
    }

    public OrdersPojo selectOrder(int id) {
        return select(OrdersPojo.class, id, "id");
    }

    public List<OrdersPojo> selectAllOrders() {
        return selectAll(OrdersPojo.class);
    }

    public List<OrdersPojo> selectByOrderStatus(String status) {
        return selectMultiple(OrdersPojo.class, status, "status");
    }

    public List<OrdersPojo> selectOrdersByFromDate(ZonedDateTime from, ZonedDateTime to) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<OrdersPojo> cr = cb.createQuery(OrdersPojo.class);
        Root<OrdersPojo> root = cr.from(OrdersPojo.class);
        cr.select(root).where(cb.greaterThanOrEqualTo(root.get("time"), from), cb.lessThanOrEqualTo(root.get("time"), to));

        TypedQuery<OrdersPojo> query = em().createQuery(cr);
        List<OrdersPojo> results = query.getResultList();
        return results;
    }

}
