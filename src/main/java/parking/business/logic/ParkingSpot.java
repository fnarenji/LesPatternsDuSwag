package parking.business.logic;

import parking.business.vehicles.Vehicle;

/**
 * Created by sknz on 24/12/14.
 */
public interface ParkingSpot {
    boolean empty();
    boolean canFit(Vehicle vehicle);

    void park(Vehicle vehicle);
    Vehicle unpark();

    Integer getId();
    void setId(Integer id);

    Boolean getBooked();
    void setBooked(Boolean booked);
}
