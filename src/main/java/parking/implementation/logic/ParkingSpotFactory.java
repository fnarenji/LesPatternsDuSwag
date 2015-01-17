package parking.implementation.logic;

import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.ParkingSpotIdProvider;

/**
 * Created by  on 15/01/15.
 */
public class ParkingSpotFactory implements parking.api.business.contract.ParkingSpotFactory {
    private String nextVehicleType;
    private ParkingSpotIdProvider idProvider = new SimpleParkingSpotIdProvider();

    @Override
    public void setIdProvider(ParkingSpotIdProvider provider) {
        this.idProvider = provider;
    }

    public void setNextVehicleType(String nextVehicleType) {
        this.nextVehicleType = nextVehicleType;
    }

    @Override
    public ParkingSpot createParkingSpot() {
        Integer id = idProvider.nextId();
        System.out.println(id);
        if (nextVehicleType.equals("Car"))
            return new CarParkingSpot(id);
        else if (nextVehicleType.equals("Carrier"))
            return new CarrierParkingSpot(id);

        return null;
    }
}
