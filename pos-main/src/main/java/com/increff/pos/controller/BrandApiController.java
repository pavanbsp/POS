package com.increff.pos.controller;

import com.increff.pos.dto.BrandDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class BrandApiController {
    @Autowired
    private BrandDto brandDto;

    @ApiOperation(value = "Adds a brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void addBrand(@RequestBody BrandForm brandForm) throws ApiException {
        brandDto.addBrand(brandForm);
    }

    @ApiOperation(value = "Gets a brand by ID")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData getBrand(@PathVariable int id) throws ApiException {
        return brandDto.getBrand(id);
    }

    @ApiOperation(value = "Gets list of all brands")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAllBrands() throws ApiException {
        return brandDto.getAllBrands();
    }

    @ApiOperation(value = "Updates a brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void updateBrand(@PathVariable int id, @RequestBody BrandForm brandForm) throws ApiException {
        brandDto.updateBrand(id, brandForm);
    }


}
