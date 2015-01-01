package parking.api.business.contract;

import parking.api.business.concrete.Vehicle;

import java.util.Collection;

/**
 * Created by SKNZ on 30/12/2014.
 */
public interface ParkingSpotSelector {
    public ParkingSpot select(Vehicle vehicle, Collection<ParkingSpot> parkingSpots);
}
