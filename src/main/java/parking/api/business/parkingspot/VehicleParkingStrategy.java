package parking.api.business.parkingspot;

import parking.api.business.vehicle.Vehicle;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by SKNZ on 30/12/2014.
 */

public interface VehicleParkingStrategy extends Serializable {
    /**
     * Select a compatible spot for the vehicle passed as parameter into the collection of spot also passed as parameter
     *
     * @param vehicle      vehicle we want to park
     * @param parkingSpots parking spots available
     * @return
     */

    public ParkingSpot select(Vehicle vehicle, Collection<ParkingSpot> parkingSpots);

    /**
     * Check if the vehicle is in the best parking post
     *
     * @param parkingSpot spot selected
     * @param vehicle     vehicle we want to park
     * @return
     */
    public Boolean isOptimalParkingSpotForVehicle(ParkingSpot parkingSpot, Vehicle vehicle);
}
