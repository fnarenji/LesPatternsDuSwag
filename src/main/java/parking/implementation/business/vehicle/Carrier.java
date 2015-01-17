package parking.implementation.business.vehicle;

import parking.api.business.vehicle.BaseVehicle;

/**
 * Created by SKNZ on 31/12/2014.
 */
public class Carrier extends BaseVehicle {
    private double weight;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
