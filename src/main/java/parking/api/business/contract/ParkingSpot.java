package parking.api.business.contract;

import org.joda.time.Interval;
import parking.api.business.concrete.Booking;
import parking.api.business.concrete.Vehicle;
import parking.api.exceptions.BookingAlreadyConsumedException;
import parking.api.exceptions.BookingOverlapException;

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
