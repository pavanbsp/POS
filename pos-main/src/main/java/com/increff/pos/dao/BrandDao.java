package com.increff.pos.dao;

import com.increff.pos.pojo.BrandPojo;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BrandDao extends AbstractDao {

    public void insertBrand(BrandPojo p) {
        em().persist(p);
    }

    public BrandPojo selectBrand(int id) {
        return select(BrandPojo.class, id, "id");
    }

    public BrandPojo selectBrand(String brand, String category) {
        return selectSingle(BrandPojo.class, brand, "brand", category, "category");
    }

    public List<BrandPojo> selectByBrand(String brand) {
        return selectMultiple(BrandPojo.class, brand, "brand");
    }

    public List<BrandPojo> selectByCategory(String category) {
        return selectMultiple(BrandPojo.class, category, "category");
    }

    public List<BrandPojo> selectAllBrands() {
        return selectAll(BrandPojo.class);
    }

}
