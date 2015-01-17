package parking.implementation.logic;

import parking.api.business.concrete.BaseParkingSpot;

/**
 * Created by SKNZ on 31/12/2014.
 */
class CarrierParkingSpot extends BaseParkingSpot {
    CarrierParkingSpot(Integer id) {
        this.id = id;
        vehicleTypeFits.put(Carrier.class, true);
        vehicleTypeFits.put(Car.class, true);
        vehicleTypeFits.put(Motorbike.class, true);
    }
}
