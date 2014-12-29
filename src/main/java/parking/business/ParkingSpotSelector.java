package parking.business;

import java.util.Collection;

/**
 * Created by SKNZ on 30/12/2014.
 */
public interface ParkingSpotSelector {
    public ParkingSpot select(Collection<ParkingSpot> parkingSpots);
}
