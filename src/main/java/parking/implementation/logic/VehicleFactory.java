package parking.implementation.logic;

import parking.api.business.contract.Vehicle;
import parking.api.exceptions.UnknownVehicleException;

/**
 * Created by loick on 15/01/15.
 */
public class VehicleFactory implements parking.implementation.gui.VehicleFactory {
    @Override
    public Vehicle createVehicle(String name) throws UnknownVehicleException {
        if (name.equals("Voiture"))
            return new Car();
        else if (name.equals("Moto"))
            return new Motorbike();
        else if (name.equals("Camion"))
            return new Carrier();
        throw new RuntimeException();
    }
}
