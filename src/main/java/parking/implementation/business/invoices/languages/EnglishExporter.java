package parking.implementation.business.invoices.languages;

import parking.api.business.invoices.Invoice;
import parking.implementation.business.invoices.TranslatedExporter;

/**
 * Created by Thomas on 18/01/2015.
 */
public class EnglishExporter extends TranslatedExporter {
    public EnglishExporter(Invoice invoice){
        super(invoice);
    }

    public String export() {
        return super.translate("Invoice", "Price");
    }
}
