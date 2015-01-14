package parking.implementation;

import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.ParkingSpotSelector;
import parking.api.business.contract.Vehicle;
import parking.api.business.helper.ParkingSpotByVehicleTypePriorityMap;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by SKNZ on 01/01/2015.
 */
public class SimpleParkingSpotSelector implements ParkingSpotSelector {
    private static final ParkingSpotByVehicleTypePriorityMap priorityMap = new ParkingSpotByVehicleTypePriorityMap();

    static {
        priorityMap.buildFor(Motorbike.class).park(CarParkingSpot.class).park(CarrierParkingSpot.class);
        priorityMap.buildFor(Car.class).park(CarParkingSpot.class).park(CarrierParkingSpot.class);
        priorityMap.buildFor(Carrier.class).park(CarrierParkingSpot.class);
    }

    @Override
    public ParkingSpot select(Vehicle vehicle, Collection<ParkingSpot> parkingSpots) {
        ParkingSpot selectedSpot = null;
        Iterator<Class> parkingSpotType = priorityMap.whereTo(vehicle.getClass());

        while (selectedSpot == null && parkingSpotType.hasNext()) {
            Class currentParkingSpotType = parkingSpotType.next();

            selectedSpot = parkingSpots.stream()
                    .filter(parkingSpot -> parkingSpots.getClass().equals(currentParkingSpotType))
                    .findFirst().orElse(null);
        }

        return selectedSpot;
    }

    @Override
    public Boolean isOptimalParkingSpotForVehicle(ParkingSpot parkingSpot, Vehicle vehicle) {
        return priorityMap.whereTo(vehicle.getClass()).next().equals(parkingSpot.getClass());
    }
}
