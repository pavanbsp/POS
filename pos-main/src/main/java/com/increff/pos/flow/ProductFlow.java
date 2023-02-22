package com.increff.pos.flow;

import com.increff.pos.api.BrandApi;
import com.increff.pos.api.ProductApi;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.ProductHelper.convertProductPojoToProductData;

@Service
@Transactional(rollbackOn = ApiException.class)
public class ProductFlow {
    @Autowired
    private ProductApi productApi;
    @Autowired
    private BrandApi brandApi;

    public void addProduct(ProductPojo productPojo, String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandApi.checkSelectByBrandCategory(brand, category);
        productPojo.setBrand_category_id(brandPojo.getId());
        productApi.addProduct(productPojo);
    }

    public ProductData getProduct(int id) throws ApiException {
        ProductPojo productPojo = productApi.getProduct(id);
        BrandPojo brandPojo = brandApi.selectById(productPojo.getBrand_category_id());
        return convertProductPojoToProductData(productPojo, brandPojo);
    }

    public ProductData getProductByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productApi.getCheckByBarcode(barcode);
        BrandPojo brandPojo = brandApi.selectById(productPojo.getBrand_category_id());
        return convertProductPojoToProductData(productPojo, brandPojo);
    }

    public List<ProductData> getAllProducts() throws ApiException {
        List<ProductPojo> productPojoList = productApi.getAllProducts();
        List<ProductData> productDataList = new ArrayList<>();
        for (ProductPojo productPojo : productPojoList) {
            BrandPojo brandPojo = brandApi.selectById(productPojo.getBrand_category_id());
            productDataList.add(convertProductPojoToProductData(productPojo, brandPojo));
        }
        return productDataList;
    }

    public void updateProduct(int id, ProductPojo productPojo, String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandApi.checkSelectByBrandCategory(brand, category);
        productPojo.setBrand_category_id(brandPojo.getId());
        productApi.updateProduct(id, productPojo);
    }
}
