package parking.implementation.business.logistic.simple;

import org.joda.time.DateTime;
import org.joda.time.Period;
import parking.api.business.invoices.Invoice;
import parking.api.business.parkingspot.BaseParkingSpot;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;
import parking.api.business.invoices.InvoiceStrategy;
import parking.implementation.business.vehicle.Carrier;


/**
 * Created by loic on 17/01/15.
 */
public class SimpleInvoiceStrategy implements InvoiceStrategy {

    static int countInvoice = 0;
    final double TVA = 0.196;

    @Override
    public Invoice computeInvoice(Vehicle vehicle, ParkingSpot parkingSpot, double priceHour) {
        double htPrice = 0;

        if (vehicle instanceof Carrier)
            htPrice = ((Carrier) vehicle).getWeight() * 0.25;

        Period p = new Period(((BaseParkingSpot) parkingSpot).getEnteredHour(), DateTime.now());
        htPrice = p.getHours() * priceHour;
        double ttcPrice = htPrice + (htPrice * TVA);

        return new Invoice(countInvoice++, ttcPrice, new Object());
    }

}
