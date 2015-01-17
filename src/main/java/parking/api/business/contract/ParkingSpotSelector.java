package parking.api.business.contract;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by SKNZ on 30/12/2014.
 */
public interface ParkingSpotSelector extends Serializable {
    public ParkingSpot select(Vehicle vehicle, Collection<ParkingSpot> parkingSpots);
    public Boolean isOptimalParkingSpotForVehicle(ParkingSpot parkingSpot, Vehicle vehicle);
}
