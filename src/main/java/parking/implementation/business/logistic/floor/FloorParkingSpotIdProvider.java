package parking.implementation.business.logistic.floor;

import parking.api.business.parkingspot.ParkingSpotIdProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SKNZ on 03/01/2015.
 */
public class FloorParkingSpotIdProvider implements ParkingSpotIdProvider {
    private int floor = 1;
    private Map<Integer, Integer> countByFloor = new HashMap<>();

    /**
     * Set the floor
     *
     * @param floor floor to set
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public Integer nextId() {
        int count = countByFloor.getOrDefault(floor, 0) + 1;
        countByFloor.put(floor, count);
        return floor * 100 + count;
    }

    /**
     * Computes the floor associated with the id
     *
     * @param id the id of the parking spot
     * @return the floor
     */
    public static int ExtractFloor(int id) {
        return id / 100;
    }
}
