package parking.exceptions;

import parking.business.Parking;

/**
 * Created by SKNZ on 28/12/2014.
 */
public class ParkingBookedSpotsExceptions extends ParkingException {
    public ParkingBookedSpotsExceptions(Parking parking) {
        super("You tried to remove a parking (" + parking.getId() + ", \"" + parking.getName() + "\") that still had booked spots.");
    }
}