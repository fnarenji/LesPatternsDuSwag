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
        switch (type){
            case "Car":
                System.out.println(type);
                return new CarParkingSpot(idProvider.nextId());
            case "Carrier":
                System.out.println(type);
                return  new CarrierParkingSpot(idProvider.nextId());
            default:
                return null;
        }
    }
}
