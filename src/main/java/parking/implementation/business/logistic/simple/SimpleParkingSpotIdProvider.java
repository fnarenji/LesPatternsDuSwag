package parking.implementation.business.logistic.simple;

import parking.api.business.parkingspot.ParkingSpotIdProvider;

/**
 * Created by SKNZ on 03/01/2015.
 */
public class SimpleParkingSpotIdProvider implements ParkingSpotIdProvider {
    private int i = 0;

    @Override
    public Integer nextId() {
        return ++i;
    }
}
