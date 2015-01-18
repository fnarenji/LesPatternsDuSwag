package parking.implementation.gui.controls;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import parking.api.business.parkingspot.ParkingSpot;

/**
 * Created by sknz on 1/18/15.
 */
public class ParkingTableViewRow {
    private boolean locked = false;
    private SimpleIntegerProperty floor = new SimpleIntegerProperty();
    private SimpleIntegerProperty quantity = new SimpleIntegerProperty();
    private SimpleObjectProperty<Class<? extends ParkingSpot>> type = new SimpleObjectProperty<>();

    public ParkingTableViewRow(Integer floor, Integer quantity, Class<? extends ParkingSpot> type) {
        setFloor(floor);
        setQuantity(quantity);
        setType(type);
    }

    public ParkingTableViewRow(Integer floor, Integer quantity, Class<? extends ParkingSpot> type, boolean locked) {
        this(floor, quantity, type);
        this.locked = locked;
    }

    public int getFloor() {
        return floor.get();
    }

    public void setFloor(int floor) {
        this.floor.set(floor);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public Class<? extends ParkingSpot> getType() {
        return type.get();
    }

    public void setType(Class<? extends ParkingSpot> type) {
        this.type.set(type);
    }

    public boolean isLocked() {
        return locked;
    }
}
