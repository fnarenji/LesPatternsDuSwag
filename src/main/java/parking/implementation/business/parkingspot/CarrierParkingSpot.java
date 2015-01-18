package parking.implementation.business.parkingspot;

import parking.api.business.parkingspot.BaseParkingSpot;
import parking.implementation.business.vehicle.Car;
import parking.implementation.business.vehicle.Carrier;
import parking.implementation.business.vehicle.Motorbike;

/**
 * Created by SKNZ on 31/12/2014.
 */
public class CarrierParkingSpot extends BaseParkingSpot {
    public CarrierParkingSpot(Integer id) {
        this.id = id;
        vehicleTypeFits.put(Carrier.class, true);
        vehicleTypeFits.put(Car.class, true);
        vehicleTypeFits.put(Motorbike.class, true);
    }
}
