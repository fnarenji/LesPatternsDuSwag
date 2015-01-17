package parking.implementation.business.invoices;

import parking.api.business.invoices.Invoice;
import parking.api.business.invoices.InvoiceExporter;

/**
 * Created by Thomas on 18/01/2015.
 */
public class FrenchExporter extends TranslatedExporter {
    public FrenchExporter(Invoice invoice){
        super(invoice);
    }

    @Override
    public void export() {

    }
}
