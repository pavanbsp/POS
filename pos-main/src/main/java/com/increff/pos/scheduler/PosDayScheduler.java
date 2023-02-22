package com.increff.pos.scheduler;

import com.increff.pos.dto.DailyReportDto;
import com.increff.pos.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class PosDayScheduler {
    @Autowired
    DailyReportDto dailyReportDto;

    @Scheduled(fixedDelay = 60000)
    public void scheduleFixedDelayTask() throws ApiException {
        dailyReportDto.checkUpdateDailyReport();
    }
}
