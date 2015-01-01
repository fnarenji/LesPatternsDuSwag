package parking.api.business.concrete;

import org.joda.time.Interval;
import parking.api.business.concrete.Booking;
import parking.api.business.concrete.Vehicle;
import parking.api.business.contract.ParkingSpot;
import parking.api.exceptions.BookingOverlapException;
import parking.api.exceptions.BookingAlreadyConsumedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by SKNZ on 29/12/2014.
 */
public abstract class BaseParkingSpot implements ParkingSpot {
    private int id;
    private Vehicle vehicle;
    private Collection<Booking> bookings = new HashSet<>();
    protected static Map<Class, Boolean> vehicleTypeFits = new HashMap<>();

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Boolean isVehicleParked() {
        return vehicle != null;
    }

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public void park(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public Vehicle unpark() {
        Vehicle temp = vehicle;
        vehicle = null;
        return temp;
    }

    @Override
    public Boolean isBooked(Interval interval) {
        return bookings.stream().anyMatch(booking -> booking.overlaps(interval));
    }

    @Override
    public void book(Booking booking) throws BookingOverlapException {
        if (isBooked(booking.getInterval()))
            throw new BookingOverlapException(booking);

        bookings.add(booking);
    }

    @Override
    public void unbook(Booking booking) throws BookingAlreadyConsumedException {
        if (booking.consumed)
            throw new BookingAlreadyConsumedException(booking);

        bookings.remove(booking);
    }

    @Override
    public Boolean fits(Vehicle vehicle) {
        return vehicleTypeFits.getOrDefault(vehicle.getClass(), false);
    }
}
