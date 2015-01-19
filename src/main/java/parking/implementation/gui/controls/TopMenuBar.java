package parking.implementation.gui.controls;

import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManagerSerializer;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;
import parking.api.exceptions.*;
import parking.implementation.gui.ClientManager;
import parking.implementation.gui.stages.BookStage;
import parking.implementation.gui.stages.ChangeParkingStage;
import parking.implementation.gui.stages.ClientStage;
import parking.implementation.gui.stages.VehicleStage;

import java.io.File;
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
    private ButtonSpot tmpButton;

    public TopMenuBar(Stage primaryStage, ParkingGrid parkingGrid) {
        this.primaryStage = primaryStage;
        this.parkingGrid = parkingGrid;
        getMenus().addAll(createFileMenu(), createMenuClient(), createMenuParking(), createMenuSelector(), createMenuQuit());
    }

    private Menu createMenuClient() {
        Menu menuClient = new Menu("Client");

        MenuItem nouveau = new MenuItem("Nouveau");
        nouveau.setOnAction(event -> {
            ClientStage clientStage = new ClientStage(primaryStage);
            clientStage.showAndWait();
            if (!clientStage.getClient().getFirstName().equals(""))
                ClientManager.getInstance().addClient(clientStage.getClient());
        });

        menuClient.getItems().add(nouveau);

        return menuClient;
    }

    private Menu createMenuParking() {
        Menu menuParking = new Menu("Parking");
        MenuItem modifyParkingMenuItem = new MenuItem("Modifier le parking");
        MenuItem changeParkingMenuItem = new MenuItem("Changer de parking");

        modifyParkingMenuItem.setOnAction(event -> {
            new Alert(Alert.AlertType.INFORMATION, "Cette fonctionnalité n'est pas disponible : le code est présent mais n'est pas capable de gérer la totalité des cas possibles.").show();

            /*EditParkingStage editParkingStage = new EditParkingStage(primaryStage, currentParking);
            editParkingStage.showAndWait();
            parkingChangeListener.accept(editParkingStage.getParking());*/
        });

        changeParkingMenuItem.setOnAction(event -> {
            ChangeParkingStage changeParkingStage = new ChangeParkingStage(primaryStage);
            changeParkingStage.showAndWait();
            Parking newParking = changeParkingStage.getChoice();
            if (newParking != null)
                parkingChangeListener.accept(newParking);
        });

        menuParking.getItems().addAll(changeParkingMenuItem, modifyParkingMenuItem);

        return menuParking;
    }

    private Menu createMenuSelector() {
        Menu menuSelector = new Menu("EasySelector");
        MenuItem find = new MenuItem("AutoPark");
        MenuItem book = new MenuItem("AutoBook");

        find.setOnAction(event -> {
            VehicleStage vehicleStage = new VehicleStage(primaryStage);
            vehicleStage.showAndWait();

            if (vehicleStage.getCancelled())
                return;

            Vehicle vehicle = null;
            vehicle = vehicleStage.getVehicle();

            ParkingSpot parkingSpot = null;
            try {
                parkingSpot = currentParking.findAvailableParkingSpotForVehicle(vehicle);
                parkingSpot.park(vehicle);
            } catch (NoSpotAvailableException e) {
                new Alert(Alert.AlertType.ERROR, "Pas de place disponible.").show();
            } catch (ParkingNoVehicleParkingStrategyException e) {
                new Alert(Alert.AlertType.ERROR, "Pas de VehicleParkingStrategy configuré.").show();
            } catch (SpotNotEmptyException | VehicleNotFitException | SpotBookedException e) {
                new Alert(Alert.AlertType.ERROR, "Erreur interne: " + e).show();
            }
        });

        book.setOnAction(event -> {
            BookStage bookStage = new BookStage(primaryStage, true);
            bookStage.showAndWait();

            if (bookStage.getCancelled())
                return;

            Vehicle vehicle = null;
            try {
                vehicle = bookStage.getVehicleType().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                new Alert(Alert.AlertType.ERROR, "Erreur interne: " + e).show();
            }

            ParkingSpot parkingSpot = null;
            try {
                parkingSpot = currentParking.findAvailableParkingSpotForVehicle(vehicle);
                parkingSpot.park(vehicle);
            } catch (NoSpotAvailableException e) {
                new Alert(Alert.AlertType.ERROR, "Pas de place disponible.").show();
            } catch (ParkingNoVehicleParkingStrategyException e) {
                new Alert(Alert.AlertType.ERROR, "Pas de VehicleParkingStrategy configuré.").show();
            } catch (SpotNotEmptyException | VehicleNotFitException | SpotBookedException e) {
                new Alert(Alert.AlertType.ERROR, "Erreur interne: " + e).show();
            }
        });

        menuSelector.getItems().addAll(find, book);
        return menuSelector;
    }

    private Menu createMenuQuit() {
        Menu menuQuit = new Menu();
        Label quitLabel = new Label("Quit");
        quitLabel.setOnMouseClicked(event -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Êtes vous sûr de vouloir quitter ?");
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
        currentParking = parking;
    }

    private Menu createFileMenu() {
        Menu fileMenu = new Menu("File");

        MenuItem save = new MenuItem("Save");
        save.setOnAction(event -> chooseFileToSave());

        fileMenu.getItems().addAll(save);

        return fileMenu;
    }

    private void chooseFileToSave() {
        String path;
        File selectedDirectory = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Selectionner le dossier de sauvegarde");
        alert.setHeaderText("Veuillez selectionner le dossier dans lequel vous souhaitez effectuer la sauvegarde");

        ButtonType buttonTypeOne = new ButtonType("Selectionner");
        ButtonType buttonTypeDefault = new ButtonType("Par défaut");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeDefault);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            selectedDirectory = directoryChooser.showDialog(primaryStage);
            path = selectedDirectory.getAbsolutePath() + "/parkingManager.ser";

        } else {
            path = "save/parkingManager.ser";
        }

        ParkingManagerSerializer.serialize(path);
        new Alert(Alert.AlertType.INFORMATION, "Parking sauvegardé !").show();
    }
}
