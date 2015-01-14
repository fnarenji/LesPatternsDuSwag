package parking.api.business.contract;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import parking.api.business.concrete.Booking;
import parking.api.exceptions.BookingAlreadyConsumedException;
import parking.api.exceptions.BookingOverlapException;
import parking.api.exceptions.SpotNotEmptyOrBookedException;

/**
 * Created by SKNZ on 28/12/2014.
 */
public interface ParkingSpot {
    /**
     * Get the id of the parking spot
     * @return The id of the parking spot
     */
    public Integer getId();

    /**
     * Whether a vehicle is parked
     * @return true if vehicle parked, false if no vehicle parked
     */
    public Boolean isVehicleParked();

    /**
     * Gets the parked vehicle
     * @return Vehicle object if vehicle parked, null otherwise
     */
    public Vehicle getVehicle();

    /**
     * Parks the vehicle.
     * @param vehicle The vehicle to be parked
     * @throws SpotNotEmptyOrBookedException if a vehicle is already parked in the spot or if the spot is booked.
     */
    public void park(Vehicle vehicle) throws SpotNotEmptyOrBookedException;

    /**
     * Removes the vehicle that is parked.
     * @return the parked vehicle that was removed, or null if no vehicle parked.
     */
    public Vehicle unpark();

    /**
     * Checks if the parking spot is booked.
     * @return true if booked; false if not booked
     */
    public Boolean isBooked();

    /**
     * Clients books spot until date.
     * @param until the date until which the spot will be booked. null for infinite.
     * @param client the client who is booking the spots
     * @throws BookingOverlapException
     */
    public void book(DateTime until, Object client) throws SpotNotEmptyOrBookedException;

    public void unbook(Booking booking) throws BookingAlreadyConsumedException;

    public Boolean fits(Vehicle vehicle);
}
