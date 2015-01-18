package parking.api.exceptions;

/**
 * Created by sknz on 15/01/15.
 */
public class ReorganizationException extends Exception {
    /**
     * Raised when reorganization failed to reorganize the parking, might be due to a spot already occupied or booked
     */
    public ReorganizationException(Throwable cause) {
        super("The parking spot selector failed to provide a valid spot during parking reorganization. Provided spot was already occupied or booked. Automatic replacing of parked vehicle by another one is not supported yet.", cause);
    }
}
