package parking.api.business.concrete;

/**
 * Created by SKNZ on 29/12/2014.
 */
public interface Vehicle {
    String getPlate();

    void setPlate(String plate);

    String getBrand();

    void setBrand(String brand);

    String getModel();

    void setModel(String model);

    VehicleType getType();
}
