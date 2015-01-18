package parking.implementation.business.invoices;

import parking.api.business.invoices.InvoiceExporter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas on 18/01/2015.
 */
public class HTMLExporter implements InvoiceExporter {

    private TranslatedExporter translatedExporter;

    public HTMLExporter(TranslatedExporter translatedExporter){
        this.translatedExporter = translatedExporter;
    }

    @Override
    public String export() {
        return null;
    }
}
