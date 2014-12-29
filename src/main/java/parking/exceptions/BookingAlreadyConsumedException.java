package parking.exceptions;

import parking.business.Booking;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class BookingAlreadyConsumedException extends ParkingException {
    public BookingAlreadyConsumedException(Booking booking) {
        super("Can not unbook a booking that has already been consumed/is being consumed: " + booking.getInterval());
    }
}
