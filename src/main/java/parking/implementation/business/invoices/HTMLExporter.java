package parking.implementation.business.invoices;

import parking.api.business.invoices.Invoice;
import parking.api.business.invoices.InvoiceExporter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Thomas on 18/01/2015.
 */
public class HTMLExporter implements InvoiceExporter {

    private InvoiceExporter invoiceExporter;

    public HTMLExporter(InvoiceExporter invoiceExporter) {
        this.invoiceExporter = invoiceExporter;
    }

    public InvoiceExporter getTranslatedExporter() {
        return invoiceExporter;
    }

    @Override
    public String export() {
        StringTokenizer st = new StringTokenizer(invoiceExporter.export());
        String toExport = "<html>\n";
        toExport += "<body>\n";

        List<String> document = new ArrayList<>();

        while (st.hasMoreTokens()) {
            document.add(st.nextToken());
        }

        Iterator<String> it = document.iterator();

        int hValue = 0;
        while (it.hasNext()) {
            String s = it.next();

            if (s.contains("#")) {
                if (hValue != 0) {
                    toExport += "</h" + hValue + ">\n";
                }
                toExport += "<h" + s.length() + ">";
                hValue = s.length();
            } else {
                toExport += s + " ";
            }
        }
        toExport += "</h" + hValue + ">\n";
        toExport += "</body>\n";
        toExport += "</html>";
        return toExport;
    }

    @Override
    public Invoice getInvoice() {
        return invoiceExporter.getInvoice();
    }
}
