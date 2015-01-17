package parking.api.exceptions;

import parking.api.business.parkingspot.ParkingSpot;

/**
 * Created by SKNZ on 02/01/2015.
 */
public class SpotNotEmptyException extends ParkingException {
    public SpotNotEmptyException(ParkingSpot parkingSpot) {
        super("This spot (" + parkingSpot.getId() + ") is not empty.");
    }
}
