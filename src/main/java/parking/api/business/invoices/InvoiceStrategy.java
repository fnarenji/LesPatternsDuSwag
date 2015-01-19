package parking.api.business.invoices;

import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;

/**
 * Created by loic on 17/01/15.
 */
public interface InvoiceStrategy {

    public Invoice computeInvoice(Vehicle baseVehicle, ParkingSpot baseParkingSpot);

}
