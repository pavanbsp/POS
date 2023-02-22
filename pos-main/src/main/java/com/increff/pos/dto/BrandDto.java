package com.increff.pos.dto;

import com.increff.pos.api.BrandApi;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.BrandHelper.convertBrandFormtoBrandPojo;
import static com.increff.pos.helper.BrandHelper.convertBrandPojoToBrandData;
import static com.increff.pos.util.NormalizeFormUtil.normalizeBrandForm;
import static com.increff.pos.util.ValidateFormUtil.validateBrandForm;

@Service
public class BrandDto {

    @Autowired
    private BrandApi brandApi;

    public void addBrand(BrandForm brandForm) throws ApiException {
        validateBrandForm(brandForm);
        normalizeBrandForm(brandForm);
        brandApi.addBrand(convertBrandFormtoBrandPojo(brandForm));
    }

    public BrandData getBrand(int id) throws ApiException {
        return convertBrandPojoToBrandData(brandApi.getBrand(id));
    }

    public List<BrandData> getAllBrands() throws ApiException {
        List<BrandPojo> brandPojoList = brandApi.getAllBrands();
        List<BrandData> brandDataList = new ArrayList<>();
        for (BrandPojo brandPojo : brandPojoList) {
            brandDataList.add(convertBrandPojoToBrandData(brandPojo));
        }
        return brandDataList;
    }

    public void updateBrand(int id, BrandForm brandForm) throws ApiException {
        validateBrandForm(brandForm);
        normalizeBrandForm(brandForm);
        brandApi.updateBrand(id, convertBrandFormtoBrandPojo(brandForm));
    }
}
