package parking.implementation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import parking.api.business.parking.ParkingManager;
import parking.api.exceptions.ParkingExistsException;
import parking.implementation.gui.controls.MainSplash;
import parking.implementation.gui.controls.ParkingGrid;
import parking.implementation.gui.controls.TopMenuBar;
import parking.implementation.business.logistic.floor.FloorParkingSpotIdProvider;
import parking.implementation.business.parkingspot.ParkingSpotFactory;

//Created by on 30/12/14.

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LesPatternsDuSwag - Parking qualitatif since 1889");
        primaryStage.setOnCloseRequest(event -> Platform.exit());

        MainSplash splash = new MainSplash();
        splash.run(primaryStage);
        switch (splash.getResult()) {
            case 1:
                System.out.println(1);
                break;
            case 2:
                System.out.println(2);
                break;
            default:
                System.out.println("CASSOS !");
                break;
        }
/*
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
        BorderPane pane = new BorderPane();

        //create top menu
        MenuBar menu = new TopMenuBar(primaryStage);

        pane.setTop(menu);
        pane.setPrefHeight(menu.getHeight());
        pane.setCenter(parkingGrid);

        Scene scene = new Scene(pane, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show(); // show time !*/
    }
}
