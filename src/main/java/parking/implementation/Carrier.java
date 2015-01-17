package parking.implementation;

import parking.api.business.concrete.BaseVehicle;

/**
 * Created by SKNZ on 31/12/2014.
 */
class Carrier extends BaseVehicle {
    private double weight;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
