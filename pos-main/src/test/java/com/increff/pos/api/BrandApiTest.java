package com.increff.pos.api;

import com.increff.pos.config.AbstractUnitTest;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.BrandPojo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.DummyPojoUtil.GetBrandPojo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BrandApiTest extends AbstractUnitTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Autowired
    private BrandApi brandApi;

    @Test
    public void testAddBrand() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        BrandPojo brandPojoAdded = brandApi.getAllBrands().get(0);
        assertEquals(brandPojo.getBrand(), brandPojoAdded.getBrand());
        assertEquals(brandPojo.getCategory(), brandPojoAdded.getCategory());
    }

    @Test
    public void testGetBrand() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        int id = brandApi.getAllBrands().get(0).getId();
        BrandPojo brandPojoAdded = brandApi.getBrand(id);

        assertEquals(brandPojo.getBrand(), brandPojoAdded.getBrand());
        assertEquals(brandPojo.getCategory(), brandPojoAdded.getCategory());
    }

    @Test
    public void testGetAllBrand() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        List<BrandPojo> brandPojoList = brandApi.getAllBrands();
        assertFalse(brandPojoList.isEmpty());
        assertEquals(brandPojoList.size(), 1);
    }

    @Test
    public void testUpdateBrand() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        int id = brandApi.getAllBrands().get(0).getId();
        BrandPojo brandPojoAdded = new BrandPojo();
        brandPojoAdded.setBrand("testBrand");
        brandPojoAdded.setCategory("testCat");
        brandApi.updateBrand(id, brandPojoAdded);

        BrandPojo updatedBrandPojo = brandApi.getBrand(id);
        assertEquals(updatedBrandPojo.getBrand(), brandPojoAdded.getBrand());
        assertEquals(updatedBrandPojo.getCategory(), brandPojoAdded.getCategory());
    }

    @Test
    public void testGetCheckBrand() throws ApiException {
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Brand with given ID does not exist, id: 1");
        brandApi.getCheckBrand(1);
    }

    @Test
    public void testCheckUnique() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Brand category combination must be unique");
        brandApi.addBrand(GetBrandPojo());
    }

    @Test
    public void testSelectByBrandCategory() throws ApiException {
        brandApi.addBrand(GetBrandPojo());
        brandApi.checkSelectByBrandCategory("pavan", "boina");

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Brand category combination doesn't exists");
        brandApi.checkSelectByBrandCategory("a", "a");
    }

    @Test
    public void testSelectByBrand() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        List<BrandPojo> brandPojoList = brandApi.selectByBrand(brandPojo.getBrand());
        assertEquals(brandPojoList.size(), 1);
    }

    @Test
    public void testSelectByCategory() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        List<BrandPojo> brandPojoList = brandApi.selectByCategory(brandPojo.getCategory());
        assertEquals(brandPojoList.size(), 1);
    }

    @Test
    public void testSelectById() throws ApiException {
        BrandPojo brandPojo = GetBrandPojo();
        brandApi.addBrand(brandPojo);

        int id = brandApi.getAllBrands().get(0).getId();
        BrandPojo brandPojoAdded = brandApi.selectById(id);

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("No brand exists with the given id");
        brandApi.selectById(2);
    }
}