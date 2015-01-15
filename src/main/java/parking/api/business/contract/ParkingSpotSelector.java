package parking.api.business.contract;

import java.util.Collection;

/**
 * Created by SKNZ on 30/12/2014.
 */
public interface ParkingSpotSelector {
    public ParkingSpot select(Vehicle vehicle, Collection<ParkingSpot> parkingSpots);
    public Boolean isOptimalParkingSpotForVehicle(ParkingSpot parkingSpot, Vehicle vehicle);
    public Collection<ParkingSpot> checkBooked(Vehicle vehicle, Collection<ParkingSpot> parkingSpots);
}
