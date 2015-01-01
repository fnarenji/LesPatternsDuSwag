package parking.api.business.concrete;

/**
 * Created by SKNZ on 31/12/2014.
 */
public class BaseVehicle implements Vehicle {
    private VehicleType type;
    private String plate;
    private String brand;
    private String model;

    @Override
    public String getPlate() {
        return plate;
    }

    @Override
    public void setPlate(String plate) {

        this.plate = plate;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public void setBrand(String brand) {

        this.brand = brand;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {

        this.model = model;
    }

    public BaseVehicle(VehicleType type) {
        this.type = type;
    }

    public BaseVehicle(VehicleType type, String plate, String brand, String model) {
        this.type = type;
        this.plate = plate;
        this.brand = brand;
        this.model = model;
    }

    @Override
    public VehicleType getType() {
        return type;
    }
}
