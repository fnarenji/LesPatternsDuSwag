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
            this.parkingManager.newParking(this.nbParking++, "Parking 1");

        } catch (ParkingExistsException e) {
            e.printStackTrace();
        }

        Collection<String> vehicules = new ArrayList<>();
        Map<String, Integer> nbVehicules = new HashMap<>();
        vehicules.add("Car");
        nbVehicules.put("Car", this.nbCar);
        vehicules.add("Carrier");
        nbVehicules.put("Carrier", this.nbCarrier);

        final int[] x = {0};
        final int[] y = {0};
        vehicules.forEach(vehicule -> {
            try {
                this.parkingManager.getParkingById(0)
                        .newParkingSpot(
                                this.parkingSpotFactory,
                                nbVehicules.get(vehicule),
                                vehicule)
                        .forEach(
                                spot -> {
                                    if (x[0] == maxInLine) {
                                        x[0] = 0;
                                        y[0]++;
                                    }

                                    ButtonSpot buttonSpot = new ButtonSpot(
                                            spot,
                                            vehicule,
                                            primaryStage
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
        MenuItem nouveau = new MenuItem("Nouveau");

        this.menuClient.getItems().addAll(
                nouveau
        );
    }

    private void createMenuParking() {
        this.menuParking = new Menu("Parking");
        MenuItem nouveau = new MenuItem("Nouveau");

        nouveau.setOnAction(event -> {
            ConstructStage constructStage = new ConstructStage(this.primaryStage);
            constructStage.showAndWait();

            this.nbCar = Integer.parseInt(constructStage.getNbCar());
            this.nbCarrier = Integer.parseInt(constructStage.getNbTruck());

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
