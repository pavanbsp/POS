package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.config.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.DummyFormUtil.GetBrandForm;
import static org.junit.Assert.assertEquals;

public class BrandDtoTest extends AbstractUnitTest {
    @Autowired
    private BrandDto brandDto;
    @Test
    public void testAddBrand() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        BrandData brandData = brandDto.getAllBrands().get(0);
        assertEquals(brandForm.getBrand(), brandData.getBrand());
        assertEquals(brandForm.getCategory(), brandData.getCategory());
    }

    @Test
    public void testGetBrand() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        BrandData brandData = brandDto.getAllBrands().get(0);
        int id = brandDto.getBrand(brandData.getId()).getId();
        assertEquals(id, brandData.getId());
        assertEquals(brandData.getBrand(), brandForm.getBrand());
        assertEquals(brandData.getCategory(), brandForm.getCategory());
    }

    @Test
    public void testGetAllBrands() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        BrandForm brandForm1 = GetBrandForm("dumBrand", "dumCategory");
        brandDto.addBrand(brandForm1);

        List<BrandData> brandDataList = brandDto.getAllBrands();
        assertEquals(2, brandDataList.size());
    }

    @Test
    public void testUpdateBrand() throws ApiException {
        BrandForm brandForm = GetBrandForm();
        brandDto.addBrand(brandForm);

        int id = brandDto.getAllBrands().get(0).getId();

        BrandForm brandForm1 = GetBrandForm("testBrand", "brandCategory");
        brandDto.updateBrand(id, brandForm1);

        BrandData brandDataUpdated = brandDto.getAllBrands().get(0);
        assertEquals(id, brandDataUpdated.getId());
        assertEquals(brandForm1.getBrand(), brandDataUpdated.getBrand());
        assertEquals(brandForm1.getCategory(), brandDataUpdated.getCategory());
    }
}