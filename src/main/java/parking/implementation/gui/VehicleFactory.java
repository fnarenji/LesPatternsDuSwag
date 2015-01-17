package parking.implementation.gui;

import parking.api.business.vehicle.Vehicle;
import parking.api.exceptions.UnknownVehicleException;

/**
 * Created by loick on 15/01/15.
 */
public interface VehicleFactory {
    public Vehicle createVehicle(String vehicle) throws UnknownVehicleException;
}
