package parking.implementation.gui;

import javafx.scene.control.*;

import java.util.Optional;

/**
 * Created by sknz on 1/17/15.
 */
public class TopMenuBar extends MenuBar {
    public TopMenuBar() {
        getMenus().addAll(createMenuClient(), createMenuParking(), createMenuSelector(), createMenuQuit());
    }

    private Menu createMenuClient() {
        Menu menuClient = new Menu("Client");
        MenuItem list = new MenuItem("Selectionner");
        list.setOnAction(event -> {
            ClientListStage clientListStage = new ClientListStage(ParkingGUI.getMainStage());
            clientListStage.showAndWait();
        });

        MenuItem nouveau = new MenuItem("Nouveau");
        nouveau.setOnAction(event -> {
            ClientStage clientStage = new ClientStage(ParkingGUI.getMainStage());
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
            ParkingListStage parkingListStage = new ParkingListStage(ParkingGUI.getMainStage());
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
                ParkingGUI.getMainStage().close();
        });

        menuQuit.setGraphic(quitLabel);

        return menuQuit;
    }
}
