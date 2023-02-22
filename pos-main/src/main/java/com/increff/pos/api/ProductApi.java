package com.increff.pos.api;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class ProductApi {

    @Autowired
    private ProductDao productDao;

    public void addProduct(ProductPojo productPojo) throws ApiException {
        checkUniqueProduct(productPojo.getBarcode());
        productDao.insertProduct(productPojo);
    }

    public ProductPojo getProduct(int id) throws ApiException {
        return getCheckProductById(id);
    }

    public List<ProductPojo> getAllProducts() throws ApiException {
        return productDao.selectAllProducts();
    }

    public void updateProduct(int id, ProductPojo productPojo) throws ApiException {
        ProductPojo productPojoUpdated = getCheckProductById(id);
        if (productPojo.getBarcode().compareTo(productPojoUpdated.getBarcode()) != 0) {
            throw new ApiException("Cannot update barcode for this product");
        }
        if (productPojo.getBrand_category_id() != productPojoUpdated.getBrand_category_id()) {
            throw new ApiException("Cannot update brand and category for this product");
        }
        productPojoUpdated.setName(productPojo.getName());
        productPojoUpdated.setMrp(productPojo.getMrp());
    }

    public ProductPojo getCheckProductById(int id) throws ApiException {
        ProductPojo productPojo = productDao.selectProduct(id);
        if (productPojo == null) {
            throw new ApiException("Product with given ID does not exist, id: " + id);
        }
        return productPojo;
    }

    public ProductPojo getCheckByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.selectProductByBarcode(barcode);
        if (productPojo == null) {
            throw new ApiException("Product with given barcode does not exist, barcode: " + barcode);
        }
        return productPojo;
    }

    private void checkUniqueProduct(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.selectProductByBarcode(barcode);
        if (productPojo != null) {
            throw new ApiException("Barcode should be unique");
        }
    }

    public List<ProductPojo> getProductsByBrandCategoryId(int brand_category_id) {
        return productDao.selectByBrandCategoryId(brand_category_id);
    }
}