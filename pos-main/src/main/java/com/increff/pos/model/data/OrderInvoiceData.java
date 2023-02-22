package com.increff.pos.model.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@Setter
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInvoiceData {
    @XmlElement
    private String name;
    @XmlElement
    private Double mrp;
    @XmlElement
    private Integer quantity;

}
