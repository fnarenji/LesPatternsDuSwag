package parking.implementation.gui;

import javafx.scene.layout.GridPane;
import parking.api.business.concrete.ParkingManager;
import parking.api.business.contract.ParkingSpot;
import parking.api.exceptions.ParkingNotPresentException;

/**
 * Created by sknz on 1/17/15.
 */
public class ParkingGrid extends GridPane {
    private static final int MAX_LINE = 10;
    private ParkingManager parkingManager = ParkingManager.getInstance();

    public void updateGrid(Integer parking, Integer floor) {
        try {
            int x = 0;
            int y = 0;

            for (ParkingSpot parkingSpot : parkingManager.getParkingById(parking)) {
                if (y == MAX_LINE) {
                    y++;
                    x = 0;
                }

                add(new ButtonSpot(parkingSpot, ParkingGUI.getMainStage()), x++, y);
            }
        } catch (ParkingNotPresentException e) {
            e.printStackTrace();
        }
    }
}
