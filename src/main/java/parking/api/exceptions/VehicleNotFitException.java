package parking.api.exceptions;

import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;

/**
 * Created by sknz on 16/01/15.
 */
public class VehicleNotFitException extends Exception {
    /**
     * Raised when try to park a vehicle in a spot which do not correspond to vehicle type
     *
     * @param spot    spot to park the car
     * @param vehicle vehicle to park
     */
    public VehicleNotFitException(ParkingSpot spot, Vehicle vehicle) {
        super("Tried to fit " + vehicle.getClass().getName() + " in " + spot.getClass().getName() + ".");
    }
}
