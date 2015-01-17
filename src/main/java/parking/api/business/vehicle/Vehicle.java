package parking.api.business.vehicle;

/**
 * Created by SKNZ on 29/12/2014.
 */
public interface Vehicle {
    /**
     * Get the plate of the vehicle.
     *
     * @return The plate of the vehicle
     */
    String getPlate();

    /**
     * Set the plate of the vehicle.
     *
     * @param plate The vehicles plate.
     */
    void setPlate(String plate);

    /**
     * Get the brand of the vehicle
     *
     * @return The brand of the vehicle
     */
    String getBrand();

    /**
     * Set the brand of the vehicle.
     *
     * @param brand The brand of the vehicle
     */
    void setBrand(String brand);

    /**
     * Get the vehicle of the vehicle.
     *
     * @return The model of the vehicle
     */
    String getModel();

    /**
     * Sets the model of the vehicle.
     *
     * @param model The model of the vehicle
     */
    void setModel(String model);

    /**
     * Get the owner.
     *
     * @return the owner
     */
    Object getOwner();

    /**
     * Sets the owner.
     *
     * @param owner The owner of the vehicle
     */
    void setOwner(Object owner);
}
