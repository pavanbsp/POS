package com.increff.pos.api;

import com.increff.pos.config.AbstractUnitTest;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.ProductPojo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.DummyPojoUtil.GetProductPojo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ProductApiTest extends AbstractUnitTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Autowired
    private ProductApi productApi;

    @Test
    public void testAdd() throws ApiException {
        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        ProductPojo productPojoAdded = productApi.getAllProducts().get(0);

        assertEquals(productPojo.getBarcode(), productPojoAdded.getBarcode());
        assertEquals(productPojo.getBrand_category_id(), productPojoAdded.getBrand_category_id());
        assertEquals(productPojo.getName(), productPojoAdded.getName());
        assertEquals(productPojo.getMrp(), productPojoAdded.getMrp(), 0);
    }

    @Test
    public void testGet() throws ApiException {
        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        int id = productApi.getAllProducts().get(0).getId();
        ProductPojo productPojoAdded = productApi.getProduct(id);

        assertEquals(productPojo.getBarcode(), productPojoAdded.getBarcode());
        assertEquals(productPojo.getBrand_category_id(), productPojoAdded.getBrand_category_id());
        assertEquals(productPojo.getName(), productPojoAdded.getName());
        assertEquals(productPojo.getMrp(), productPojoAdded.getMrp(), 0);
    }

    @Test
    public void testGetAll() throws ApiException {
        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        List<ProductPojo> productPojoList = productApi.getAllProducts();

        assertEquals(1, productPojoList.size());
    }

    @Test
    public void testUpdateProduct() throws ApiException {
        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        int id = productApi.getAllProducts().get(0).getId();
        ProductPojo productPojoAdded = GetProductPojo();
        productPojoAdded.setName("testName");
        productPojoAdded.setMrp(123);
        productApi.updateProduct(id, productPojoAdded);

        ProductPojo updatedProductPojo = productApi.getProduct(id);
        assertEquals(productPojoAdded.getBrand_category_id(), updatedProductPojo.getBrand_category_id());
        assertEquals(productPojoAdded.getMrp(), updatedProductPojo.getMrp(), 0);
        assertEquals(productPojoAdded.getName(), updatedProductPojo.getName());
        assertEquals(productPojoAdded.getBarcode(), updatedProductPojo.getBarcode());
    }

    @Test
    public void testUpdateProductBarcode() throws ApiException {
        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        int id = productApi.getAllProducts().get(0).getId();
        ProductPojo productPojoAdded = GetProductPojo();
        productPojoAdded.setBarcode("newBC");
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Cannot update barcode for this product");
        productApi.updateProduct(id, productPojoAdded);
    }

    @Test
    public void testUpdateProductBCID() throws ApiException {
        ProductPojo productPojo = GetProductPojo();
        productApi.addProduct(productPojo);

        int id = productApi.getAllProducts().get(0).getId();
        ProductPojo productPojoAdded = GetProductPojo();
        productPojoAdded.setBrand_category_id(100);
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Cannot update brand and category for this product");
        productApi.updateProduct(id, productPojoAdded);
    }

    @Test
    public void testGetCheckProduct() throws ApiException {
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Product with given ID does not exist, id: 1");
        productApi.getCheckProductById(1);
    }

    @Test
    public void testGetCheckBarcode() throws ApiException {
        productApi.addProduct(GetProductPojo());
        productApi.getCheckByBarcode("testBarcode");

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Product with given barcode does not exist, barcode: a");
        productApi.getCheckByBarcode("a");
    }

    @Test
    public void testCheckUniqueProduct() throws ApiException {
        productApi.addProduct(GetProductPojo());

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Barcode should be unique");
        productApi.addProduct(GetProductPojo());
    }

    @Test
    public void testGetByBrandCategoryId() throws ApiException {
        productApi.getProductsByBrandCategoryId(1);
    }
}