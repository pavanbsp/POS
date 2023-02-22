package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.config.AbstractUnitTest;
import com.increff.pos.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.DummyFormUtil.*;
import static org.junit.Assert.assertEquals;

public class ProductDtoTest extends AbstractUnitTest {
    @Autowired
    private ProductDto productDto;

    @Autowired
    private BrandDto brandDto;

    @Test
    public void testAddProduct() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        ProductData productData = productDto.getAllProducts().get(0);

        assertEquals(productForm.getBarcode(), productData.getBarcode());
        assertEquals(productForm.getBrand(), productData.getBrand());
        assertEquals(productForm.getCategory(), productData.getCategory());
        assertEquals(productForm.getName(), productData.getName());
        assertEquals(productForm.getMrp(), productData.getMrp(), 0);
    }

    @Test
    public void testGetProduct() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        int id = productDto.getAllProducts().get(0).getId();
        ProductData productData = productDto.getProduct(id);

        assertEquals(productForm.getBarcode(), productData.getBarcode());
        assertEquals(productForm.getBrand(), productData.getBrand());
        assertEquals(productForm.getCategory(), productData.getCategory());
        assertEquals(productForm.getName(), productData.getName());
        assertEquals(productForm.getMrp(), productData.getMrp(), 0);
    }

    @Test
    public void testGetProductByBarcode() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        String barcode = productDto.getAllProducts().get(0).getBarcode();
        ProductData productData = productDto.getProductByBarcode(barcode);

        assertEquals(productForm.getMrp(), productData.getMrp(), 0);
    }

    @Test
    public void testGetAllProducts() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        ProductForm productForm1 = GetProductForm();
        productForm1.setBarcode("testBarcode2");
        productDto.addProduct(productForm1);

        List<ProductData> productDataList = productDto.getAllProducts();
        assertEquals(2, productDataList.size());
    }

    @Test
    public void testUpdateProduct() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        ProductForm productForm = GetProductForm();
        productDto.addProduct(productForm);

        int id = productDto.getAllProducts().get(0).getId();

        ProductForm productForm1 = GetProductForm();
        productForm1.setMrp(11);
        productForm1.setName("diffName");
        productDto.updateProduct(id, productForm1);

        ProductData productDataUpdated = productDto.getAllProducts().get(0);
        assertEquals(id, productDataUpdated.getId());
        assertEquals(productForm1.getBrand(), productDataUpdated.getBrand());
        assertEquals(productForm1.getMrp(), productDataUpdated.getMrp(), 0);
        assertEquals(productForm1.getBarcode(), productDataUpdated.getBarcode());
        assertEquals(productForm1.getName(), productDataUpdated.getName());
    }
}