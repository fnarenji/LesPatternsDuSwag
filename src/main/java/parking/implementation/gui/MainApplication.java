package parking.implementation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.api.business.parking.ParkingManagerSerializer;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.business.parkingspot.CarrierParkingSpot;
import parking.implementation.business.vehicle.Car;
import parking.implementation.business.vehicle.Carrier;
import parking.implementation.business.vehicle.Motorbike;
import parking.implementation.gui.controls.ParkingGrid;
import parking.implementation.gui.controls.StartSplashStage;
import parking.implementation.gui.controls.TopMenuBar;
import parking.implementation.gui.stages.NewParkingStage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

//Created by on 30/12/14.

/**
 * Main application : launch everything to create the GUI
 */

public class MainApplication extends Application {
    public static Collection<Class<? extends ParkingSpot>> ParkingSpotTypes = new ArrayList<>();
    public static Collection<Class<? extends Vehicle>> VehicleTypes = new ArrayList<>();
    private Parking currentParking = null;

    static {
        ParkingSpotTypes.add(CarParkingSpot.class);
        ParkingSpotTypes.add(CarrierParkingSpot.class);

        VehicleTypes.add(Motorbike.class);
        VehicleTypes.add(Car.class);
        VehicleTypes.add(Carrier.class);
    }

    private ParkingGrid parkingGrid;
    private TopMenuBar menu;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // create root
        BorderPane pane = new BorderPane();

        parkingGrid = new ParkingGrid(primaryStage);

        // create top menu
        menu = new TopMenuBar(primaryStage, parkingGrid);
        menu.setOnChangeParking(this::setCurrentParking);

        pane.setTop(menu);
        pane.setPrefHeight(menu.getHeight());
        pane.setCenter(parkingGrid);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("LesPatternsDuSwag - Parking qualitatif since 1889");
        primaryStage.setOnCloseRequest(event -> Platform.exit());

        StartSplashStage splash = new StartSplashStage(primaryStage);
        splash.showAndWait();
        switch (splash.getResult()) {
            case StartSplashStage.NEW:
                NewParkingStage newParkingStage = new NewParkingStage(primaryStage);
                newParkingStage.showAndWait();
                setCurrentParking(newParkingStage.getParking());
                break;
            case StartSplashStage.OPEN:
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Selectionner un Parking enregistré");
                File file = fileChooser.showOpenDialog(primaryStage);

                String fileName = (file == null) ? "save/parkingManager.ser" : String.valueOf(file);
                ParkingManagerSerializer.deserialize(fileName);
                break;
            case StartSplashStage.EXIT:
            default:
                return;
        }

        parkingGrid.updateGrid(1);

        primaryStage.setResizable(false);
        primaryStage.show(); // show time !
    }

    private void setCurrentParking(Parking parking) {
        this.currentParking = parking;
        parkingGrid.observeParkingChange(parking);
        menu.observeParkingChange(parking);
        primaryStage.sizeToScene();
    }
}
