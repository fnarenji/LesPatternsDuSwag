package parking.implementation;

import parking.api.business.contract.Vehicle;
import parking.api.exceptions.UnknowVehiculeException;

/**
 * Created by loick on 15/01/15.
 */
public class VehiculeFactory implements parking.api.business.contract.VehiculeFactory {
    @Override
    public Vehicle createVehicule(String name) throws UnknowVehiculeException {

        Vehicle vehicule = null;

        switch (name) {
            case "Voiture":
                vehicule = new Car();
                break;
            case "Moto":
                vehicule = new Motorbike();
                break;
            case "Camion":
                vehicule = new Carrier();
                break;
            default:
                throw new UnknowVehiculeException();
        }

        return vehicule;
    }
}
