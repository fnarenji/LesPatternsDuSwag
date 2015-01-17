package parking.api.exceptions;

/**
 * Created by SKNZ on 28/12/2014.
 */
public class ParkingExistsException extends ParkingException {
    /**
     * Raised when try create a parking with an id already existing
     * 
     * @param id Id of the parking
     */
    public ParkingExistsException(Integer id) {
        super("Duplicate parking id (" + id + ")");
    }
}
