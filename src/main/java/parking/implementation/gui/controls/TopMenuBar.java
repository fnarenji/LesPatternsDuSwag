package parking.implementation.gui.controls;

import javafx.scene.control.*;
import javafx.stage.Stage;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.api.business.parking.ParkingManagerSerializer;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;
import parking.api.exceptions.ParkingNotPresentException;
import parking.implementation.business.logistic.simple.SimpleParkingSpotSelector;
import parking.implementation.business.vehicle.Car;
import parking.implementation.business.vehicle.Carrier;
import parking.implementation.business.vehicle.Motorbike;
import parking.implementation.gui.ClientManager;
import parking.implementation.gui.stages.*;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by sknz on 1/17/15.
 * This class is regroup the functions in the top menu, and create the top menu
 */
public class TopMenuBar extends MenuBar {
    private Stage primaryStage;
    private Parking currentParking;
    private ParkingGrid parkingGrid;
    private  ButtonSpot tmpButton;

    public TopMenuBar(Stage primaryStage, ParkingGrid parkingGrid) {
        this.primaryStage = primaryStage;
        this.parkingGrid = parkingGrid;
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
            if(!clientStage.getClient().getFirstName().equals(""))
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
        MenuItem modifyParkingMenuItem = new MenuItem("Modifier le parking");
        MenuItem changeParkingMenuItem = new MenuItem("Changer de parking");

        modifyParkingMenuItem.setOnAction(event -> {
            NewParkingStage newParkingStage = new NewParkingStage(primaryStage, currentParking);
            newParkingStage.showAndWait();
            parkingChangeListener.accept(newParkingStage.getParking());
        });

        changeParkingMenuItem.setOnAction(event -> {
            ChangeParkingStage changeParkingStage = new ChangeParkingStage(primaryStage);
            changeParkingStage.showAndWait();
            Parking parking = changeParkingStage.getChoice();
            parkingChangeListener.accept(parking);
        });

        menuParking.getItems().addAll(modifyParkingMenuItem, changeParkingMenuItem);

        return menuParking;
    }

    private Menu createMenuSelector() {
        Menu menuSelector = new Menu("AutoSelector");
        MenuItem find = new MenuItem("Find a place");
        MenuItem undo = new MenuItem("Unselect place");
        
        find.setOnAction(event ->{
            
            AutoSelectorStage autoSelectorStage = new AutoSelectorStage(primaryStage);
            autoSelectorStage.showAndWait();
            
            Vehicle vehicle = null;
            
            switch (autoSelectorStage.getVehicleType()){
                case "Moto":
                    vehicle = new Motorbike();
                    break;
                case "Voiture":
                    vehicle = new Car();
                    break;
                case "Camion":
                    vehicle = new Carrier();
                    break;
                default:
                    break;
            }
            
            ParkingSpot parkingSpot = null;
            
            try {
                parkingSpot = currentParking.findAvailableParkingSpotForVehicle(vehicle);
                tmpButton = parkingGrid.highlightButton(parkingSpot.getId());
                tmpButton.setStyle("-fx-background-color: #00ccff");
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Pas de place disponible ou type non défini.").show();
            }
            
        });

        menuSelector.getItems().addAll(find, undo);

        undo.setOnAction(event ->{
            tmpButton.setStyle("-fx-background-color: #60ff05");
        });
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

    private Consumer<Parking> parkingChangeListener;
    public void setOnChangeParking(Consumer<Parking> parkingChangeListener) {
        this.parkingChangeListener = parkingChangeListener;
    }

    public void observeParkingChange(Parking parking) {
        if (currentParking != null)
            System.out.println("MENUOLDPRK " + currentParking.getId() + " " + currentParking.getName());
        else System.out.println("MENUOLDPRK NULL");

        currentParking = parking;
        System.out.println("MENUNEWPRK " + currentParking.getId() + " " + currentParking.getName());
    }

    private Menu createFileMenu(){
        Menu fileMenu = new Menu("File");

        MenuItem save = new MenuItem("Save");
        save.setOnAction(event -> {
            ParkingManagerSerializer.serialize();
            new Alert(Alert.AlertType.INFORMATION, "Parking sauvegardé !").show();
        });

        fileMenu.getItems().addAll(save);

        return fileMenu;
    }
}
