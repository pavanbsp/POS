package com.increff.pos.model.xml;

import com.increff.pos.model.data.OrderInvoiceData;
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
public class OrderInvoiceXmlList {
    @XmlElement(name = "order_id")
    private Integer order_id;
    @XmlElement(name = "datetime")
    private String datetime;
    @XmlElement(name = "total")
    private Double total;
    @XmlElement(name = "item")
    private List<OrderInvoiceData> orderInvoiceData;
}
