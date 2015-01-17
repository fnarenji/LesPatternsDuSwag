package parking.api.exceptions;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class UnknownVehicleException extends ParkingException {
    /**
     * Raised when the vehicle to park does not exist
     */
    public UnknownVehicleException() {
        super("The requested vehicle does not exist.");
    }
}
