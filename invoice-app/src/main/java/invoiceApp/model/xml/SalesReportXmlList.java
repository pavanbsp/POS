package invoiceApp.model.xml;

import invoiceApp.model.data.SalesReportData;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter@Setter
public class SalesReportXmlList {

    @XmlElement(name = "fromDate")
    private String fromDate;

    @XmlElement(name = "toDate")
    private String toDate;

    @XmlElement(name = "brand")
    private String brand;

    @XmlElement(name = "category")
    private String category;
    @XmlElement(name = "item")
    private List<SalesReportData> salesReportDataList;
}

