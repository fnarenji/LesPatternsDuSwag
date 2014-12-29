package parking.business;

import org.joda.time.Interval;
import parking.exceptions.NoSpotAvailableException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by SKNZ on 28/12/2014.
 */
public class Parking {
    private final Integer id;
    private String name;
    private Map<Integer, ParkingSpot> parkingSpotsById = new HashMap<>();
    private ParkingSpotSelector parkingSpotSelector;

    Parking(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long countParkingSpots() {
        return Long.valueOf(parkingSpotsById.size());
    }

    public Long countParkingSpots(Predicate<ParkingSpot> predicate) {
        return parkingSpotsById.values().stream().filter(predicate).count();
    }

    public ParkingSpot newParkingSpot(ParkingSpotFactory parkingSpotFactory) {
        ParkingSpot parkingSpot = parkingSpotFactory.createParkingSpot();

        parkingSpotsById.put(parkingSpot.getId(), parkingSpot);

        return parkingSpot;
    }

    public Collection<ParkingSpot> newParkingSpot(ParkingSpotFactory parkingSpotFactory, Integer amount) {
        if (amount < 1)
            throw new IllegalArgumentException("Amount must be > 1, is " + amount);

        Collection<ParkingSpot> parkingSpots = new ArrayList<>();

        for (Integer i = 0; i < amount; ++i)
            parkingSpots.add(newParkingSpot(parkingSpotFactory));

        return parkingSpots;
    }

    public ParkingSpot getSpotBySpotId(Integer parkingSpotId) {
        return parkingSpotsById.getOrDefault(parkingSpotId, null);
    }

    public ParkingSpot getSpotByVehiclePlate(String plate) {
        return parkingSpotsById.values().stream().filter(parkingSpot ->
                        parkingSpot.isVehicleParked() && parkingSpot.getVehicle().getPlate() == plate
        ).findFirst().orElse(null);
    }

    public void setParkingSpotSelector(ParkingSpotSelector parkingSpotSelector) {
        this.parkingSpotSelector = parkingSpotSelector;
    }

    // Undefined behaviour if vehicle already parked
    public ParkingSpot findAvailableParkingSpotForVehicle(Vehicle vehicle, Interval interval) throws NoSpotAvailableException {
        return parkingSpotSelector.select(parkingSpotsById.values().stream().filter(parkingSpot -> parkingSpot.fits(vehicle)).collect(Collectors.toList()));
    }
}