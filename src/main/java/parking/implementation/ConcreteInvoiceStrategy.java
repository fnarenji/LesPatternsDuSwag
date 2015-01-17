package parking.implementation;

import org.joda.time.DateTime;
import org.joda.time.Period;

import parking.api.business.concrete.BaseParkingSpot;

import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.Vehicle;


/**
 * Created by loic on 17/01/15.
 */
public class ConcreteInvoiceStrategy implements parking.api.business.contract.InvoiceStrategy {
    
    final double TVA = 0.196;
    static int countInvoice = 0;
    
    @Override
    public Invoice computeInvoice(Vehicle vehicle, ParkingSpot parkingSpot,double priceHour) {
        double htPrice = 0;
        
        if(vehicle instanceof Carrier)
            htPrice = ((Carrier) vehicle).getWeight() * 0.25;

        Period p = new Period(((BaseParkingSpot) parkingSpot).getEnteredHour(), DateTime.now());
        htPrice = p.getHours() * priceHour;
        double ttcPrice = htPrice + (htPrice * TVA);
        
       return new Invoice(countInvoice++,ttcPrice);
    }
    
}
