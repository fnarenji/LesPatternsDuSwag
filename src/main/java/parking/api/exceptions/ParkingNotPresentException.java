package parking.api.exceptions;

/**
 * Created by SKNZ on 28/12/2014.
 */
public class ParkingNotPresentException extends ParkingException {
    /**
     * Raised when try to remove a parking which do not exist
     *
     * @param id Id of the parking which is not present
     */
    public ParkingNotPresentException(Integer id) {
        super("You tried to remove a parking (" + id + ") that was no present in the ParkingManager. Might have been removed already.");
    }
}
