package com.increff.pos.dao;

import com.increff.pos.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class InventoryDao extends AbstractDao {

    public void insertInventory(InventoryPojo p) {
        em().persist(p);
    }

    public InventoryPojo selectInventory(int id) {
        return select(InventoryPojo.class, id, "id");
    }

    public List<InventoryPojo> selectAllInventory() {
        return selectAll(InventoryPojo.class);
    }
}
