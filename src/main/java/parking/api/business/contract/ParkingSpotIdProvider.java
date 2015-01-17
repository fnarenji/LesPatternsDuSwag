package parking.api.business.contract;

/**
 * Created by SKNZ on 29/12/2014.
 */
public interface ParkingSpotIdProvider {
    /**
     * Provide an id for a new place.
     *
     * @return
     */
    public Integer nextId();
}
