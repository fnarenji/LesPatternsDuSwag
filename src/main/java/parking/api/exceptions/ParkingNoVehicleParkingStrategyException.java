package parking.api.exceptions;

/**
 * Created by sknz on 1/18/15.
 */
public class ParkingNoVehicleParkingStrategyException extends Exception {
    public ParkingNoVehicleParkingStrategyException() {
        super("The feature you tried to use require the presence of a parking spot selector inside the parking. Use setParkingSpotSelector() for this purpose.");
    }
}
