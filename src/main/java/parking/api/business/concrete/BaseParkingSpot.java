package parking.api.business.concrete;

import org.joda.time.DateTime;
import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.Vehicle;
import parking.api.exceptions.SpotBookedException;
import parking.api.exceptions.SpotNotBookedException;
import parking.api.exceptions.SpotNotEmptyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by SKNZ on 29/12/2014.
 */
public abstract class BaseParkingSpot implements ParkingSpot {
    protected static Map<Class, Boolean> vehicleTypeFits = new HashMap<>();
    protected int id;
    private Vehicle vehicle = null;
    private Collection<Booking> bookings = new HashSet<>();

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
    public void park(Vehicle vehicle) throws SpotNotEmptyException, SpotBookedException {
        if (this.vehicle != null)
            throw new SpotNotEmptyException(this);

        if (isBooked() && !getCurrentBooking().isOwnedBy(vehicle.getOwner()))
            throw new SpotBookedException(this);

        this.vehicle = vehicle;
    }

    @Override
    public Vehicle unpark() {
        Vehicle temp = vehicle;
        vehicle = null;
        return temp;
    }

    public Booking getCurrentBooking() {
        return bookings.stream().filter(booking -> booking.getInterval().contains(DateTime.now())).findFirst().orElse(null);
    }
    @Override
    public Boolean isBooked() {
        return bookings.stream().anyMatch(booking -> booking.getInterval().contains(DateTime.now()));
    }

    @Override
    public void book(Object client, DateTime until) throws SpotBookedException, SpotNotEmptyException {
        if (isBooked())
            throw new SpotBookedException(this);

        if (isVehicleParked())
            throw new SpotNotEmptyException(this);

        bookings.add(new Booking(client, until));
    }

    @Override
    public Booking unbook() throws SpotNotBookedException, SpotNotEmptyException {
        if (isVehicleParked())
            throw new SpotNotEmptyException(this);

        if (!isBooked())
            throw new SpotNotBookedException();

        Booking booking = bookings.stream()
                .filter(currentBooking -> currentBooking.getInterval().contains(DateTime.now()))
                .findFirst()
                .get();

        bookings.remove(booking);
        return booking;
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
        return !(bookings != null ? !bookings.equals(that.bookings) : that.bookings != null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (bookings != null ? bookings.hashCode() : 0);
        return result;
    }
}
