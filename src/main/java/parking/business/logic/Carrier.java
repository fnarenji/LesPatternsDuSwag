package parking.business.logic;

import parking.business.vehicles.Vehicle;

/**
 * Created by LesPatternsDuSwag on 27/12/14.
 */
public class Carrier implements ParkingSpot {

    Vehicle V = null;
    Boolean booked = false;
    Integer id = null;

    @Override
    public boolean empty() {
        return (V == null);
    }

    @Override
    public boolean canFit(Vehicle vehicle) {
        return empty();
    }

    @Override
    public void park(Vehicle vehicle) {
        V = vehicle;
    }

    @Override
    public Vehicle unpark() {
        Vehicle tmp = V;
        V = null;
        return tmp;
    }

    @Override
    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    @Override
    public Boolean getBooked() {
        return booked;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
