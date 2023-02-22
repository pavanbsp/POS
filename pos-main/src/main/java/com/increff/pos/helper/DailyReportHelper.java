package com.increff.pos.helper;

import com.increff.pos.model.data.DailyReportData;
import com.increff.pos.pojo.DailyReportPojo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DailyReportHelper {

    public static DailyReportPojo convertToDailyReportpojo() {
        DailyReportPojo dailyReportPojo = new DailyReportPojo();
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dailyReportPojo.setDate(date.format(formatter));
        return dailyReportPojo;
    }

    public static List<DailyReportData> convertToDailyReportDataList(List<DailyReportPojo> dailyReportPojoList) {
        List<DailyReportData> dailyReportDataList = new ArrayList<>();
        for (DailyReportPojo dailyReportPojo : dailyReportPojoList) {
            DailyReportData dailyReportData = new DailyReportData();
            dailyReportData.setDate(dailyReportPojo.getDate());
            dailyReportData.setInvoiced_orders_count(dailyReportPojo.getInvoiced_orders_count());
            dailyReportData.setInvoiced_items_count(dailyReportPojo.getInvoiced_items_count());
            dailyReportData.setTotal_revenue(dailyReportPojo.getTotal_revenue());
            dailyReportDataList.add(dailyReportData);
        }
        return dailyReportDataList;
    }
}
