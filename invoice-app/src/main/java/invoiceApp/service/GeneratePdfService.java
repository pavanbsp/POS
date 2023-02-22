package invoiceApp.service;

import invoiceApp.model.xml.*;
import invoiceApp.util.CreateXmlFile;
import invoiceApp.util.CreatePdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.Base64;

@Service
public class GeneratePdfService {
    @Autowired
    CreateXmlFile createXmlFile;

    @Autowired
    CreatePdf createPdf;
    public String getEncodedInvoicePdf(OrderInvoiceXmlList orderInvoiceXmlList) throws Exception {
        createXmlFile.generateXml(new File("./src/main/resources/invoice.xml"), orderInvoiceXmlList, OrderInvoiceXmlList.class);
        byte[] bytes = createPdf.generatePDF(new File("./src/main/resources/invoice.xml"), new StreamSource("./src/main/resources/invoice.xsl"));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String getEncodedInventoryReportPdf(InventoryReportXmlList inventoryReportXmlList) throws Exception {
        createXmlFile.generateXml(new File("./src/main/resources/inventoryReport.xml"), inventoryReportXmlList, InventoryReportXmlList.class);
        byte[] bytes = createPdf.generatePDF(new File("./src/main/resources/inventoryReport.xml"), new StreamSource("./src/main/resources/inventoryReport.xsl"));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String getEncodedDailyReportPdf(DailyReportXmlList dailyReportXmlList) throws Exception {
        createXmlFile.generateXml(new File("./src/main/resources/dailyReport.xml"), dailyReportXmlList, DailyReportXmlList.class);
        byte[] bytes = createPdf.generatePDF(new File("./src/main/resources/dailyReport.xml"), new StreamSource("./src/main/resources/dailyReport.xsl"));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String getEncodedSalesReportPdf(SalesReportXmlList salesReportXmlList) throws Exception {
        createXmlFile.generateXml(new File("./src/main/resources/salesReport.xml"), salesReportXmlList, SalesReportXmlList.class);
        byte[] bytes = createPdf.generatePDF(new File("./src/main/resources/salesReport.xml"), new StreamSource("./src/main/resources/salesReport.xsl"));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String getEncodedBrandReportPdf(BrandReportXmlList brandReportXmlList) throws Exception {
        createXmlFile.generateXml(new File("./src/main/resources/brandReport.xml"), brandReportXmlList, BrandReportXmlList.class);
        byte[] bytes = createPdf.generatePDF(new File("./src/main/resources/brandReport.xml"), new StreamSource("./src/main/resources/brandReport.xsl"));
        return Base64.getEncoder().encodeToString(bytes);
    }
}
