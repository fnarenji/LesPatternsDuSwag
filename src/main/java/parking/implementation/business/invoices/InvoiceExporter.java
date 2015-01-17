package parking.implementation.business.invoices;

import parking.api.business.invoices.Invoice;

/**
 * Created by Thomas on 17/01/2015.
 */
public interface InvoiceExporter {
    Invoice invoice = null;

    public abstract class TranslatedExporter {
        private String language;
        public String getLanguage(){ return language; }
        public void setLanguage(String l){ language = l; }
    }

    public void export();
}
