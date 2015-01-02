package parking.implementation;

import parking.api.business.concrete.Vehicle;
import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.ParkingSpotSelector;
import parking.api.business.helper.ParkingSpotByVehicleTypePriorityMap;
import parking.implementation.parkingspots.CarParkingSpot;
import parking.implementation.parkingspots.CarrierParkingSpot;
import parking.implementation.vehicles.Car;
import parking.implementation.vehicles.Carrier;
import parking.implementation.vehicles.Motorbike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by SKNZ on 01/01/2015.
 */
public class SimpleParkingSpotSelector implements ParkingSpotSelector {
    private static ParkingSpotByVehicleTypePriorityMap priorityMap = new ParkingSpotByVehicleTypePriorityMap();

    static {
        priorityMap.buildFor(Motorbike.class).park(CarParkingSpot.class).park(CarrierParkingSpot.class);
        priorityMap.buildFor(Car.class).park(CarParkingSpot.class).park(CarrierParkingSpot.class);
        priorityMap.buildFor(Carrier.class).park(CarrierParkingSpot.class);
    }

    @Override
    public ParkingSpot select(Vehicle vehicle, Collection<ParkingSpot> parkingSpots) {
        ParkingSpot selectedSpot = null;
        Iterator parkingSpotType = priorityMap.whereTo(vehicle.getClass());

        while (parkingSpotType.hasNext() && selectedSpot == null) {
            selectedSpot = parkingSpots.stream().filter(parkingSpot -> parkingSpots.getClass().equals(parkingSpotType.next())).findFirst().orElse(null);
        }

        return selectedSpot;
    }
}
