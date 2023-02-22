package com.increff.pos.api;

import com.increff.pos.config.AbstractUnitTest;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.DailyReportPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.increff.pos.util.DummyPojoUtil.GetDailyReportPojo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DailyReportApiTest extends AbstractUnitTest {
    @Autowired
    private DailyReportApi dailyReportApi;

    @Test
    public void testAddDailyReport() throws ApiException {
        DailyReportPojo dailyReportPojo = GetDailyReportPojo();
        dailyReportApi.addDailyReport(dailyReportPojo);

        DailyReportPojo dailyReportPojoAdded = dailyReportApi.getDailyReport(dailyReportPojo.getDate());
        assertEquals(dailyReportPojo.getDate(), dailyReportPojoAdded.getDate());
        assertEquals(dailyReportPojo.getTotal_revenue(), dailyReportPojoAdded.getTotal_revenue(), 0);
        assertEquals(dailyReportPojo.getInvoiced_items_count(), dailyReportPojoAdded.getInvoiced_items_count());
        assertEquals(dailyReportPojo.getInvoiced_orders_count(), dailyReportPojoAdded.getInvoiced_orders_count());
    }

    @Test
    public void testGetAllDailyReports() throws ApiException {
        dailyReportApi.getAllDailyReports();
    }

    @Test
    public void TestSelectDailyReportByFromDate() throws ApiException {
        dailyReportApi.selectDailyReportByFromDate("testFrom", "testTo");
    }

    @Test
    public void testUpdateDailyRepor() throws ApiException {
        DailyReportPojo dailyReportPojo = GetDailyReportPojo();
        dailyReportApi.addDailyReport(dailyReportPojo);

        DailyReportPojo dailyReportPojoAdded = dailyReportApi.getDailyReport(dailyReportPojo.getDate());
        DailyReportPojo dailyReportPojo1 = GetDailyReportPojo();
        dailyReportPojo1.setInvoiced_orders_count(2);
        dailyReportPojo1.setInvoiced_items_count(2);
        dailyReportPojo1.setTotal_revenue(2);

        dailyReportApi.updateDailyReport(dailyReportPojoAdded.getDate(), dailyReportPojo1);
        DailyReportPojo dailyReportPojoUpdated = dailyReportApi.getDailyReport(dailyReportPojo.getDate());
        assertEquals(dailyReportPojo.getDate(), dailyReportPojoUpdated.getDate());
        assertEquals(dailyReportPojo.getInvoiced_orders_count(), dailyReportPojoUpdated.getInvoiced_orders_count());
        assertEquals(dailyReportPojo.getInvoiced_items_count(), dailyReportPojoUpdated.getInvoiced_items_count());
        assertEquals(dailyReportPojo.getTotal_revenue(), dailyReportPojoUpdated.getTotal_revenue(), 0);
    }

}