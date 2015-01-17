package parking.api.exceptions;

import parking.api.business.parkingspot.ParkingSpot;

/**
 * Created by SKNZ on 02/01/2015.
 */
public class SpotBookedException extends ParkingException {
    /**
     *Raised when spot already booked
     *
     * @param parkingSpot parking spot which is already booked
     */
    public SpotBookedException(ParkingSpot parkingSpot) {
        super("This spot (" + parkingSpot.getId() + ") is already booked.");
    }
}
