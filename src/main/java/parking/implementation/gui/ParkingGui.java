package parking.implementation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import parking.api.business.concrete.Parking;
import parking.api.business.concrete.ParkingManager;
import parking.api.business.contract.ParkingSpot;
import parking.api.exceptions.ParkingExistsException;
import parking.api.exceptions.ParkingNotPresentException;
import parking.implementation.logic.Client;
import parking.implementation.logic.FloorParkingSpotIdProvider;
import parking.implementation.logic.ParkingSpotFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

//Created by on 30/12/14.

public class ParkingGui extends Application {
    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    private GridPane gridPane;
    private int nbMaxLine = 10;

    private ParkingManager parkingManager = ParkingManager.getInstance();

    public void updateGrid(Integer parking, Integer floor) {
        try {
            int x = 0;
            int y = 0;

            for (ParkingSpot parkingSpot : parkingManager.getParkingById(parking)) {
                if (y == nbMaxLine) {
                    y++;
                    x = 0;
                }

                gridPane.add(
                        new ButtonSpot(parkingSpot, parkingSpot.getClass().toString(), getMainStage()), x++, y);
            }
        } catch (ParkingNotPresentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;

        ParkingSpotFactory parkingSpotFactory = new ParkingSpotFactory();
        parkingSpotFactory.setIdProvider(new FloorParkingSpotIdProvider());
        parkingSpotFactory.setNextVehicleType("Car");

        try {
            ParkingManager.getInstance().newParking(1, "Parking 1").newParkingSpot(parkingSpotFactory, 10);
        } catch (ParkingExistsException e) {
            e.printStackTrace();
        }

        gridPane = new GridPane();
        updateGrid(1, 1);

        //create root
        BorderPane borderPane = new BorderPane();

        //create top menu
        MenuBar menu = new TopMenuBar();

        // vertical layout box
        /*VBox vBox = new VBox();
        vBox.getChildren().add(menu);
        vBox.setPrefHeight(menu.getHeight());*/

        borderPane.setTop(menu);
        borderPane.setPrefHeight(menu.getHeight());
        borderPane.setCenter(gridPane);

        Scene scene = new Scene(borderPane, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("LesPatternsDuSwag - Parking qualitatif since 1889");

        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show(); // show time !
    }

    public static void main(String[] args) {
        launch(args);
    }
}
