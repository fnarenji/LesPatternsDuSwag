package parking.implementation;

import parking.api.business.concrete.Vehicle;
import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.ParkingSpotSelector;
import parking.implementation.parkingspots.CarParkingSpot;
import parking.implementation.parkingspots.CarrierParkingSpot;
import parking.implementation.vehicles.Car;
import parking.implementation.vehicles.Motorbike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SKNZ on 01/01/2015.
 */
public class SimpleParkingSpotSelector implements ParkingSpotSelector {
    private static Map<Class, ArrayList<Class>> queue = new HashMap<>();

    static {
        ArrayList<Class> a = new ArrayList<>();
        a.add(CarParkingSpot.class);
        a.add(CarrierParkingSpot.class);

        queue.put(Car.class, a);
        queue.put(Motorbike.class, a);
    }

    @Override
    public ParkingSpot select(Vehicle vehicle, Collection<ParkingSpot> parkingSpots) {
        for (int i = 0; i < queue.get(vehicle.getClass()).size(); ++i)
            parkingSpots.stream().filter(o => );
            return parkingSpots.iterator().next(); // First matching element
    }
}
