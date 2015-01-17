package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import parking.api.business.concrete.ParkingManager;
import parking.api.exceptions.ParkingExistsException;
import parking.api.exceptions.ParkingNotPresentException;

import parking.implementation.Client;
import parking.implementation.ParkingSpotFactory;

import java.util.*;

//Created by on 30/12/14.

public class ParkingGui extends Application {

    private Stage primaryStage;
    private GridPane gridPane;
    private Menu menuClient;
    private Menu menuParking;
    private Menu menuSelector;
    private Menu menuQuit;

    private int maxInLine = 10;
    private int nbParking = 0;
    private int nbCar;
    private int nbCarrier;
    private Collection<Client> clients = new ArrayList<>();
    private ParkingManager parkingManager;
    private ParkingSpotFactory parkingSpotFactory;

    public static void main(String[] args) {
        launch(args);
    }

    private void generateParking() {
        try {
            this.parkingManager = ParkingManager.getInstance();
            this.parkingSpotFactory = new ParkingSpotFactory();

            this.parkingManager.setCompanyName("SWAG COMPANY");
            this.parkingManager.newParking(++nbParking, "Parking 1");

        } catch (ParkingExistsException e) {
            e.printStackTrace();
        }

        Collection<String> vehicles = new ArrayList<>();
        Map<String, Integer> nbVehicles = new HashMap<>();
        vehicles.add("Car");
        nbVehicles.put("Car", this.nbCar);
        vehicles.add("Carrier");
        nbVehicles.put("Carrier", this.nbCarrier);

        final int[] x = {0};
        final int[] y = {0};
        vehicles.forEach(vehicle -> {
            try {
                this.parkingSpotFactory.setNextVehicleType(vehicle);
                this.parkingManager.getParkingById(0)
                        .newParkingSpot(
                                this.parkingSpotFactory,
                                nbVehicles.get(vehicle))
                        .forEach(
                                spot -> {
                                    if (x[0] == maxInLine) {
                                        x[0] = 0;
                                        y[0]++;
                                    }

                                    ButtonSpot buttonSpot = new ButtonSpot(
                                            spot,
                                            vehicle,
                                            primaryStage,
                                            clients
                                    );

                                    gridPane.add(
                                            buttonSpot,
                                            x[0]++,
                                            y[0]
                                    );
                                }
                        );
            } catch (ParkingNotPresentException e) {
                e.printStackTrace();
            }
        });

        this.primaryStage.setHeight(
                y[0] * 50 + 90  //50: button height & 90: menu height
        );
    }

    private void createMenu() {
        createMenuClient();
        createMenuParking();
        createMenuSelector();
        createMenuQuit();

    }

    private void createMenuClient() {
        this.menuClient = new Menu("Client");
        MenuItem list = new MenuItem("Selectionner");
        list.setOnAction(event -> {
            ClientListStage clientListStage = new ClientListStage(primaryStage, clients);
            clientListStage.showAndWait();

            System.out.println(clients);
        });

        MenuItem nouveau = new MenuItem("Nouveau");
        nouveau.setOnAction(event -> {
            ClientStage clientStage = new ClientStage(primaryStage);
            clientStage.showAndWait();
            clients.add(clientStage.getClient());
        });

        this.menuClient.getItems().addAll(
                list,
                nouveau
        );
    }

    private void createMenuParking() {
        this.menuParking = new Menu("Parking");
        MenuItem nouveau = new MenuItem("Nouveau");

        nouveau.setOnAction(event -> {
            ConstructStage constructStage = new ConstructStage(this.primaryStage);
            constructStage.showAndWait();

            this.nbCar = constructStage.getCarNumberField();
            this.nbCarrier = constructStage.getTruckNumberField();

            if (nbCar != 0 || nbCarrier != 0)
                generateParking();
        });

        this.menuParking.getItems().addAll(
                nouveau
        );
    }

    private void createMenuSelector() {
        this.menuSelector = new Menu("AutoSelector");
        MenuItem find = new MenuItem("Find a place");
        MenuItem undo = new MenuItem("Unselect place");

        this.menuSelector.getItems().addAll(
                find,
                undo
        );
    }

    private void createMenuQuit() {
        this.menuQuit = new Menu("Quit");
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(event -> {
            Alert confirm = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Êtes vous sûr de vouloir quitter ?"
            );
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == ButtonType.OK)
                this.primaryStage.close();
        });

        this.menuQuit.getItems().addAll(
                quit
        );
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setOnCloseRequest(event -> Platform.exit());

        this.gridPane = new GridPane();
        BorderPane root = new BorderPane();
        VBox topContainer = new VBox();
        MenuBar mainMenu = new MenuBar();
        ToolBar toolBar = new ToolBar();

        topContainer.getChildren().add(mainMenu);
        topContainer.getChildren().add(toolBar);
        root.setTop(topContainer);
        root.setCenter(gridPane);

        clients.add(new Client("", "Anonyme", ""));
        Client currentClient = clients.iterator().next();

        createMenu();
        mainMenu.getMenus().addAll(
                this.menuClient,
                this.menuParking,
                this.menuSelector,
                this.menuQuit
        );

        Scene sc = new Scene(
                root,
                600,
                500
        );

        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
        primaryStage.setTitle("SWAG Parking");
        primaryStage.show();

    }

}
