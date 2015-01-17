package parking.implementation.business.logistic.floor;

import parking.api.business.parkingspot.ParkingSpotIdProvider;

/**
 * Created by SKNZ on 03/01/2015.
 */
public class FloorParkingSpotIdProvider implements ParkingSpotIdProvider {
    private int floor = 1;
    private int i = 0;

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public Integer nextId() {
        return floor * 100 + ++i;
    }
}
