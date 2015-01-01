package parking.api.exceptions;

import parking.api.business.concrete.Booking;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class BookingAlreadyConsumedException extends ParkingException {
    public BookingAlreadyConsumedException(Booking booking) {
        super("Can not unbook a booking that has already been consumed/is being consumed: " + booking.getInterval());
    }
}
