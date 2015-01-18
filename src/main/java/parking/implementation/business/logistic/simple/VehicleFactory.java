package parking.implementation.business.logistic.simple;

import parking.api.business.vehicle.Vehicle;
import parking.api.exceptions.UnknownVehicleException;

/**
 * Created by sknz on 1/18/15.
 */
public interface VehicleFactory {
    public Vehicle createVehicle(String name) throws UnknownVehicleException;
}
