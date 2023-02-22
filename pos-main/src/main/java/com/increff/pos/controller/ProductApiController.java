package com.increff.pos.controller;

import com.increff.pos.dto.ProductDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ProductApiController {
    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Adds a product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void addProduct(@RequestBody ProductForm productForm) throws ApiException {
        productDto.addProduct(productForm);
    }

    @ApiOperation(value = "Gets a product by ID")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
    public ProductData getProduct(@PathVariable int id) throws ApiException {
        return productDto.getProduct(id);
    }

    @ApiOperation(value = "Gets a product by barcode")
    @RequestMapping(path = "/api/product/barcode/{barcode}", method = RequestMethod.GET)
    public ProductData getProductByBarcode(@PathVariable String barcode) throws ApiException {
        return productDto.getProductByBarcode(barcode);
    }

    @ApiOperation(value = "Gets list of all products")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductData> getAllProducts() throws ApiException {
        return productDto.getAllProducts();
    }

    @ApiOperation(value = "Updates a product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable int id, @RequestBody ProductForm productForm) throws ApiException {
        productDto.updateProduct(id, productForm);
    }
}
