package parking.api.exceptions;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class UnknowVehiculeException extends ParkingException {
    public UnknowVehiculeException() {
        super("The requested vehicle does'nt exist.");
    }
}
