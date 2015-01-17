package parking.api.business.invoices;

/**
 * Created by loicpauletto on 17/01/15.
 */
public class Invoice {

    private int invoiceNumber;
    private double invoicePrice;

    public Invoice(int invoiceNuber, double invoicePrice) {
        this.invoiceNumber = invoiceNumber;
        this.invoicePrice = invoicePrice;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public double getInvoicePrice() {
        return invoicePrice;
    }
}
