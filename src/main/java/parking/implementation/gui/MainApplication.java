package parking.implementation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import parking.api.business.concrete.ParkingManager;
import parking.api.exceptions.ParkingExistsException;
import parking.implementation.gui.controls.ParkingGrid;
import parking.implementation.gui.controls.TopMenuBar;
import parking.implementation.logic.FloorParkingSpotIdProvider;
import parking.implementation.logic.ParkingSpotFactory;

//Created by on 30/12/14.

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ParkingSpotFactory parkingSpotFactory = new ParkingSpotFactory();
        parkingSpotFactory.setIdProvider(new FloorParkingSpotIdProvider());
        parkingSpotFactory.setNextVehicleType("Car");

        try {
            ParkingManager.getInstance().newParking(1, "Parking 1").newParkingSpot(parkingSpotFactory, 10);
        } catch (ParkingExistsException e) {
            e.printStackTrace();
        }

        ParkingGrid parkingGrid = new ParkingGrid(primaryStage);
        parkingGrid.updateGrid(1, 1);

        //create root
        BorderPane borderPane = new BorderPane();

        //create top menu
        MenuBar menu = new TopMenuBar(primaryStage);

        borderPane.setTop(menu);
        borderPane.setPrefHeight(menu.getHeight());
        borderPane.setCenter(parkingGrid);

        Scene scene = new Scene(borderPane, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("LesPatternsDuSwag - Parking qualitatif since 1889");

        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show(); // show time !
    }
}
