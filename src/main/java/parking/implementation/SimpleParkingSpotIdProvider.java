package parking.implementation;

import parking.api.business.contract.ParkingSpotIdProvider;

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
                                      git comm