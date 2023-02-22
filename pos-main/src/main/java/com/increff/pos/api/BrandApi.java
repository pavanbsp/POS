package com.increff.pos.api;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandApi {

    @Autowired
    private BrandDao brandDao;

    public void addBrand(BrandPojo brandPojo) throws ApiException {
        checkUniqueBrand(brandPojo.getBrand(), brandPojo.getCategory());
        brandDao.insertBrand(brandPojo);
    }

    public BrandPojo getBrand(int id) throws ApiException {
        return getCheckBrand(id);
    }

    public List<BrandPojo> getAllBrands() throws ApiException {
        return brandDao.selectAllBrands();
    }

    public void updateBrand(int id, BrandPojo brandPojo) throws ApiException {
        BrandPojo brandPojoUpdated = getCheckBrand(id);
        if (!Objects.equals(brandPojo.getCategory(), brandPojoUpdated.getCategory()) || !Objects.equals(brandPojo.getBrand(), brandPojoUpdated.getBrand())) {
            checkUniqueBrand(brandPojo.getBrand(), brandPojo.getCategory());
        }
        brandPojoUpdated.setBrand(brandPojo.getBrand());
        brandPojoUpdated.setCategory(brandPojo.getCategory());
    }

    public BrandPojo getCheckBrand(int id) throws ApiException {
        BrandPojo brandPojo = brandDao.selectBrand(id);
        if (brandPojo == null) {
            throw new ApiException("Brand with given ID does not exist, id: " + id);
        }
        return brandPojo;
    }

    private void checkUniqueBrand(String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandDao.selectBrand(brand, category);
        if (brandPojo != null) {
            throw new ApiException("Brand category combination must be unique");
        }
    }

    public BrandPojo checkSelectByBrandCategory(String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandDao.selectBrand(brand, category);
        if (brandPojo == null) {
            throw new ApiException("Brand category combination doesn't exists");
        }
        return brandPojo;
    }

    public List<BrandPojo> selectByBrand(String brand) throws ApiException {
        List<BrandPojo> brandPojoList = brandDao.selectByBrand(brand);
        return brandPojoList;
    }

    public List<BrandPojo> selectByCategory(String category) throws ApiException {
        List<BrandPojo> brandPojoList = brandDao.selectByCategory(category);
        return brandPojoList;
    }

    public BrandPojo selectById(int id) throws ApiException {
        BrandPojo brandPojo = brandDao.selectBrand(id);
        if (brandPojo == null) {
            throw new ApiException("No brand exists with the given id");
        }
        return brandPojo;
    }

}
