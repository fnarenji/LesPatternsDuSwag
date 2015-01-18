package parking.implementation.business.logistic.simple;

import parking.api.business.vehicle.Vehicle;
import parking.api.exceptions.UnknownVehicleException;
import parking.implementation.business.vehicle.Car;
import parking.implementation.business.vehicle.Carrier;
import parking.implementation.business.vehicle.Motorbike;

/**
 * Created by loick on 15/01/15.
 */
public class SimpleVehicleFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(String name) throws UnknownVehicleException {
        if (name.equals("Voiture"))
            return new Car();
        else if (name.equals("Moto"))
            return new Motorbike();
        else if (name.equals("Camion"))
            return new Carrier();
        throw new UnknownVehicleException();
    }
}
