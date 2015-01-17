package parking.api.exceptions;

/**
 * Created by SKNZ on 28/12/2014.
 */
public class ParkingNotPresentException extends ParkingException {
    public ParkingNotPresentException(Integer id) {
        super("You tried to remove a parking (" + id + ") that was no present in the ParkingManager. Might have been removed already.");
    }
}
