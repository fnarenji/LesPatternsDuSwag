package parking.implementation;

import parking.api.business.concrete.BaseParkingSpot;

/**
 * Created by SKNZ on 31/12/2014.
 */
public class CarrierParkingSpot extends BaseParkingSpot {
    static {
        vehicleTypeFits.put(Carrier.class,      true);
        vehicleTypeFits.put(Car.class,          true);
        vehicleTypeFits.put(Motorbike.class,    true);
    }

    CarrierParkingSpot(Integer id) {
        this.id = id;
    }
}
