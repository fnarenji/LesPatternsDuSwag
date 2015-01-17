package parking.api.business.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by SKNZ on 02/01/2015.
 */
public class ParkingSpotByVehicleTypePriorityMap {
    private Map<Class, ArrayList<Class>> parkingSpotPriorityMap = new HashMap<>();

    public ParkingSpotByVehicleTypePriorityMap() {
    }

    public InnerHelper buildFor(Class vehicleClass) {
        return new InnerHelper(parkingSpotPriorityMap.get(vehicleClass));
    }

    public Iterator<Class> whereTo(Class vehicleClass) {
        return parkingSpotPriorityMap.get(vehicleClass).iterator();
    }

    public class InnerHelper {
        private final ArrayList<Class> priorityList;

        private InnerHelper(ArrayList<Class> priorityList) {
            this.priorityList = priorityList;
        }

        public InnerHelper park(Class parkingSpotClass) {
            priorityList.add(parkingSpotClass);
            return this;
        }
    }
}
