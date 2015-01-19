package parking.api.business.invoices;


/**
 * Created by Thomas on 17/01/2015.
 */
public interface InvoiceExporter {
    public String export();

    public Invoice getInvoice();
}
