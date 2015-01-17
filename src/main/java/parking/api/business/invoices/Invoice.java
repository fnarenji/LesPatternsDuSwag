package parking.api.business.invoices;

import java.io.Serializable;

/**
 * Created by loic on 17/01/15.
 */
public class Invoice implements Serializable{

    private int invoiceNumber;
    private double invoicePrice;
    private Object owner;

    public Invoice(int invoiceNumber, double invoicePrice, Object owner) {
        this.invoiceNumber = invoiceNumber;
        this.invoicePrice = invoicePrice;
        this.owner = owner;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public double getInvoicePrice() {
        return invoicePrice;
    }

    public Object getOwner(){ return owner; }
}
