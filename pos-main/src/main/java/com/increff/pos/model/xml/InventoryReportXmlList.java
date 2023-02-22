package com.increff.pos.model.xml;

import com.increff.pos.model.data.InventoryReportData;
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
public class InventoryReportXmlList {
    @XmlElement(name = "brand")
    private String brand;
    @XmlElement(name = "category")
    private String category;
    @XmlElement(name = "datetime")
    private String datetime;
    @XmlElement(name = "item")
    private List<InventoryReportData> inventoryReportData;

}
