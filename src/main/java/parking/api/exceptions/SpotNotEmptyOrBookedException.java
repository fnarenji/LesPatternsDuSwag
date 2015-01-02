package parking.api.exceptions;

import parking.api.business.contract.ParkingSpot;

/**
 * Created by SKNZ on 02/01/2015.
 */
public class SpotNotEmptyOrBookedException extends ParkingException {
    public SpotNotEmptyOrBookedException(ParkingSpot parkingSpot) {
        super("This spot (" + parkingSpot.getId() +  ") is already booked or is not empty.");
    }
}
