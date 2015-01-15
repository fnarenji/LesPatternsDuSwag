package parking.implementation;
import parking.api.business.contract.ParkingSpot;
import java.util.Comparator;


/**
 * Created by thomasmunoz on 15/01/15.
 */
public class ParkingSpotBookedComparator  implements Comparator<ParkingSpot>{
    private ParkingSpot ps;

    @Override
    public int compare(ParkingSpot o1, ParkingSpot o2) {
        return o1.isBooked().compareTo(o2.isBooked());
    }
}
