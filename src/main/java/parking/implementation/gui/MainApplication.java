package parking.implementation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.api.business.parking.ParkingManagerSerializer;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;
import parking.api.exceptions.ParkingNotPresentException;
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
    private Text currentFloor;
    private Text parkingTitle;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // create root
        BorderPane pane = new BorderPane();

        // Parking title label
        parkingTitle = new Text("");
        parkingTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        VBox.setMargin(parkingTitle, new Insets(2, 10, 2, 10));

        // create grid
        parkingGrid = new ParkingGrid(primaryStage);
        VBox.setMargin(parkingGrid, new Insets(2, 10, 2, 10));

        // Vertical container for the center of the window
        VBox vBox = new VBox(parkingTitle, parkingGrid);
        vBox.setAlignment(Pos.CENTER);

        // create top menu
        menu = new TopMenuBar(primaryStage, parkingGrid);
        menu.setOnChangeParking(this::parkingChangeHandler);

        // create bottom bar
        currentFloor = new Text();
        Button floorUpButton = new Button("Etage +1");
        Button floorDownButton = new Button("Etage -1");

        floorDownButton.setOnAction(event -> {
            parkingGrid.floorDown();
            updateFloorLabel();
        });

        floorUpButton.setOnAction(event -> {
            parkingGrid.floorUp();
            updateFloorLabel();
        });

        // Horizontal container for bottom bar content
        HBox hBox = new HBox(floorUpButton, currentFloor, floorDownButton);
        hBox.setAlignment(Pos.CENTER);
        HBox.setMargin(floorDownButton, new Insets(2, 10, 2, 10));
        HBox.setMargin(currentFloor, new Insets(2, 10, 2, 10));
        HBox.setMargin(floorUpButton, new Insets(2, 10, 2, 10));

        // Put it all in the shape in the pane
        pane.setTop(menu);
        pane.setPrefHeight(menu.getHeight());
        pane.setCenter(vBox);
        pane.setBottom(hBox);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("LesPatternsDuSwag - Parking qualitatif since 1889");
        primaryStage.setOnCloseRequest(event -> Platform.exit());

        // Show the splash page...
        StartSplashStage splash = new StartSplashStage(primaryStage);
        splash.showAndWait();
        switch (splash.getResult()) {
            case StartSplashStage.NEW:
                NewParkingStage newParkingStage = new NewParkingStage(primaryStage);
                newParkingStage.showAndWait();
                parkingChangeHandler(newParkingStage.getParking());
                break;
            case StartSplashStage.OPEN:
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Selectionner un Parking enregistré");
                File file = fileChooser.showOpenDialog(primaryStage);

                String fileName = (file == null) ? "save/parkingManager.ser" : String.valueOf(file);
                ParkingManagerSerializer.deserialize(fileName);
                try {
                    if (ParkingManager.getInstance().count() == 0)
                        throw new ParkingNotPresentException(0);

                    parkingChangeHandler(ParkingManager.getInstance().getParkingById(1));
                } catch (ParkingNotPresentException e) {
                    new Alert(Alert.AlertType.ERROR, "Le fichier ne contenait pas de parking valide.");
                }
                break;
            case StartSplashStage.EXIT:
            default:
                Platform.exit();
                return;
        }

        parkingGrid.updateGrid();
        currentFloor.setText("Etage 1/" + parkingGrid.floorCount());

        primaryStage.setResizable(false);
        primaryStage.show(); // show time !
    }

    private void updateFloorLabel() {
        currentFloor.setText("Etage " + parkingGrid.getFloor() + "/" + parkingGrid.floorCount());
    }

    private void parkingChangeHandler(Parking parking) {
        if (currentParking != null)
            parkingGrid.updateGrid();

        if (parking.equals(currentParking))
            return;

        this.currentParking = parking;
        parkingGrid.observeParkingChange(parking);
        menu.observeParkingChange(parking);
        updateFloorLabel();
        parkingTitle.setText(currentParking.getName());
    }
}
