package parking.api.exceptions;

/**
 * Created by SKNZ on 29/12/2014.
 */
public class NoSpotAvailableException extends ParkingException {
    /**
     * Raised when there are no spot available
     */
    public NoSpotAvailableException() {
        super("No spot was available.");
    }
}
