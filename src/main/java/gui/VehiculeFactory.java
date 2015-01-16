package gui;

import parking.api.business.contract.Vehicle;
import parking.api.exceptions.UnknowVehiculeException;

/**
 * Created by loick on 15/01/15.
 */
public interface VehiculeFactory {

    public Vehicle createVehicule(String vehicule) throws UnknowVehiculeException;
}
