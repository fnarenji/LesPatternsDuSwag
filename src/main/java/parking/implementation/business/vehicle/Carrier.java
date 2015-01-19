package parking.implementation.business.vehicle;

import parking.api.business.vehicle.BaseVehicle;

/**
 * Created by SKNZ on 31/12/2014.
 */
public class Carrier extends BaseVehicle {
    private double weight;

    /**
     * Get the weight of the vehicle
     *
     * @return the weight of the vehicle
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Set the weight of the vehicle
     *
     * @param weight weight of the vehicle
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
}
