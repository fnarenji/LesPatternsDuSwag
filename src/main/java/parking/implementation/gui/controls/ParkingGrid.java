package parking.implementation.gui.controls;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import parking.api.business.parking.Parking;
import parking.api.business.parkingspot.ParkingSpot;
import parking.implementation.business.logistic.floor.FloorParkingSpotIdProvider;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sknz on 1/17/15.
 * This class create the gird which will determinate the parking
 */
public class ParkingGrid extends GridPane {
    private static final int MAX_LINE = 10;
    private Stage primaryStage;
    private Parking currentParking;
    private Map<Integer, ButtonSpot> buttonSpotMap = new TreeMap<>();
    private int floor = 1;

    public ParkingGrid(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Integer x, y;

    public void updateGrid() {
        x = 0;
        y = 0;
        getChildren().clear();
        buttonSpotMap.clear();

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

        primaryStage.sizeToScene();
    }

    public void observeParkingChange(Parking parking) {
        currentParking = parking;
        floorOne();
        updateGrid();
    }

    public void floorDown() {
        do {
            floor = Math.max(floor - 1, 1);
        }
        while (currentParking.stream().noneMatch(parkingSpot -> FloorParkingSpotIdProvider.ExtractFloor(parkingSpot.getId()) == floor) && floor > 0);
        updateGrid();
    }

    public void floorUp() {
        Integer floorCount = floorCount();
        do {
            floor = Math.min(floor + 1, floorCount);
        }
        while (currentParking.stream().noneMatch(parkingSpot -> FloorParkingSpotIdProvider.ExtractFloor(parkingSpot.getId()) == floor) && floor < floorCount);
        updateGrid();
    }

    public Integer floorCount() {
        return buttonSpotMap.keySet().stream()
                .map(FloorParkingSpotIdProvider::ExtractFloor)
                .max(Integer::compareTo)
                .get();
    }

    public void floorOne() {
        floor = 1;
        updateGrid();
    }

    public int getFloor() {
        return floor;
    }
}
