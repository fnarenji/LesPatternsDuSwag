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

    public static void setMainStage(Stage mainStage) {
        ParkingGui.mainStage = mainStage;
    }

    private Stage stage;
    private GridPane gridPane;
    private int nbMaxLine = 10;

    private Collection<Collection<GridPane>> gridPaneParking = new ArrayList<>();

    private ParkingManager parkingManager = ParkingManager.getInstance();
    private ParkingSpotFactory parkingSpotFactory;

    public static void main(String[] args) {
        launch(args);
    }

    private Collection<GridPane> generateParking() {
        ParkingListStage parkingListStage = new ParkingListStage(stage);
        parkingListStage.showAndWait();
        return parkingListStage.getParking();
    }


    private Menu createMenuClient() {
        Menu menuClient = new Menu("Client");
        MenuItem list = new MenuItem("Selectionner");
        list.setOnAction(event -> {
            ClientListStage clientListStage = new ClientListStage(stage, clients);
            clientListStage.showAndWait();

            System.out.println(clients);
        });

        MenuItem nouveau = new MenuItem("Nouveau");
        nouveau.setOnAction(event -> {
            ClientStage clientStage = new ClientStage(stage);
            clientStage.showAndWait();
            clients.add(clientStage.getClient());
        });

        menuClient.getItems().addAll(
                list,
                nouveau
        );

        return menuClient;
    }

    private Menu createMenuParking() {
        Menu menuParking = new Menu("Parking");
        MenuItem nouveau = new MenuItem("Nouveau");

        nouveau.setOnAction(event -> {
            ParkingListStage parkingListStage = new ParkingListStage(stage);
            parkingListStage.showAndWait();

            System.out.println(parkingListStage.getChoice());
        });

        menuParking.getItems().addAll(
                nouveau
        );

        return menuParking;
    }

    private Menu createMenuSelector() {
        Menu menuSelector = new Menu("AutoSelector");
        MenuItem find = new MenuItem("Find a place");
        MenuItem undo = new MenuItem("Unselect place");

        menuSelector.getItems().addAll(
                find,
                undo
        );

        return menuSelector;
    }

    private Menu createMenuQuit() {
        Menu menuQuit = new Menu();
        Label quitLabel = new Label("Quit");
        quitLabel.setOnMouseClicked(event -> {
            Alert confirm = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Êtes vous sûr de vouloir quitter ?"
            );
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == ButtonType.OK)
                stage.close();
        });

        menuQuit.setGraphic(quitLabel);

        return menuQuit;
    }

    public void updateGrid(Integer parking, Integer floor) {
        try {
            final int[] x = {0};
            final int[] y = {0};
            parkingManager.getParkingById(parking)
                    .forEach(spot -> {
                        if (y[0] == nbMaxLine) {
                            y[0]++;
                            x[0] = 0;
                        }

                        gridPane.add(
                                new ButtonSpot(
                                        spot,
                                        spot.getClass().toString(),
                                        stage,
                                        clients
                                ),
                                x[0]++,
                                y[0]
                        );
                    });
        } catch (ParkingNotPresentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;

        //generate parkings
        Collection<GridPane> parkingPanes = generateParking();

        try {
            ParkingSpotFactory parkingSpotFactory = new ParkingSpotFactory();
            parkingSpotFactory.setIdProvider(new FloorParkingSpotIdProvider());
            parkingSpotFactory.setNextVehicleType("Car");

            ParkingManager.getInstance().newParking(1, "Parking 1").newParkingSpot(parkingSpotFactory, 10);
        } catch (ParkingExistsException e) {
            e.printStackTrace();
        }

        updateGrid(0, 1);
        //active floor
        GridPane currentFloorPane = parkingPanes.iterator().next();

        //create root
        BorderPane borderPane = new BorderPane();

        //create top menu
        MenuBar menu = createMenu();

        // vertical layout box
        VBox vBox = new VBox();
        vBox.getChildren().add(menu);
        vBox.setPrefHeight(menu.getHeight());

        borderPane.setTop(vBox);
        borderPane.setPrefHeight(vBox.getHeight());

        borderPane.setCenter(gridPane);

        Scene scene = new Scene(borderPane, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("LesPatternsDuSwag - Parking de qualité since 1889");

        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show(); // show time !
    }

}
