package parking.business;

import org.joda.time.Interval;
import parking.exceptions.BookingAlreadyConsumedException;
import parking.exceptions.BookingOverlapException;

/**
 * Created by SKNZ on 28/12/2014.
 */
public interface ParkingSpot {
    public Integer getId();

    public Boolean isVehicleParked();

    public Vehicle getVehicle();

    public void park(Vehicle vehicle);

    public Vehicle unpark();

    public Boolean isBooked(Interval interval);

    public void book(Booking booking) throws BookingOverlapException;

    public void unbook(Booking booking) throws BookingAlreadyConsumedException;

    public Boolean fits(Vehicle vehicle);
}
