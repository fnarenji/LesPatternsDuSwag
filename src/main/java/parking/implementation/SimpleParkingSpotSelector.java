package parking.implementation;

import parking.api.business.concrete.Parking;
import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.ParkingSpotSelector;
import parking.api.business.contract.Vehicle;
import parking.api.business.helper.ParkingSpotByVehicleTypePriorityMap;

import java.util.*;

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
     * Check every spot to find the bests spots (the booked spots first=
     * @param vehicle
     * @param parkingSpots
     * @return Collection of the parking spots available for the user and with the booked spots first
     */
    @Override
    public Collection<ParkingSpot> checkBooked(Vehicle vehicle, Collection<ParkingSpot> parkingSpots){
        List<ParkingSpot> selectedSpots = new ArrayList<ParkingSpot>();

        for(ParkingSpot currentSpot : parkingSpots){
            if(currentSpot.isBooked() && currentSpot.getBookOwner() != vehicle.getOwner()) continue;

            selectedSpots.add(currentSpot);
        }

        Collections.sort(selectedSpots, new ParkingSpotBookedComparator());

        Collection<ParkingSpot> ps = null;

        for(ParkingSpot parkSpot : selectedSpots){
            ps.add(parkSpot);
        }
        return ps;
    }

}
