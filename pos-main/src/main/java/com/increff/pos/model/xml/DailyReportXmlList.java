package com.increff.pos.model.xml;

import com.increff.pos.model.data.DailyReportData;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class DailyReportXmlList {
    @XmlElement(name = "fromDate")
    private String fromDate;
    @XmlElement(name = "toDate")
    private String toDate;
    @XmlElement(name = "item")
    private List<DailyReportData> dailyReportDataList;
}
