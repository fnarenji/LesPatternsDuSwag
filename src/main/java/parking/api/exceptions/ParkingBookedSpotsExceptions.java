package parking.api.exceptions;

import parking.api.business.parking.Parking;

/**
 * Created by SKNZ on 28/12/2014.
 */
public class ParkingBookedSpotsExceptions extends ParkingException {
    /**
     * Raised when try to remove a parking which have booked spots
     *
     * @param parking parking to remove
     */
    public ParkingBookedSpotsExceptions(Parking parking) {
        super("You tried to remove a parking (" + parking.getId() + ", \"" + parking.getName() + "\") that still had booked spots.");
    }
}
