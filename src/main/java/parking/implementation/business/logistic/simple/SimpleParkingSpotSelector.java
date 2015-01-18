package parking.implementation.business.logistic.simple;

import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.parkingspot.ParkingSpotSelector;
import parking.api.business.vehicle.Vehicle;
import parking.api.business.helper.ParkingSpotTypeByVehicleTypePriorityMap;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.business.parkingspot.CarrierParkingSpot;
import parking.implementation.business.vehicle.Car;
import parking.implementation.business.vehicle.Carrier;
import parking.implementation.business.vehicle.Motorbike;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by SKNZ on 01/01/2015.
 */
public class SimpleParkingSpotSelector implements ParkingSpotSelector {
    private static final ParkingSpotTypeByVehicleTypePriorityMap priorityMap = new ParkingSpotTypeByVehicleTypePriorityMap();

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

            selectedSpot = parkingSpots.stream().filter(parkingSpot -> parkingSpot.getClass().equals(currentParkingSpotType))
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
     *
     * @param vehicle
     * @param parkingSpots
     * @return Collection of the parking spots available for the user and with the booked spots first
     */
    private Collection<ParkingSpot> cleanAndSortSpotList(Vehicle vehicle, Collection<ParkingSpot> parkingSpots) {
        List<ParkingSpot> selectedSpots = new ArrayList<ParkingSpot>();

        /*selectedSpots.addAll(
                parkingSpots.stream()
                        .filter(currentSpot -> currentSpot.isBooked() && currentSpot.getCurrentBooking().getOwner().equals(vehicle.getOwner()))
                        .collect(Collectors.toList()));*/

        for(ParkingSpot currentSpot : parkingSpots){
            if(currentSpot.isVehicleParked() || currentSpot.isBooked() && currentSpot.getCurrentBooking().getOwner().equals(vehicle.getOwner())){
                continue;
            } else {
                selectedSpots.add(currentSpot);
            }
        }

        Collections.sort(selectedSpots, (a, b) -> a.isBooked().compareTo(b.isBooked()));

        return selectedSpots;
    }

}
