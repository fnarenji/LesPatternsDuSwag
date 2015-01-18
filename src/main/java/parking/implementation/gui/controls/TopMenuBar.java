package parking.implementation.gui.controls;

import javafx.scene.control.*;
import javafx.stage.Stage;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.api.exceptions.ParkingNotPresentException;
import parking.implementation.gui.stages.ChangeParkingStage;
import parking.implementation.gui.stages.ClientListStage;
import parking.implementation.gui.ClientManager;
import parking.implementation.gui.stages.ClientStage;
import parking.implementation.gui.stages.NewParkingStage;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by sknz on 1/17/15.
 * This class is about the functions in the top menu
 */
public class TopMenuBar extends MenuBar {
    private Stage primaryStage;
    private Parking currentParking;

    public TopMenuBar(Stage primaryStage) {
        this.primaryStage = primaryStage;
        getMenus().addAll(createMenuClient(), createMenuParking(), createMenuSelector(), createMenuQuit());
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

        menuSelector.getItems().addAll(find, undo);

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
        this.currentParking = parking;
    }
}
