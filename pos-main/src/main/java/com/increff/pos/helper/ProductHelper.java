package com.increff.pos.helper;

import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;

public class ProductHelper {
    public static ProductPojo convertProductFormtoProductPojo(ProductForm productForm) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setMrp(productForm.getMrp());
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setName(productForm.getName());
        return productPojo;
    }

    public static ProductData convertProductPojoToProductData(ProductPojo productPojo, BrandPojo brandPojo) {
        ProductData productData = new ProductData();
        productData.setMrp(productPojo.getMrp());
        productData.setBarcode(productPojo.getBarcode());
        productData.setName(productPojo.getName());
        productData.setId(productPojo.getId());
        productData.setBrand(brandPojo.getBrand());
        productData.setCategory(brandPojo.getCategory());
        return productData;
    }
}
