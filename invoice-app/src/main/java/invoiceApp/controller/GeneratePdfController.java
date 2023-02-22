package invoiceApp.controller;

import invoiceApp.model.xml.*;
import invoiceApp.service.GeneratePdfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class GeneratePdfController {

    @Autowired
    private GeneratePdfService generatePdfService;

    @ApiOperation(value = "Returns base64-encoded string representing the PDF invoice")
    @RequestMapping(path = "/api/generate/invoice", method = RequestMethod.POST)
    public String getEncodedInvoicePdf(@RequestBody OrderInvoiceXmlList orderInvoiceXmlList) throws Exception {
        return generatePdfService.getEncodedInvoicePdf(orderInvoiceXmlList);
    }

    @ApiOperation(value = "Returns base64-encoded string representing the Inventory Report data")
    @RequestMapping(path = "/api/generate/inventoryReportPdf", method = RequestMethod.POST)
    public String getEncodedInventoryReportPdf(@RequestBody InventoryReportXmlList inventoryReportXmlList) throws Exception {
        return generatePdfService.getEncodedInventoryReportPdf(inventoryReportXmlList);
    }

    @ApiOperation(value = "Returns base64-encoded string representing the daily Report data")
    @RequestMapping(path = "/api/generate/dailyReportPdf", method = RequestMethod.POST)
    public String getEncodedDailyReportPdf(@RequestBody DailyReportXmlList dailyReportXmlList) throws Exception {
        return generatePdfService.getEncodedDailyReportPdf(dailyReportXmlList);
    }

    @ApiOperation(value = "Returns base64-encoded string representing the Sales Report data")
    @RequestMapping(path = "/api/generate/salesReportPdf", method = RequestMethod.POST)
    public String getEncodedSalesReportPdf(@RequestBody SalesReportXmlList salesReportXmlList) throws Exception {
        return generatePdfService.getEncodedSalesReportPdf(salesReportXmlList);
    }

    @ApiOperation(value = "Returns base64-encoded string representing the Brand Report data")
    @RequestMapping(path = "/api/generate/brandReportPdf", method = RequestMethod.POST)
    public String getEncodedBrandReportPdf(@RequestBody BrandReportXmlList brandReportXmlList) throws Exception {
        return generatePdfService.getEncodedBrandReportPdf(brandReportXmlList);
    }

}
