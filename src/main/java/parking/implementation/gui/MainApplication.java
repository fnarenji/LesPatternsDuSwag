package parking.implementation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.api.exceptions.ParkingExistsException;
import parking.implementation.business.logistic.floor.FloorParkingSpotIdProvider;
import parking.implementation.business.logistic.simple.SimpleParkingSpotSelector;
import parking.implementation.business.parkingspot.ParkingSpotFactory;
import parking.implementation.gui.controls.ParkingGrid;
import parking.implementation.gui.controls.StartSplashStage;
import parking.implementation.gui.controls.TopMenuBar;
import parking.implementation.gui.stages.NewParkingStage;

//Created by on 30/12/14.

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        StartSplashStage splash = new StartSplashStage(primaryStage);
        splash.showAndWait();
        switch (splash.getResult()) {
            case StartSplashStage.NEW:
                NewParkingStage newParkingStage = new NewParkingStage(primaryStage);
                newParkingStage.showAndWait();
                newParkingStage.applyChanges();
                break;
            case StartSplashStage.OPEN:
                System.out.println(2);
                break;
            case StartSplashStage.EXIT:
            default:
                return;
        }

        ParkingSpotFactory parkingSpotFactory = new ParkingSpotFactory();
        parkingSpotFactory.setIdProvider(new FloorParkingSpotIdProvider());
        parkingSpotFactory.setNextVehicleType("Car");

//        try {
//            ParkingManager.getInstance().newParking(1, "Parking 1").newParkingSpot(parkingSpotFactory, 10);
//        } catch (ParkingExistsException e) {
//            e.printStackTrace();
//        }

        ParkingGrid parkingGrid = new ParkingGrid(primaryStage);
        parkingGrid.updateGrid(1, 1);

        //create root
        BorderPane pane = new BorderPane();

        //create top menu
        MenuBar menu = new TopMenuBar(primaryStage);

        pane.setTop(menu);
        pane.setPrefHeight(menu.getHeight());
        pane.setCenter(parkingGrid);

        Scene scene = new Scene(pane, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("LesPatternsDuSwag - Parking qualitatif since 1889");
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show(); // show time !
    }
}
