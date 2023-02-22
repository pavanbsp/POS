package com.increff.pos.controller;

import com.increff.pos.dto.ReportsDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.BrandReportData;
import com.increff.pos.model.data.DailyReportData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.DailyReportForm;
import com.increff.pos.model.form.SalesReportForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class ReportsApiController {
    @Autowired
    private ReportsDto reportsDto;

    @ApiOperation(value = "Gets the sales report")
    @RequestMapping(path = "/api/reports/sales-report", method = RequestMethod.POST)
    public List<SalesReportData> getSalesReport(@RequestBody SalesReportForm salesReportForm) throws ApiException {
        return reportsDto.getSalesReport(salesReportForm);
    }

    @ApiOperation(value = "Gets sales report pdf")
    @RequestMapping(path = "/api/reports/sales-report/download", method = RequestMethod.POST)
    public String getSalesReportPdf(@RequestBody SalesReportForm salesReportForm) throws Exception {
        return reportsDto.getSalesReportPdf(salesReportForm);
    }

    @ApiOperation(value = "Gets the daily report")
    @RequestMapping(path = "/api/reports/dailyReport", method = RequestMethod.POST)
    public List<DailyReportData> getDailyReport(@RequestBody DailyReportForm dailyReportForm) throws ApiException {
        return reportsDto.getDailyReport(dailyReportForm);
    }

    @ApiOperation(value = "Gets PDF of daily report")
    @RequestMapping(path = "/api/reports/dailyReport/download", method = RequestMethod.POST)
    public String getDailyReportPdf(@RequestBody DailyReportForm dailyReportForm) throws Exception {
        return reportsDto.getDailyReportPdf(dailyReportForm);
    }

    @ApiOperation(value = "Gets the inventory report")
    @RequestMapping(path = "/api/reports/inventoryReport", method = RequestMethod.POST)
    public List<InventoryReportData> getInventoryReport(@RequestBody BrandForm brandForm) throws ApiException {
        return reportsDto.getInventoryReport(brandForm);
    }

    @ApiOperation(value = "Gets PDF of inventory report")
    @RequestMapping(path = "/api/reports/inventoryReport/download", method = RequestMethod.POST)
    public String getInventoryReportPdf(@RequestBody BrandForm brandForm) throws Exception {
        return reportsDto.getInventoryReportPdf(brandForm);
    }

    @ApiOperation(value = "Gets the brand report")
    @RequestMapping(path = "/api/reports/brandReport", method = RequestMethod.POST)
    public List<BrandReportData> getBrandReport(@RequestBody BrandForm brandForm) throws ApiException {
        return reportsDto.getBrandReport(brandForm);
    }

    @ApiOperation(value = "Gets PDF of brand report")
    @RequestMapping(path = "/api/reports/brandReport/download", method = RequestMethod.POST)
    public String getBrandReportPdf(@RequestBody BrandForm brandForm) throws Exception {
        return reportsDto.getBrandReportPdf(brandForm);
    }
}
