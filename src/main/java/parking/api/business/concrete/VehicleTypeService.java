package parking.api.business.concrete;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SKNZ on 02/01/2015.
 */
public class VehicleTypeService {
    private static VehicleTypeService instance = new VehicleTypeService();
    private List<VehicleType> registeredVehicleTypes = new ArrayList<>();

    private VehicleTypeService() {

    }

    public static VehicleTypeService getInstance() {
        return instance;
    }

    public void registerType(String name) {
        registeredVehicleTypes.add(new VehicleType(name));
    }

    public List<VehicleType> getVehicleTypes() {
        return registeredVehicleTypes;
    }
}
