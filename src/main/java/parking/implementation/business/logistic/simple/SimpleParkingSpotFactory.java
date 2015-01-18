package parking.implementation.business.logistic.simple;

import parking.api.business.parking.Parking;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.parkingspot.ParkingSpotIdProvider;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.business.parkingspot.CarrierParkingSpot;

/**
 * Created by  on 15/01/15.
 */
public class SimpleParkingSpotFactory implements parking.api.business.parkingspot.ParkingSpotFactory {
    private Class parkingSpotType;
    private ParkingSpotIdProvider idProvider = new SimpleParkingSpotIdProvider();

    @Override
    public void setIdProvider(ParkingSpotIdProvider provider) {
        this.idProvider = provider;
    }

    public void setParkingSpotType(String parkingSpotType) {
        if (parkingSpotType.equals("Car"))
            this.setParkingSpotType(CarParkingSpot.class);
        else if (parkingSpotType.equals("Carrier"))
            this.setParkingSpotType(CarrierParkingSpot.class);
        else
            this.setParkingSpotType(ParkingSpot.class);
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
