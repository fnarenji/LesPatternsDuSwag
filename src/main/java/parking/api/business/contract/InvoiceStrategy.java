package parking.api.business.contract;

import parking.api.business.concrete.BaseParkingSpot;
import parking.api.business.concrete.BaseVehicle;

import parking.implementation.Invoice;

/**
 * Created by loic on 17/01/15.
 */
public interface InvoiceStrategy {

    public Invoice computeInvoice(Vehicle baseVehicle, ParkingSpot baseParkingSpot,double priceHour);
    
}
