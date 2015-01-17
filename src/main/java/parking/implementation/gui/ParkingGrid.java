package parking.implementation.gui;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import parking.api.business.concrete.Parking;
import parking.api.business.concrete.ParkingManager;
import parking.api.business.contract.ParkingSpot;
import parking.api.exceptions.ParkingNotPresentException;

/**
 * Created by sknz on 1/17/15.
 */
public class ParkingGrid extends GridPane {
    private static final int MAX_LINE = 10;
    private Stage primaryStage;

    public ParkingGrid(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void updateGrid(Integer parkingId, Integer floor) {
        try {
            int x = 0;
            int y = 0;

            Parking parking = ParkingManager.getInstance().getParkingById(parkingId);
            for (ParkingSpot parkingSpot : parking) {
                if (y == MAX_LINE) {
                    y++;
                    x = 0;
                }

                add(new ButtonSpot(parkingSpot, primaryStage), x++, y);
            }
        } catch (ParkingNotPresentException e) {
            e.printStackTrace();
        }
    }
}
