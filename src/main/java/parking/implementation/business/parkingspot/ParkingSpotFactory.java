package parking.implementation.business.parkingspot;

import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.parkingspot.ParkingSpotIdProvider;
import parking.implementation.business.logistic.simple.SimpleParkingSpotIdProvider;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.business.parkingspot.CarrierParkingSpot;

/**
 * Created by  on 15/01/15.
 */
public class ParkingSpotFactory implements parking.api.business.parkingspot.ParkingSpotFactory {
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

        if (nextVehicleType.equals("Car"))
            return new CarParkingSpot(id);
        else if (nextVehicleType.equals("Carrier"))
            return new CarrierParkingSpot(id);

        return null;
    }
}
