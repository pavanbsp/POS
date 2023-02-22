package com.increff.pos.model.form;

import com.increff.pos.exception.ApiException;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class SalesReportForm {

    private String brand;
    private String category;
    private ZonedDateTime from;
    private ZonedDateTime to;

    //FORMAT CHECK VALIDATION
    public void setFrom(String from) throws ApiException {
        try {
            this.from = ZonedDateTime.parse(from);
        } catch (Throwable e) {
            throw new ApiException("Invalid! The from date time format must be of the Format ZonedDateTime");
        }
    }

    public void setTo(String to) throws ApiException {
        try {
            this.to = ZonedDateTime.parse(to);
        } catch (Throwable e) {
            throw new ApiException("Invalid! The to date time format must be of the Format ZonedDateTime");
        }
    }
}
