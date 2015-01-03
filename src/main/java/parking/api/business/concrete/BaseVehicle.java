package parking.api.business.concrete;

import parking.api.business.contract.Vehicle;

/**
 * Created by SKNZ on 31/12/2014.
 */
public class BaseVehicle implements Vehicle {
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

    @Override
    public String toString() {
        return "BaseVehicle{" +
                "plate='" + plate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseVehicle)) return false;

        BaseVehicle that = (BaseVehicle) o;

        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (!plate.equals(that.plate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = plate.hashCode();
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }
}
