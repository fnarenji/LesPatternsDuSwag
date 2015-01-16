package parking.implementation;

import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.ParkingSpotIdProvider;

/**
 * Created by  on 15/01/15.
 */
public class ParkingSpotFactory implements parking.api.business.contract.ParkingSpotFactory {

    private ParkingSpotIdProvider idProvider = new SimpleParkingSpotIdProvider();

    @Override
    public void setIdProvider(ParkingSpotIdProvider provider) {
        this.idProvider = provider;
    }

    @Override
    public ParkingSpot createParkingSpot(String type) {
        Integer id = idProvider.nextId();
        System.out.println(id);
        if (type.equals("Car"))
            return new CarParkingSpot(id);
        else if (type.equals("Carrier"))
            return new CarrierParkingSpot(id);
        throw new RuntimeException();
    }
}
