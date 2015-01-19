package parking.implementation.business.logistic.simple;

import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.parkingspot.ParkingSpotFactory;
import parking.api.business.parkingspot.ParkingSpotIdProvider;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.business.parkingspot.CarrierParkingSpot;

/**
 * Created by  on 15/01/15.
 */
public class SimpleParkingSpotFactory implements ParkingSpotFactory {
    private Class parkingSpotType;
    private ParkingSpotIdProvider idProvider = new SimpleParkingSpotIdProvider();

    @Override
    public void setIdProvider(ParkingSpotIdProvider provider) {
        this.idProvider = provider;
    }

    public void setParkingSpotType(Class<? extends ParkingSpot> nextParkingSpotType) {
        this.parkingSpotType = nextParkingSpotType;
    }

    @Override
    public ParkingSpot createParkingSpot() {
        Integer id = idProvider.nextId();

        if (parkingSpotType.equals(CarParkingSpot.class))
            return new CarParkingSpot(id);
        else if (parkingSpotType.equals(CarrierParkingSpot.class))
            return new CarrierParkingSpot(id);

        return null;
    }
}
