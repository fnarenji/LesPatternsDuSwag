package parking.api.business.contract;

import parking.api.business.concrete.BaseParkingSpot;
import parking.api.business.concrete.BaseVehicle;

/**
 * Created by loic on 17/01/15.
 */
public interface Invoice {
    public double computeInvoice(BaseVehicle baseVehicle, BaseParkingSpot baseParkingSpot);
    
}
