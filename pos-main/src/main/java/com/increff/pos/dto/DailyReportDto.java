package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.flow.DailyReportFlow;
import com.increff.pos.pojo.DailyReportPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.increff.pos.helper.DailyReportHelper.convertToDailyReportpojo;

@Service
public class DailyReportDto {
    @Autowired
    private DailyReportFlow dailyReportFlow;

    public void checkUpdateDailyReport() throws ApiException {
        DailyReportPojo dailyReportPojo = convertToDailyReportpojo();
        dailyReportFlow.checkUpdateDailyReport(dailyReportPojo);
    }
}
