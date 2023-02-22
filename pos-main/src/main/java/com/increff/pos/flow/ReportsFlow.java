package com.increff.pos.flow;

import com.increff.pos.api.*;
import com.increff.pos.client.InvoiceClient;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.BrandReportData;
import com.increff.pos.model.data.DailyReportData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.DailyReportForm;
import com.increff.pos.model.form.SalesReportForm;
import com.increff.pos.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.increff.pos.helper.DailyReportHelper.convertToDailyReportDataList;
import static com.increff.pos.helper.ReportsHelper.*;
import static com.increff.pos.util.NormalizeFormUtil.normalizeSalesReportForm;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class ReportsFlow {
    @Autowired
    private OrdersApi ordersApi;
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private ProductApi productApi;
    @Autowired
    private OrderItemsApi orderItemsApi;
    @Autowired
    private DailyReportApi dailyReportApi;
    @Autowired
    private InventoryApi inventoryApi;
    @Autowired
    private InvoiceClient invoiceClient;

    public List<DailyReportData> getDailyReport(DailyReportForm dailyReportForm) throws ApiException {
        List<DailyReportData> dailyReportDataList = convertToDailyReportDataList(dailyReportApi.selectDailyReportByFromDate(dailyReportForm.getFrom(), dailyReportForm.getTo()));
        if (dailyReportDataList.size() == 0) {
            throw new ApiException("No reports exists for the given dates");
        }
        return dailyReportDataList;
    }

    public List<SalesReportData> getSalesReport(SalesReportForm salesReportForm) throws ApiException {
        List<SalesReportData> salesReportsDataListData = getSalesReportUtil(salesReportForm);
        return salesReportsDataListData;
    }

    private List<SalesReportData> getSalesReportUtil(SalesReportForm salesReportForm) throws ApiException {
        List<OrdersPojo> orderPojoList = ordersApi.selectByFromDate(salesReportForm.getFrom(), salesReportForm.getTo());
        List<Integer> orderIdList = new ArrayList<>();
        for (OrdersPojo ordersPojo : orderPojoList) {
            orderIdList.add(ordersPojo.getId());
        }
        if (orderIdList.size() == 0) {
            throw new ApiException("No Sales for a given range");
        }
        List<OrderItemsPojo> orderItemsPojoList = orderItemsApi.getFromOrderIdList(orderIdList);
        List<ProductPojo> productPojoList = new ArrayList<>();
        //if user do not entered both brand and category
        if (Objects.equals(salesReportForm.getBrand(), "") && Objects.equals(salesReportForm.getCategory(), "")) {
            productPojoList = productApi.getAllProducts();
        }
        //if user entered brand
        else if (!Objects.equals(salesReportForm.getBrand(), "") && Objects.equals(salesReportForm.getCategory(), "")) {
            List<BrandPojo> brandPojoList = brandApi.selectByBrand(salesReportForm.getBrand());
            if (brandPojoList.size() == 0) {
                throw new ApiException("No brand exists with the given input");
            }
            for (BrandPojo brandPojo : brandPojoList) {
                List<ProductPojo> productPojo = productApi.getProductsByBrandCategoryId(brandPojo.getId());
                productPojoList.addAll(productPojo);
            }
        }
        //if user entered category
        else if (!Objects.equals(salesReportForm.getCategory(), "") && Objects.equals(salesReportForm.getBrand(), "")) {
            List<BrandPojo> brandPojoList = brandApi.selectByCategory(salesReportForm.getCategory());
            if (brandPojoList.size() == 0) {
                throw new ApiException("No category exists with the given input");
            }
            for (BrandPojo brandPojo : brandPojoList) {
                List<ProductPojo> productPojo = productApi.getProductsByBrandCategoryId(brandPojo.getId());
                productPojoList.addAll(productPojo);
            }
        }
        //if user entered both brand and category
        else {
            BrandPojo brandPojo = brandApi.checkSelectByBrandCategory(salesReportForm.getBrand(), salesReportForm.getCategory());
            List<ProductPojo> productPojo = productApi.getProductsByBrandCategoryId(brandPojo.getId());
            productPojoList.addAll(productPojo);
        }

        List<SalesReportData> report = new ArrayList<>();
        Map<Integer, ProductPojo> productPojoMap = new HashMap<>();
        for (ProductPojo productPojo : productPojoList) {
            productPojoMap.put(productPojo.getId(), productPojo);
        }
        for (OrderItemsPojo orderItemsPojo : orderItemsPojoList) {
            if (productPojoMap.containsKey(orderItemsPojo.getProductId())) {
                ProductPojo productPojo = productPojoMap.get(orderItemsPojo.getProductId());
                BrandPojo brandPojo = brandApi.selectById(productPojo.getBrand_category_id());
                SalesReportData salesReportData = new SalesReportData(brandPojo.getBrand(), brandPojo.getCategory(), orderItemsPojo.getQuantity(), orderItemsPojo.getSellingPrice() * orderItemsPojo.getQuantity());
                report.add(salesReportData);
            }
        }
        return filterReport(report);
    }

    private List<SalesReportData> filterReport(List<SalesReportData> report) {//function to add revenue of a unique pair
        Map<String, SalesReportData> map = new HashMap<>();
        for (SalesReportData data : report) {
            String pair = data.getBrand() + "/-/" + data.getCategory();
            if (map.containsKey(pair)) {
                SalesReportData salesReportData = map.get(pair);
                int netQuantity = salesReportData.getQuantity() + data.getQuantity();
                double netRevenue = salesReportData.getRevenue() + data.getRevenue();
                salesReportData.setRevenue(netRevenue);
                salesReportData.setQuantity(netQuantity);
                map.put(pair, salesReportData);
            } else {
                map.put(pair, data);
            }
        }
        List<SalesReportData> salesReportDataList = new ArrayList<>(map.values());
        return salesReportDataList;
    }

    public List<BrandReportData> getBrandReport(BrandForm brandForm) throws ApiException {
        List<BrandPojo> brandPojoList = new ArrayList<>();
        //if user do not entered both brand and category
        if (Objects.equals(brandForm.getBrand(), "") && Objects.equals(brandForm.getCategory(), "")) {
            brandPojoList = brandApi.getAllBrands();
        }
        //if user entered brand
        else if (!Objects.equals(brandForm.getBrand(), "") && Objects.equals(brandForm.getCategory(), "")) {
            brandPojoList = brandApi.selectByBrand(brandForm.getBrand());
            if (brandPojoList.size() == 0) {
                throw new ApiException("No brand exists with the given input");
            }
        }
        //if user entered category
        else if (!Objects.equals(brandForm.getCategory(), "") && Objects.equals(brandForm.getBrand(), "")) {
            brandPojoList = brandApi.selectByCategory(brandForm.getCategory());
            if (brandPojoList.size() == 0) {
                throw new ApiException("No category exists with the given input");
            }
        }
        //if user entered both brand and category
        else {
            BrandPojo brandPojo = brandApi.checkSelectByBrandCategory(brandForm.getBrand(), brandForm.getCategory());
            brandPojoList.add(brandPojo);
        }
        List<BrandReportData> brandReportDataList = new ArrayList<>();
        for (BrandPojo brandPojo : brandPojoList) {
            BrandReportData brandReportData = new BrandReportData();
            brandReportData.setBrand(brandPojo.getBrand());
            brandReportData.setCategory(brandPojo.getCategory());
            brandReportDataList.add(brandReportData);
        }
        if (brandReportDataList.size() == 0) {
            throw new ApiException("No data for the given brand-category");
        }
        return brandReportDataList;
    }

    public List<InventoryReportData> getInventoryReport(BrandForm brandForm) throws ApiException {
        List<ProductPojo> productPojoList = new ArrayList<>();
        //if user do not entered both brand and category
        if (Objects.equals(brandForm.getBrand(), "") && Objects.equals(brandForm.getCategory(), "")) {
            productPojoList = productApi.getAllProducts();
        }
        //if user entered brand
        else if (!Objects.equals(brandForm.getBrand(), "") && Objects.equals(brandForm.getCategory(), "")) {
            List<BrandPojo> brandPojoList = brandApi.selectByBrand(brandForm.getBrand());
            for (BrandPojo brandPojo : brandPojoList) {
                List<ProductPojo> productPojo = productApi.getProductsByBrandCategoryId(brandPojo.getId());
                productPojoList.addAll(productPojo);
            }
        }
        //if user entered category
        else if (!Objects.equals(brandForm.getCategory(), "") && Objects.equals(brandForm.getBrand(), "")) {
            List<BrandPojo> brandPojoList = brandApi.selectByCategory(brandForm.getCategory());
            for (BrandPojo brandPojo : brandPojoList) {
                List<ProductPojo> productPojo = productApi.getProductsByBrandCategoryId(brandPojo.getId());
                productPojoList.addAll(productPojo);
            }
        }
        //if user entered both brand and category
        else {
            BrandPojo brandPojo = brandApi.checkSelectByBrandCategory(brandForm.getBrand(), brandForm.getCategory());
            List<ProductPojo> productPojo = productApi.getProductsByBrandCategoryId(brandPojo.getId());
            productPojoList.addAll(productPojo);
        }
        List<InventoryPojo> inventoryPojoList = inventoryApi.getAllInventory();
        if (isEmpty(inventoryPojoList)) {
            throw new ApiException("Inventory is empty");
        }
        List<InventoryReportData> inventoryReportDataList = new ArrayList<>();
        for (ProductPojo productPojo : productPojoList) {
            InventoryReportData inventoryReportData = new InventoryReportData();
            InventoryPojo inventoryPojo = null;
            try {//for getting inventory if exists or null
                inventoryPojo = inventoryApi.getInventory(productPojo.getId());
            } catch (ApiException e) {
                //product do not exist in inventory
            }
            if (inventoryPojo != null) {
                BrandPojo brandPojo = brandApi.getBrand(productPojo.getBrand_category_id());
                inventoryReportData.setBrand(brandPojo.getBrand());
                inventoryReportData.setCategory(brandPojo.getCategory());
                inventoryReportData.setQuantity(inventoryPojo.getQuantity());
                inventoryReportData.setName(productPojo.getName());
                inventoryReportDataList.add(inventoryReportData);
            }
        }
        if (inventoryReportDataList.size() == 0) {
            throw new ApiException("No inventory exists for the given brand-category");
        }
        return inventoryReportDataList;
    }

    public String getInventoryReportPdf(BrandForm brandForm) throws Exception {
        List<InventoryReportData> inventoryReportDataList = getInventoryReport(brandForm);
        return invoiceClient.generateInventoryReport(convertToInventoryReportXmlList(inventoryReportDataList, brandForm));
    }

    public String getSalesReportPdf(SalesReportForm salesReportForm) throws Exception {
        List<SalesReportData> salesReportDataList = getSalesReport(salesReportForm);
        return invoiceClient.generateSalesReport(convertToSalesReportDataList(salesReportDataList, salesReportForm));
    }

    public String getBrandReportPdf(BrandForm brandForm) throws Exception {
        List<BrandReportData> brandReportDataList = getBrandReport(brandForm);
        return invoiceClient.generateBrandReport(convertToBrandReportXmlList(brandReportDataList, brandForm));
    }

    public String getDailyReportPdf(DailyReportForm dailyReportForm) throws Exception {
        List<DailyReportData> dailyReportDataList = getDailyReport(dailyReportForm);
        return invoiceClient.generateDailyReport(convertToDailyReportXmlList(dailyReportDataList, dailyReportForm));
    }
}
