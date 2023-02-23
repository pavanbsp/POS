package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.flow.ReportsFlow;
import com.increff.pos.model.data.BrandReportData;
import com.increff.pos.model.data.DailyReportData;
import com.increff.pos.model.data.InventoryReportData;
import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.DailyReportForm;
import com.increff.pos.model.form.SalesReportForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.increff.pos.util.NormalizeFormUtil.normalizeSalesReportForm;
import static com.increff.pos.util.ValidateFormUtil.*;

@Service
public class ReportsDto {
    @Autowired
    private ReportsFlow reportsFlow;

    public List<DailyReportData> getDailyReport(DailyReportForm dailyReportForm) throws ApiException {
        validateDailyReportForm(dailyReportForm);
        return reportsFlow.getDailyReport(dailyReportForm);
    }

    public List<SalesReportData> getSalesReport(SalesReportForm salesReportForm) throws ApiException {
        validateSalesForm(salesReportForm);
        normalizeSalesReportForm(salesReportForm);
        return reportsFlow.getSalesReport(salesReportForm);
    }

    public List<BrandReportData> getBrandReport(BrandForm brandForm) throws ApiException {
        validateBrandReportForm(brandForm);
        return reportsFlow.getBrandReport(brandForm);
    }

    public List<InventoryReportData> getInventoryReport(BrandForm brandForm) throws ApiException {
        validateInventoryReportForm(brandForm);
        return reportsFlow.getInventoryReport(brandForm);
    }

    public String getInventoryReportPdf(BrandForm brandForm) throws ApiException {
        return reportsFlow.getInventoryReportPdf(brandForm);
    }

    public String getSalesReportPdf(SalesReportForm salesReportForm) throws ApiException {
        return reportsFlow.getSalesReportPdf(salesReportForm);
    }

    public String getBrandReportPdf(BrandForm brandForm) throws ApiException {
        return reportsFlow.getBrandReportPdf(brandForm);
    }

    public String getDailyReportPdf(DailyReportForm dailyReportForm) throws ApiException {
        return reportsFlow.getDailyReportPdf(dailyReportForm);
    }
}