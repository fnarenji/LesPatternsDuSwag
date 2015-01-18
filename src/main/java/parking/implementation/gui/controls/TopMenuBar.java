package parking.implementation.gui.controls;

import javafx.scene.control.*;
import javafx.stage.Stage;
import parking.api.business.parking.ParkingManager;
import parking.api.business.parking.ParkingManagerSerializer;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;
import parking.api.exceptions.ParkingNotPresentException;
import parking.implementation.business.logistic.simple.SimpleParkingSpotSelector;
import parking.implementation.business.vehicle.Car;
import parking.implementation.business.vehicle.Carrier;
import parking.implementation.business.vehicle.Motorbike;
import parking.implementation.gui.stages.AutoSelectorStage;
import parking.implementation.gui.stages.ClientListStage;
import parking.implementation.gui.ClientManager;
import parking.implementation.gui.stages.ClientStage;
import parking.implementation.gui.stages.ParkingListStage;

import java.util.Optional;

/**
 * Created by sknz on 1/17/15.
 * This class is about the functions in the top menu
 */
public class TopMenuBar extends MenuBar {
    private Stage primaryStage;
    private ParkingGrid parkingGrid;

    public TopMenuBar(Stage primaryStage, ParkingGrid parkingGrid) {
        this.primaryStage = primaryStage;
        getMenus().addAll(createFileMenu(), createMenuClient(), createMenuParking(), createMenuSelector(), createMenuQuit());
    }

    private Menu createMenuClient() {
        Menu menuClient = new Menu("Client");
        MenuItem list = new MenuItem("Selectionner");
        list.setOnAction(event -> {
            ClientListStage clientListStage = new ClientListStage(primaryStage);
            clientListStage.showAndWait();
        });

        MenuItem nouveau = new MenuItem("Nouveau");
        nouveau.setOnAction(event -> {
            ClientStage clientStage = new ClientStage(primaryStage);
            clientStage.showAndWait();
            ClientManager.getInstance().addClient(clientStage.getClient());
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
            ParkingListStage parkingListStage = new ParkingListStage(primaryStage);
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
        
        find.setOnAction(event ->{
            AutoSelectorStage autoSelectorStage = new AutoSelectorStage(primaryStage);
            autoSelectorStage.showAndWait();
            SimpleParkingSpotSelector simpleParkingSpotSelector = new SimpleParkingSpotSelector();
            Vehicle tmp = null;
            switch (autoSelectorStage.getVehicleType()){
                case "Moto":
                    tmp = new Motorbike();
                    break;
                case "Voiture":
                    tmp = new Car();
                    break;
                default:
                    tmp = new Carrier();
                    break;
            }
            System.out.println(tmp.getClass());
            ParkingSpot parkingSpot = null;
            try {
                System.out.println(ParkingManager.getInstance().getParkingById(1).getSpots());
                parkingSpot = simpleParkingSpotSelector.select(tmp, ParkingManager.getInstance().getParkingById(1).getSpots());
            } catch (ParkingNotPresentException e) {
                e.printStackTrace();
            }
            ButtonSpot tmpButton = (ButtonSpot) parkingGrid.getButtonSpotMap().get(parkingSpot.getId());
            tmpButton.setStyle("-fx-background-color: #00ccff");
        });

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
                primaryStage.close();
        });

        menuQuit.setGraphic(quitLabel);

        return menuQuit;
    }

    private Menu createFileMenu(){
        Menu fileMenu = new Menu("File");

        MenuItem save = new MenuItem("Save");
        save.setOnAction(event -> {
            ParkingManagerSerializer.serialize();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Parking sauvegardé");
            alert.show();
        });

        fileMenu.getItems().addAll(save);

        return fileMenu;
    }
}
