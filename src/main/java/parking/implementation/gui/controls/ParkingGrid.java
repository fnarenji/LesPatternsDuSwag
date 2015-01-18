package parking.implementation.gui.controls;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.exceptions.ParkingNotPresentException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sknz on 1/17/15.
 * This class create the gird which will determinate the parking
 */
public class ParkingGrid extends GridPane {
    private static final int MAX_LINE = 10;
    private Stage primaryStage;
    private Map<Integer,ButtonSpot> buttonSpotMap = new HashMap<>();

    public ParkingGrid(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void updateGrid(Integer parkingId, Integer floor) {
        try {
            int x = 0;
            int y = 0;

            Parking parking = ParkingManager.getInstance().getParkingById(parkingId);
            for (ParkingSpot parkingSpot : parking) {
                if (x == MAX_LINE) {
                    ++y;
                    x = 0;
                }
                ButtonSpot tmp = new ButtonSpot(parkingSpot, primaryStage);
                add(tmp, x++, y);
                buttonSpotMap.put(tmp.getParkingSpot().getId(),tmp);
            }
        } catch (ParkingNotPresentException e) {
            e.printStackTrace();
        }
    }
    
    public Map getButtonSpotMap(){
        return buttonSpotMap;
    }
}
