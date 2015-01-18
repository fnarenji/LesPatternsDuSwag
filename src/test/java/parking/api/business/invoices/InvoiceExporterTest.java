package parking.api.business.invoices;

import org.junit.Before;
import org.junit.Test;
import parking.implementation.business.invoices.EnglishExporter;
import parking.implementation.business.invoices.FrenchExporter;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thomas on 18/01/2015.
 */
public class InvoiceExporterTest {
    private Invoice invoice;

    @Before
    public void setInvoice(){
        invoice = new Invoice(42, 18.92, new Object());
    }

    @Test
    public void testFrenchExport(){
        InvoiceExporter exporter = new FrenchExporter(invoice);

        String invoiceExported = exporter.export();

        assertEquals(invoiceExported.toLowerCase().contains("" + invoice.getInvoiceNumber()), true);
        assertEquals(invoiceExported.toLowerCase().contains("" + invoice.getInvoicePrice()), true);
        assertEquals(invoiceExported.toLowerCase().contains("facture"), true);
        assertEquals(invoiceExported.toLowerCase().contains("prix"), true);
    }

    @Test
    public void testEnglishExport(){
        InvoiceExporter exporter = new EnglishExporter(invoice);

        String invoiceExported = exporter.export();

        assertEquals(invoiceExported.toLowerCase().contains("" + invoice.getInvoiceNumber()), true);
        assertEquals(invoiceExported.toLowerCase().contains("" + invoice.getInvoicePrice()), true);
        assertEquals(invoiceExported.toLowerCase().contains("invoice"), true);
        assertEquals(invoiceExported.toLowerCase().contains("price"), true);
    }
}
