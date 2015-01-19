package parking.implementation.business.logistic.simple;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import parking.api.business.helper.ParkingSpotTypeByVehicleTypePriorityMap;
import parking.api.business.parking.Parking;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.parkingspot.VehicleParkingStrategy;
import parking.api.business.vehicle.Vehicle;
import parking.api.exceptions.SpotBookedException;
import parking.api.exceptions.SpotNotEmptyException;
import parking.api.exceptions.VehicleNotFitException;
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
public class SimpleVehicleParkingStrategy implements VehicleParkingStrategy {
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

            selectedSpot = parkingSpots.stream()
                    .filter(parkingSpot -> parkingSpot.getClass().equals(currentParkingSpotType))
                    .findFirst()
                    .orElse(null);
        }

        return selectedSpot;
    }

    @Override
    public Boolean isOptimalParkingSpotForVehicle(ParkingSpot parkingSpot, Vehicle vehicle) {
        if (parkingSpot.isBooked())
            return true;

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
        List<ParkingSpot> selectedSpots = new ArrayList<>();

        selectedSpots.addAll(
                parkingSpots.stream()
                        .filter(currentSpot -> currentSpot.isBooked() && currentSpot.getCurrentBooking().getOwner().equals(vehicle.getOwner()))
                        .collect(Collectors.toList()));

        if (selectedSpots.isEmpty())
            selectedSpots.addAll(
                    parkingSpots.stream()
                            .filter(currentSpot -> !currentSpot.isBooked())
                            .collect(Collectors.toList()));

        Collections.sort(selectedSpots, (a, b) -> a.isBooked().compareTo(b.isBooked()));

        return selectedSpots;
    }

    public void observe(Parking parking) {
        parking.forEach(parkingSpot -> parkingSpot.registerObserver(this::observeParkingSpot));
    }

    private Set<ParkingSpot> suboptimallyParkedParkingSpots = new HashSet<>();

    private void observeParkingSpot(ParkingSpot parkingSpot) {
        if (parkingSpot.isVehicleParked()) {
            if (!isOptimalParkingSpotForVehicle(parkingSpot, parkingSpot.getVehicle())) {
                suboptimallyParkedParkingSpots.add(parkingSpot);
            }
        } else {
            if (suboptimallyParkedParkingSpots.contains(parkingSpot)) {
                suboptimallyParkedParkingSpots.remove(parkingSpot);
            } else if (!parkingSpot.isBooked()) {
                for (ParkingSpot badSpot : suboptimallyParkedParkingSpots) {
                    ArrayList<ParkingSpot> parkingSpots = new ArrayList<>();
                    parkingSpots.add(parkingSpot);
                    parkingSpots.add(badSpot);

                    ParkingSpot selectedParkingSpot = select(badSpot.getVehicle(), parkingSpots);
                    if (selectedParkingSpot != null && selectedParkingSpot != badSpot) {

                        String message = String.format("Réorganisation automatique: déplacer de %d (suboptimal) vers %d ?",
                                badSpot.getId(), selectedParkingSpot.getId());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK, ButtonType.NO);

                        alert.showAndWait();
                        if (alert.getResult() != ButtonType.OK)
                            continue;

                        Vehicle vehicle = badSpot.unpark();

                        try {
                            parkingSpot.park(vehicle);
                            break;
                        } catch (SpotNotEmptyException | SpotBookedException | VehicleNotFitException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
