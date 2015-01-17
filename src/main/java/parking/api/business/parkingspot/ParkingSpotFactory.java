package parking.api.business.parkingspot;

/**
 * Created by SKNZ on 29/12/2014.
 */
public interface ParkingSpotFactory {
    /**
     * Create a new parking
     *
     * @param provider id provider which will be used during the creation of the parking spot
     */
    void setIdProvider(ParkingSpotIdProvider provider);

    ParkingSpot createParkingSpot();
}
