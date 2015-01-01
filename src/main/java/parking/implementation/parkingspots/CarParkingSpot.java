package parking.implementation.parkingspots;

import parking.api.business.concrete.BaseParkingSpot;
import parking.api.business.concrete.Vehicle;
import parking.api.business.contract.ParkingSpot;
import parking.implementation.vehicles.Car;
import parking.implementation.vehicles.Carrier;
import parking.implementation.vehicles.Motorbike;

/**
 * Created by SKNZ on 31/12/2014.
 */
public class CarParkingSpot extends BaseParkingSpot {
    static {
        vehicleTypeFits.put(Carrier.class,      false);
        vehicleTypeFits.put(Car.class,          true);
        vehicleTypeFits.put(Motorbike.class,    true);
    }
}
