package parking.implementation.business.invoices;

import parking.api.business.invoices.Invoice;

/**
 * Created by Thomas on 17/01/2015.
 */
public interface InvoiceExporter {
    public void export(Invoice invoice);
}
