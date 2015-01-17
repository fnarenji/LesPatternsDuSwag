package parking.implementation.logic;

import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.ParkingSpotSelector;
import parking.api.business.contract.Vehicle;
import parking.api.business.helper.ParkingSpotByVehicleTypePriorityMap;

import java.util.*;
import java.util.stream.Collectors;

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

        parkingSpots = cleanAndSortSpotList(vehicle, parkingSpots);

        while (selectedSpot == null && parkingSpotType.hasNext()) {
            Class currentParkingSpotType = parkingSpotType.next();

            selectedSpot = parkingSpots.stream()                    .filter(parkingSpot -> parkingSpot.getClass().equals(currentParkingSpotType))
                    .findFirst()
                    .orElse(null);
        }

        return selectedSpot;
    }

    @Override
    public Boolean isOptimalParkingSpotForVehicle(ParkingSpot parkingSpot, Vehicle vehicle) {
        return priorityMap.whereTo(vehicle.getClass()).next().equals(parkingSpot.getClass());
    }

    /**
     * Check every spot to find the bests spots (the booked spots first)
     * @param vehicle
     * @param parkingSpots
     * @return Collection of the parking spots available for the user and with the booked spots first
     */
    private List<ParkingSpot> cleanAndSortSpotList(Vehicle vehicle, Collection<ParkingSpot> parkingSpots) {
        List<ParkingSpot> selectedSpots = new ArrayList<ParkingSpot>();

        selectedSpots.addAll(
                parkingSpots.stream()
                        .filter(currentSpot -> currentSpot.isBooked() && currentSpot.getCurrentBooking().getOwner().equals(vehicle.getOwner()))
                        .collect(Collectors.toList()));

        Collections.sort(selectedSpots, (a, b) -> a.isBooked().compareTo(b.isBooked()));

        return selectedSpots;
    }

}
