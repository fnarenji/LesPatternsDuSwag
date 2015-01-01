package parking.implementation.parkingspots;

import parking.api.business.concrete.Vehicle;
import parking.api.business.concrete.BaseParkingSpot;
import parking.api.business.contract.ParkingSpot;
import parking.implementation.vehicles.Car;
import parking.implementation.vehicles.Carrier;
import parking.implementation.vehicles.Motorbike;

/**
 * Created by SKNZ on 31/12/2014.
 */
public class CarrierParkingSpot extends BaseParkingSpot {
    static {
        vehicleTypeFits.put(Carrier.class,      true);
        vehicleTypeFits.put(Car.class,          true);
        vehicleTypeFits.put(Motorbike.class,    true);
    }

    public static void main(String[] args) {
        ParkingSpot spot = new CarrierParkingSpot();
        Vehicle vehicle = new Carrier();
        System.out.println(spot.fits(vehicle));
        vehicle = new Motorbike();
        System.out.println(spot.fits(vehicle));
    }
}
