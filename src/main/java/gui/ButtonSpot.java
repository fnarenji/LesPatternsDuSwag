package gui;

import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import parking.api.business.contract.ParkingSpot;

/**
 * Created by on 14/01/15.
 */
public class ButtonSpot extends MenuButton {

    private ParkingSpot parkingSpot;

    public ButtonSpot(ParkingSpot ps) {
        super(Integer.toString(ps.getId()));
        this.parkingSpot = ps;
    }

    private ButtonSpot(String text) {
        super(text);
    }

    private ButtonSpot(String text, Node graphic) {
        super(text, graphic);
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }
}
