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
import parking.implementation.Client;
import parking.implementation.ParkingSpotFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

//Created by on 30/12/14.

public class ParkingGui extends Application {
    private Stage stage;
    private BorderPane borderPane;

    private Client currentClient;
    private Collection<Collection<GridPane>> gridPaneParking = new ArrayList<>();

    private Collection<Client> clients = new ArrayList<>();
    private ParkingManager parkingManager;
    private ParkingSpotFactory parkingSpotFactory;

    public static void main(String[] args) {
        launch(args);
    }

    private Collection<GridPane> generateParking() {
        ParkingListStage parkingListStage = new ParkingListStage(stage, clients);
        parkingListStage.showAndWait();
        return parkingListStage.getParking();
    }

    private MenuBar createMenu() {
        Menu menuClient = createMenuClient();
        Menu menuParking = createMenuParking();
        Menu menuSelector = createMenuSelector();
        Menu menuQuit = createMenuQuit();

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(
                menuClient,
                menuParking,
                menuSelector,
                menuQuit
        );

        return menuBar;
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
        MenuItem select = new MenuItem("Selectionner");

        MenuItem nouveau = new MenuItem("Nouveau");

        nouveau.setOnAction(event -> {
            generateParking();
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
        Menu menuQuit = new Menu("Quit");
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(event -> {
            Alert confirm = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Êtes vous sûr de vouloir quitter ?"
            );
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == ButtonType.OK)
                stage.close();
        });

        menuQuit.getItems().addAll(
                quit
        );

        return menuQuit;
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        //add data
        clients.add(new Client("", "Anonyme", ""));
        Client currentClient = clients.iterator().next();

        //create root
        borderPane = new BorderPane();

        //create top menu
        MenuBar menu = createMenu();
        VBox vBox = new VBox();
        vBox.getChildren().add(menu);
        vBox.setPrefHeight(menu.getHeight());
        borderPane.setTop(vBox);
        borderPane.setPrefHeight(vBox.getHeight());

        //generate parking
        Collection<GridPane> parking = generateParking();
        //active floor
        GridPane currentFloor = parking.iterator().next();

        borderPane.setCenter(currentFloor);

        Scene scene = new Scene(
                borderPane,
                600,
                400
        );

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("SWAG Parking");

        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();

    }

}
