package parking.implementation.gui.controls;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.exceptions.ParkingNotPresentException;
import parking.implementation.gui.MainApplication;

/**
 * Created by sknz on 1/17/15.
 * This class create the gird which will determinate the parking
 */
public class ParkingGrid extends GridPane {
    private static final int MAX_LINE = 10;
    private Stage primaryStage;
    private Parking currentParking;

    public ParkingGrid(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void updateGrid(Integer floor) {
        int x = 0;
        int y = 0;
        for (ParkingSpot parkingSpot : currentParking) {
            if (x == MAX_LINE) {
                ++y;
                x = 0;
            }

            ButtonSpot buttonSpot = new ButtonSpot(parkingSpot, primaryStage);
            GridPane.setMargin(buttonSpot, new Insets(8));
            add(buttonSpot, x++, y);
        }
    }

    public void observeParkingChange(Parking parking) {
        currentParking = parking;
    }
}
