package parking.api.exceptions;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class NoSpotAvailableException extends ParkingException {
    public NoSpotAvailableException() {
        super("No spot was available.");
    }
}
