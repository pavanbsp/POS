package com.increff.pos.helper;

import com.increff.pos.model.data.BrandReportData;
import com.increff.pos.model.data.DailyReportData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.DailyReportForm;
import com.increff.pos.model.form.SalesReportForm;
import com.increff.pos.model.xml.BrandReportXmlList;
import com.increff.pos.model.xml.DailyReportXmlList;
import com.increff.pos.model.xml.InventoryReportXmlList;
import com.increff.pos.model.xml.SalesReportXmlList;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportsHelper {

    public static SalesReportXmlList convertToSalesReportDataList(List<SalesReportData> salesReportDataList, SalesReportForm salesReportForm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SalesReportXmlList salesReportXmlList = new SalesReportXmlList();
        salesReportXmlList.setBrand(salesReportForm.getBrand());
        salesReportXmlList.setCategory(salesReportForm.getCategory());
        if (salesReportXmlList.getBrand() == "") salesReportXmlList.setBrand("all brands");
        if (salesReportXmlList.getCategory() == "") salesReportXmlList.setCategory("all categories");
        salesReportXmlList.setFromDate(salesReportForm.getFrom().format(formatter));
        salesReportXmlList.setToDate(salesReportForm.getTo().format(formatter));
        salesReportXmlList.setSalesReportDataList(salesReportDataList);
        return salesReportXmlList;
    }

    public static InventoryReportXmlList convertToInventoryReportXmlList(List<InventoryReportData> inventoryPojoList, BrandForm brandForm) {
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss");
        InventoryReportXmlList inventoryReportXmlList = new InventoryReportXmlList();
        inventoryReportXmlList.setDatetime(date.format(formatter));
        inventoryReportXmlList.setInventoryReportData(inventoryPojoList);
        inventoryReportXmlList.setBrand(brandForm.getBrand());
        inventoryReportXmlList.setCategory(brandForm.getCategory());
        if (inventoryReportXmlList.getBrand() == "") {
            inventoryReportXmlList.setBrand("all brands");
        }
        if (inventoryReportXmlList.getCategory() == "") {
            inventoryReportXmlList.setCategory("all categories");
        }
        return inventoryReportXmlList;
    }

    public static DailyReportXmlList convertToDailyReportXmlList(List<DailyReportData> dailyReportDataList, DailyReportForm dailyReportForm) {
        DailyReportXmlList dailyReportXmlList = new DailyReportXmlList();
        dailyReportXmlList.setFromDate(dailyReportForm.getFrom());
        dailyReportXmlList.setToDate(dailyReportForm.getTo());
        dailyReportXmlList.setDailyReportDataList(dailyReportDataList);
        return dailyReportXmlList;
    }

    public static BrandReportXmlList convertToBrandReportXmlList(List<BrandReportData> brandReportDataList, BrandForm brandForm) {
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss");
        BrandReportXmlList brandReportXmlList = new BrandReportXmlList();
        brandReportXmlList.setBrand(brandForm.getBrand());
        brandReportXmlList.setCategory(brandForm.getCategory());
        brandReportXmlList.setDatetime(date.format(formatter));
        brandReportXmlList.setBrandReportData(brandReportDataList);
        if (brandReportXmlList.getBrand() == "") {
            brandReportXmlList.setBrand("all brands");
        }
        if (brandReportXmlList.getCategory() == "") {
            brandReportXmlList.setCategory("all categories");
        }
        return brandReportXmlList;
    }
}
