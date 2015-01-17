package parking.implementation.logic;

/**
 * Created by loicpauletto on 17/01/15.
 */
public class Invoice {
    
    private int invoiceNuber;
    private double invoicePrice;

    public Invoice(int invoiceNuber, double invoicePrice) {
        this.invoiceNuber = invoiceNuber;
        this.invoicePrice = invoicePrice;
    }

    public int getInvoiceNuber() {
        return invoiceNuber;
    }

    public double getInvoicePrice() {
        return invoicePrice;
    }
}
