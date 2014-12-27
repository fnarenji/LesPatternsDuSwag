package parking.business.logic;

import parking.business.vehicles.Vehicle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sknz on 24/12/14.
 */
public class Parking {
    private int id;
    private String name;
    private Map<Integer, ParkingSpot> spotByPosition;

    public Parking(int id, String name, int size) {
        this.id = id;
        this.name = name;
        this.spotByPosition = new HashMap<Integer, ParkingSpot>(size);
    }

    public <T extends Vehicle> void addSpot() {
        
    }

    public int spotCount() {
        return spotByPosition.size();
    }
}
