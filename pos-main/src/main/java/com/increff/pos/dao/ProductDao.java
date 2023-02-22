package com.increff.pos.dao;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductDao extends AbstractDao {

    public void insertProduct(ProductPojo p) {
        em().persist(p);
    }

    public ProductPojo selectProduct(int id) {
        return select(ProductPojo.class, id, "id");
    }

    public ProductPojo selectProductByBarcode(String barcode) {
        return selectSingle(ProductPojo.class, barcode, "barcode");
    }

    public List<ProductPojo> selectAllProducts() {
        return selectAll(ProductPojo.class);
    }

    public List<ProductPojo> selectByBrandCategoryId(int brand_category_id) {
        return selectMultiple(ProductPojo.class, brand_category_id, "brand_category_id");
    }
}
