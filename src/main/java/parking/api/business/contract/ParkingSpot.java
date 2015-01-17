package parking.api.business.contract;

import org.joda.time.DateTime;
import parking.api.business.concrete.Booking;
import parking.api.business.contract.observer.Observable;
import parking.api.exceptions.SpotBookedException;
import parking.api.exceptions.SpotNotBookedException;
import parking.api.exceptions.SpotNotEmptyException;
import parking.api.exceptions.VehicleNotFitException;

import java.io.Serializable;

/**
 * Created by SKNZ on 28/12/2014.
 */
public interface ParkingSpot extends Serializable, Observable {
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
     * @throws parking.api.exceptions.SpotNotEmptyException if a vehicle is already parked in the spot or if the spot is booked.
     * @throws parking.api.exceptions.SpotBookedException if the spot is booked by someone else.
     */
    public void park(Vehicle vehicle) throws SpotNotEmptyException, SpotBookedException, VehicleNotFitException;

    /**
     * Removes the vehicle that is parked.
     * @return the parked vehicle that was removed, or null if no vehicle parked.
     */
    public Vehicle unpark();

    /**
     * Get the booking that's currently in effect for this parking spot.
     *
     * @return the booking
     */
    Booking getCurrentBooking();

    /**
     * Checks if the parking spot is booked.
     * @return true if booked; false if not booked
     */
    public Boolean isBooked();

    /**
     * Books an empty parking from now until a certain time.
     * @param owner the owner who is booking
     * @param until the date until the booking ends, null for infinite
     * @throws parking.api.exceptions.SpotBookedException if the spot is already booked
     * @throws parking.api.exceptions.SpotNotEmptyException if the spot is not empty
     */
    public void book(Object owner, DateTime until) throws SpotNotEmptyException, SpotBookedException;

    /**
     * Removes the current booking.
     *
     * @return the booking that was cancelled
     * @throws parking.api.exceptions.SpotNotBookedException
     * @throws parking.api.exceptions.SpotNotEmptyException  if the vehicle has not been removed from the spot
     */
    public Booking unbook() throws SpotNotBookedException, SpotNotEmptyException;

    /**
     * Whether a vehicle fits inside this parking spot
     *
     * @param vehicle the vehicle you want to fit
     * @return true if fits, false if not fits
     */
    public Boolean fits(Vehicle vehicle);
}
