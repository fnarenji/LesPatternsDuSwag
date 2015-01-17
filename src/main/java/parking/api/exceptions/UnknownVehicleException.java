package parking.api.exceptions;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class UnknownVehicleException extends ParkingException {
    public UnknownVehicleException() {
        super("The requested vehicle does not exist.");
    }
}
