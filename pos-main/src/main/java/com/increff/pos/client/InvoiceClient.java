package com.increff.pos.client;

import com.increff.pos.model.xml.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InvoiceClient {

    @Value("${invoice.url}")
    private String fopUrl;

    public String generateInvoice(OrderInvoiceXmlList orderInvoiceXmlList) {
        RestTemplate restTemplate = new RestTemplate();
        String url = fopUrl + "/generate/invoice";
        String response = restTemplate.postForObject(url, orderInvoiceXmlList, String.class);
        return response;
    }

    public String generateInventoryReport(InventoryReportXmlList inventoryReportXmlList) {
        RestTemplate restTemplate = new RestTemplate();
        String url = fopUrl + "/generate/inventoryReportPdf";
        String response = restTemplate.postForObject(url, inventoryReportXmlList, String.class);
        return response;
    }

    public String generateDailyReport(DailyReportXmlList dailyReportXmlList) {
        RestTemplate restTemplate = new RestTemplate();
        String url = fopUrl + "/generate/dailyReportPdf";
        String response = restTemplate.postForObject(url, dailyReportXmlList, String.class);
        return response;
    }

    public String generateSalesReport(SalesReportXmlList salesReportXmlList) {
        RestTemplate restTemplate = new RestTemplate();
        String url = fopUrl + "/generate/salesReportPdf";
        String response = restTemplate.postForObject(url, salesReportXmlList, String.class);
        return response;
    }

    public String generateBrandReport(BrandReportXmlList brandReportXmlList) {
        RestTemplate restTemplate = new RestTemplate();
        String url = fopUrl + "/generate/brandReportPdf";
        String response = restTemplate.postForObject(url, brandReportXmlList, String.class);
        return response;
    }
}
