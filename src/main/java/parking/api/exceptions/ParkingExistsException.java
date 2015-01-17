package parking.api.exceptions;

/**
 * Created by SKNZ on 28/12/2014.
 */
public class ParkingExistsException extends ParkingException {
    public ParkingExistsException(Integer id) {
        super("Duplicate parking id (" + id + ")");
    }
}
