package parking.api.exceptions;

/**
 * Created by sknz on 15/01/15.
 */
public class ReorganizationException extends Exception {
    public ReorganizationException() {
        super("The parking spot selector failed to provide a valid spot during parking reorganization. Provided spot was already occupied or booked. Automatic replacing of parked vehicle by another one is not supported yet.");
    }
}
