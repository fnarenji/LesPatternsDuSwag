package parking.api.business.concrete;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import parking.api.business.contract.Vehicle;
import parking.api.business.contract.ParkingSpot;
import parking.api.exceptions.BookingOverlapException;
import parking.api.exceptions.BookingAlreadyConsumedException;
import parking.api.exceptions.SpotNotEmptyOrBookedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by SKNZ on 29/12/2014.
 */
public abstract class BaseParkingSpot implements ParkingSpot {
    protected int id;
    private Vehicle vehicle = null;
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
    public void park(Vehicle vehicle) throws SpotNotEmptyOrBookedException {
        if (this.vehicle != null)
            throw new SpotNotEmptyOrBookedException(this);

        if (bookings.stream().anyMatch(booking -> booking.getInterval().contains(DateTime.now())))
            throw new SpotNotEmptyOrBookedException(this);

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
    public void book(DateTime until, Object client) throws SpotNotEmptyOrBookedException {
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

    @Override
    public String toString() {
        return "BaseParkingSpot{" +
                "id=" + id +
                ", vehicle=" + vehicle +
                ", bookings=" + bookings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseParkingSpot)) return false;

        BaseParkingSpot that = (BaseParkingSpot) o;

        if (id != that.id) return false;
        if (bookings != null ? !bookings.equals(that.bookings) : that.bookings != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (bookings != null ? bookings.hashCode() : 0);
        return result;
    }
}
