package parking.implementation.business.invoices;

import parking.api.business.invoices.Invoice;
import parking.api.business.invoices.InvoiceExporter;

/**
 * Created by sknz on 1/18/15.
 */
public abstract class TranslatedExporter implements InvoiceExporter {
    private Invoice invoice;

    public TranslatedExporter(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    protected String translate(String invoiceName, String priceName){
        String toExport = "#" + invoiceName + " n°" + getInvoice().getInvoiceNumber();
        toExport += "\n";
        toExport += "##" + priceName + " : " + getInvoice().getInvoicePrice();

        return toExport;
    }
}
