package parking.api.business.contract;

/**
 * Created by SKNZ on 29/12/2014.
 */
public interface ParkingSpotFactory {
    void setIdProvider(ParkingSpotIdProvider provider);

    ParkingSpot createParkingSpot(String type);
}
