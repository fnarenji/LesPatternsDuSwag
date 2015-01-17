package parking.api.business.concrete;

import parking.api.exceptions.ParkingBookedSpotsExceptions;
import parking.api.exceptions.ParkingExistsException;
import parking.api.exceptions.ParkingNotPresentException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by SKNZ on 28/12/2014.
 */
public class ParkingManager {
    private static ParkingManager instance = new ParkingManager();
    private String companyName;
    private Map<Integer, Parking> parkingsById = new HashMap<>();

    private ParkingManager() {

    }

    // For unit testing purposes only, PACKAGE LOCAL
    static void resetSingleton() {
        instance = new ParkingManager();
    }

    public static ParkingManager getInstance() {
        return instance;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Parking newParking(String name) throws ParkingExistsException {
        int id = this.count();
        return this.newParking(++id, name);
    }

    public Parking newParking(Integer id, String name) throws ParkingExistsException {
        if (parkingsById.containsKey(id))
            throw new ParkingExistsException(id);

        Parking parking = new Parking(id, name);
        parkingsById.put(id, parking);

        return parking;
    }

    public void deleteParking(Integer id) throws ParkingNotPresentException, ParkingBookedSpotsExceptions {
        if (!parkingsById.containsKey(id))
            throw new ParkingNotPresentException(id);

        Parking parking = parkingsById.get(id);
        if (parking.countParkingSpots(parkingSpot -> false) != 0)
            throw new ParkingBookedSpotsExceptions(parking);

        parkingsById.remove(parking.getId());
    }

    public Boolean containsParking(Integer id) {
        return parkingsById.containsKey(id);
    }

    public Parking getParkingById(Integer id) throws ParkingNotPresentException {
        if (!parkingsById.containsKey(id))
            throw new ParkingNotPresentException(id);

        return parkingsById.get(id);
    }

    public void forEach(Consumer<Parking> consumer) {
        parkingsById.values().forEach(consumer);
    }

    public int count() {
        return count(parking -> true);
    }

    public int count(Predicate<Parking> predicate) {
        int i = 0;
        for (Parking parking : parkingsById.values()) {
            if (predicate.test(parking))
                ++i;
        }
        return i;
    }
}
