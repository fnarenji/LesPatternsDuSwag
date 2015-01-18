package parking.implementation.business.invoices;

import parking.api.business.invoices.Invoice;

/**
 * Created by Thomas on 18/01/2015.
 */
public class FrenchExporter extends TranslatedExporter {
    public FrenchExporter(Invoice invoice){
        super(invoice);
    }

    @Override
    public String export() {

        String toExport = "#Facture n°" + getInvoice().getInvoiceNumber();
        toExport += "\n";
        toExport += "##Prix : " + getInvoice().getInvoicePrice();

        return toExport;
    }
}
