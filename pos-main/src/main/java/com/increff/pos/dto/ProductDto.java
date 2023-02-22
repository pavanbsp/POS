package com.increff.pos.dto;

import com.increff.pos.api.ProductApi;
import com.increff.pos.exception.ApiException;
import com.increff.pos.flow.ProductFlow;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.increff.pos.helper.ProductHelper.convertProductFormtoProductPojo;
import static com.increff.pos.util.NormalizeFormUtil.normalizeProductForm;
import static com.increff.pos.util.ValidateFormUtil.validateProductForm;

@Service
public class ProductDto {
    @Autowired
    private ProductFlow productFlow;

    @Autowired
    private ProductApi productApi;

    public void addProduct(ProductForm productForm) throws ApiException {
        validateProductForm(productForm);
        normalizeProductForm(productForm);
        ProductPojo productPojo = convertProductFormtoProductPojo(productForm);
        productFlow.addProduct(productPojo, productForm.getBrand(), productForm.getCategory());
    }

    public ProductData getProduct(int id) throws ApiException {
        return productFlow.getProduct(id);
    }

    public ProductData getProductByBarcode(String barcode) throws ApiException {
        return productFlow.getProductByBarcode(barcode);
    }

    public List<ProductData> getAllProducts() throws ApiException {
        return productFlow.getAllProducts();
    }

    public void updateProduct(int id, ProductForm productForm) throws ApiException {
        validateProductForm(productForm);
        normalizeProductForm(productForm);
        ProductPojo productPojo = convertProductFormtoProductPojo(productForm);
        productFlow.updateProduct(id, productPojo, productForm.getBrand(), productForm.getCategory());
    }
}
