package parking.implementation;

import parking.api.business.contract.Vehicle;
import parking.api.exceptions.UnknowVehiculeException;

/**
 * Created by loick on 15/01/15.
 */
public class VehiculeFactory implements gui.VehiculeFactory {
    @Override
    public Vehicle createVehicule(String name) throws UnknowVehiculeException {
        if (name.equals("Voiture"))
            return new Car();
        else if (name.equals("Moto"))
            return new Motorbike();
        else if (name.equals("Camion"))
            return new Carrier();
        throw new RuntimeException();
    }
}
