package invoiceApp.model.data;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Getter
@Setter
public class SalesReportData {
    private String brand;
    private String category;
    private int quantity;
    private double revenue;
}
