package parking.implementation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.api.business.parking.ParkingManagerSerializer;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;
import parking.implementation.business.logistic.floor.FloorParkingSpotIdProvider;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.business.parkingspot.CarrierParkingSpot;
import parking.implementation.business.logistic.simple.SimpleParkingSpotFactory;
import parking.implementation.business.vehicle.Car;
import parking.implementation.business.vehicle.Carrier;
import parking.implementation.business.vehicle.Motorbike;
import parking.implementation.gui.controls.ParkingGrid;
import parking.implementation.gui.controls.StartSplashStage;
import parking.implementation.gui.controls.TopMenuBar;
import parking.implementation.gui.stages.NewParkingStage;

import java.util.ArrayList;
import java.util.Collection;

//Created by on 30/12/14.

public class MainApplication extends Application {
    public static Collection<Class<? extends ParkingSpot>> ParkingSpotTypes = new ArrayList<>();
    public static Collection<Class<? extends Vehicle>> VehicleTypes = new ArrayList<>();

    static {
        ParkingSpotTypes.add(CarParkingSpot.class);
        ParkingSpotTypes.add(CarrierParkingSpot.class);

        VehicleTypes.add(Motorbike.class);
        VehicleTypes.add(Car.class);
        VehicleTypes.add(Carrier.class);
    }

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
                ParkingManagerSerializer.deserialize();
                break;
            case StartSplashStage.EXIT:
            default:
                return;
        }

        SimpleParkingSpotFactory parkingSpotFactory = new SimpleParkingSpotFactory();
        parkingSpotFactory.setIdProvider(new FloorParkingSpotIdProvider());
        parkingSpotFactory.setParkingSpotType("Car");

//        try {
//            ParkingManager.getInstance().newParking(1, "Parking 1").newParkingSpot(parkingSpotFactory, 10);
//        } catch (ParkingExistsException e) {
//            e.printStackTrace();
//        }

        ParkingGrid parkingGrid = new ParkingGrid(primaryStage);
        parkingGrid.updateGrid(1, 1);

        // create root
        BorderPane pane = new BorderPane();

        // create top menu
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
