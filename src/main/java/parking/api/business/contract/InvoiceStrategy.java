package parking.api.business.contract;

import parking.implementation.logic.Invoice;

/**
 * Created by loic on 17/01/15.
 */
public interface InvoiceStrategy {

    public Invoice computeInvoice(Vehicle baseVehicle, ParkingSpot baseParkingSpot, double priceHour);

}
