package parking.api.exceptions;

import parking.api.business.concrete.Booking;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class BookingOverlapException extends ParkingException {
    public BookingOverlapException(Booking booking) {
        super("Booking (" + booking.getInterval() + ") overlaps with another booking.");
    }
}
