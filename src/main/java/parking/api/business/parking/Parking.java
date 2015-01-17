package parking.api.business.parking;

import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.parkingspot.ParkingSpotFactory;
import parking.api.business.parkingspot.ParkingSpotSelector;
import parking.api.business.vehicle.Vehicle;
import parking.api.exceptions.*;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by SKNZ on 28/12/2014.
 */
public class Parking implements Serializable, Iterable<ParkingSpot> {
    private final Integer id;
    private String name;
    private Map<Integer, ParkingSpot> parkingSpotsById = new HashMap<>();
    private ParkingSpotSelector parkingSpotSelector;

    /**
     * Create a parking
     *
     * @param id   id of the parking
     * @param name name of the parking
     */
    Parking(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get the id of the parking
     *
     * @return the id of the parking
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get the name of the parking
     *
     * @return the name of the parking
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the parking
     *
     * @param name the name of the parking
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Compute the number of spots of the parking
     *
     * @return The number of spots of the parking
     */
    public Integer countParkingSpots() {
        return parkingSpotsById.size();
    }

    /**
     * Compute the amount of spots where the predicate respond true
     *
     * @param predicate condition to be tested
     * @return The amount of spots where the predicate respond true
     */
    public Integer countParkingSpots(Predicate<ParkingSpot> predicate) {
        return Long.valueOf(parkingSpotsById.values().stream().filter(predicate).count()).intValue();
    }

    /**
     * Add the spot in the parking from the parkingSpotFactory
     *
     * @param parkingSpotFactory Give the spot to add
     * @return The parking spot created
     */
    public ParkingSpot newParkingSpot(ParkingSpotFactory parkingSpotFactory) {
        ParkingSpot parkingSpot = parkingSpotFactory.createParkingSpot();

        parkingSpotsById.put(parkingSpot.getId(), parkingSpot);

        return parkingSpot;
    }

    /**
     * Add the spot in the parking from the parkingSpotFactory
     *
     * @param parkingSpotFactory Give the spots to add
     * @param amount             Amount of places to add
     * @return The spots created
     */
    public Collection<ParkingSpot> newParkingSpot(ParkingSpotFactory parkingSpotFactory, Integer amount) {
        if (amount < 1)
            throw new IllegalArgumentException("Amount must be > 1, is " + amount);

        Collection<ParkingSpot> parkingSpots = new ArrayList<>();

        for (Integer i = 0; i < amount; ++i)
            parkingSpots.add(newParkingSpot(parkingSpotFactory));

        return parkingSpots;
    }

    /**
     * @param parkingSpotId
     * @return
     */
    public ParkingSpot getSpotBySpotId(Integer parkingSpotId) {
        return parkingSpotsById.getOrDefault(parkingSpotId, null);
    }

    public ParkingSpot getSpotByVehiclePlate(String plate) {
        return parkingSpotsById.values().stream().filter(parkingSpot ->
                        parkingSpot.isVehicleParked() && parkingSpot.getVehicle().getPlate().equals(plate)
        ).findFirst().orElse(null);
    }

    public void setParkingSpotSelector(ParkingSpotSelector parkingSpotSelector) {
        this.parkingSpotSelector = parkingSpotSelector;
    }

    // Undefined behaviour if vehicle already parked
    public ParkingSpot findAvailableParkingSpotForVehicle(Vehicle vehicle) throws NoSpotAvailableException {
        List<ParkingSpot> availableParkingSpots = parkingSpotsById.values().stream()
                .filter(parkingSpot -> parkingSpot.fits(vehicle) && !parkingSpot.isVehicleParked())
                .collect(Collectors.toList());

        if (availableParkingSpots.isEmpty())
            throw new NoSpotAvailableException();

        return parkingSpotSelector.select(vehicle, availableParkingSpots);
    }

    public void reorganizeParking() throws ReorganizationException {
        try {
            parkingSpotsById.values().stream()
                    .filter(parkingSpot -> parkingSpot.isVehicleParked() && !parkingSpotSelector.isOptimalParkingSpotForVehicle(parkingSpot, parkingSpot.getVehicle()))
                    .forEach(parkingSpot -> {
                        Vehicle vehicle = parkingSpot.unpark();
                        try {
                            ParkingSpot optimalParkingSpot = findAvailableParkingSpotForVehicle(vehicle);
                            optimalParkingSpot.park(vehicle);
                        } catch (NoSpotAvailableException e) {
                            e.printStackTrace();
                        } catch (SpotNotEmptyException | VehicleNotFitException | SpotBookedException e) {
                            throw new RuntimeException(new ReorganizationException());
                        }
                    });
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ReorganizationException)
                throw (ReorganizationException) e.getCause();

            throw e;
        }
    }

    public Stream<ParkingSpot> stream() {
        return parkingSpotsById.values().stream();
    }

    public Collection<ParkingSpot> getSpots() {
        return parkingSpotsById.values();
    }

    @Override
    public Iterator<ParkingSpot> iterator() {
        return parkingSpotsById.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super ParkingSpot> action) {
        parkingSpotsById.values().forEach(action);
    }
}
