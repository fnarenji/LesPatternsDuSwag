package parking.implementation.business.invoices.languages;

import parking.api.business.invoices.Invoice;
import parking.implementation.business.invoices.TranslatedExporter;

/**
 * Created by Thomas on 18/01/2015.
 */
public class FrenchExporter extends TranslatedExporter {
    public FrenchExporter(Invoice invoice){
        super(invoice);
    }

    public String export() {
       return super.translate("Facture", "Prix");
    }
}
