package parking.api.exceptions;

import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.Vehicle;

/**
 * Created by sknz on 16/01/15.
 */
public class VehicleNotFitException extends Exception {
    public VehicleNotFitException(ParkingSpot spot, Vehicle vehicle) {
        super("Tried to fit " + vehicle.getClass().getName() + " in " + spot.getClass().getName() + ".");
    }
}
