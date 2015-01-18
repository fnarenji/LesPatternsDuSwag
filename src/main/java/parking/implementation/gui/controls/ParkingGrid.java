package parking.implementation.gui.controls;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import parking.api.business.parking.Parking;
import parking.api.business.parkingspot.ParkingSpot;
import parking.implementation.business.logistic.floor.FloorParkingSpotIdProvider;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by sknz on 1/17/15.
 * This class create the gird which will determinate the parking
 */
public class ParkingGrid extends GridPane {
    private static final int MAX_LINE = 10;
    private Stage primaryStage;
    private Parking currentParking;
    private Map<Integer, ButtonSpot> buttonSpotMap = new TreeMap<>();

    public ParkingGrid(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Integer x, y;
    public void updateGrid(Integer floor) {
        x = 0;
        y = 0;

        for (ParkingSpot parkingSpot : currentParking) {
            ButtonSpot buttonSpot = new ButtonSpot(parkingSpot, primaryStage);
            buttonSpotMap.put(buttonSpot.getParkingSpot().getId(), buttonSpot);
        }

        buttonSpotMap.values().stream()
            .filter(buttonSpot -> FloorParkingSpotIdProvider.ExtractFloor(buttonSpot.getParkingSpot().getId()) == floor)
            .forEachOrdered(buttonSpot -> {
                if (x == MAX_LINE) {
                    ++y;
                    x = 0;
                }

                GridPane.setMargin(buttonSpot, new Insets(8));
                add(buttonSpot, x++, y);
            });
    }

    public void observeParkingChange(Parking parking) {
        currentParking = parking;
        updateGrid(1);
    }

    public ButtonSpot highlightButton(Integer id) {
        return buttonSpotMap.get(id);
    }
}
