package com.increff.pos.api;

import com.increff.pos.dao.DailyReportDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.DailyReportPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class DailyReportApi {

    @Autowired
    private DailyReportDao dailyReportDao;

    public void addDailyReport(DailyReportPojo dailyReportPojo) throws ApiException {
        dailyReportDao.insertDailyReport(dailyReportPojo);
    }

    public DailyReportPojo getDailyReport(String dateTime) throws ApiException {
        return dailyReportDao.selectDailyReport(dateTime);
    }

    public List<DailyReportPojo> selectDailyReportByFromDate(String from, String to) {
        return dailyReportDao.selectByFromDate(from, to);
    }

    public List<DailyReportPojo> getAllDailyReports() throws ApiException {
        return dailyReportDao.selectAllDailyReports();
    }

    public void updateDailyReport(String dateTime, DailyReportPojo dailyReportPojo) throws ApiException {
        DailyReportPojo dailyReportPojoUpdated = dailyReportDao.selectDailyReport(dateTime);
        dailyReportPojoUpdated.setInvoiced_items_count(dailyReportPojoUpdated.getInvoiced_items_count() + dailyReportPojo.getInvoiced_items_count());
        dailyReportPojoUpdated.setInvoiced_orders_count(dailyReportPojoUpdated.getInvoiced_orders_count() + dailyReportPojo.getInvoiced_orders_count());
        dailyReportPojoUpdated.setTotal_revenue(dailyReportPojoUpdated.getTotal_revenue() + dailyReportPojo.getTotal_revenue());
    }

}
