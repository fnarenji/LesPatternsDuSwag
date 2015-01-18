package parking.implementation.gui.stages;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingApplicationManager;
import parking.api.business.parkingspot.ParkingSpot;
import parking.implementation.business.logistic.floor.FloorParkingSpotIdProvider;
import parking.implementation.business.logistic.simple.SimpleParkingSpotFactory;
import parking.implementation.business.logistic.simple.SimpleVehicleParkingStrategy;
import parking.implementation.gui.controls.ParkingFloorTableView;
import parking.implementation.gui.controls.ParkingTableViewRow;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sknz on 1/18/15.
 *
 * Form for the creation/edition of parkings
 */
public class NewParkingStage extends Stage {
    private Parking parking;
    private String parkingName;
    private ParkingFloorTableView parkingFloorTableView = new ParkingFloorTableView();

    public NewParkingStage(Window owner) {
        this(owner, null);
    }

    public NewParkingStage(Window owner, Parking parking) {
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);

        this.parking = parking;

        if (parking != null)
            readFromParking(parking);
        else parkingFloorTableView.addNewLine(); // default line

        parkingName = parking != null ? parking.getName() : "Mon premier parking";

        Text parkingTitleLabel = new Text("Titre du parking");
        TextField parkingTitleField = new TextField(parkingName);
        parkingTitleField.textProperty().addListener((observable, oldValue, newValue) -> parkingName = newValue);
        parkingTitleField.setMaxWidth(Double.MAX_VALUE);

        Button newTableLine = new Button("Nouvelle entrée");
        Button removeTableLine = new Button("Suppr. séléction");

        newTableLine.setOnAction(event -> parkingFloorTableView.addNewLine());
        removeTableLine.setOnAction(event -> parkingFloorTableView.removeSelectedLine());

        Button okButton = new Button("Done !");
        okButton.setOnAction(event -> hide());
        okButton.setStyle("-fx-background-color: royalblue");
        okButton.setTextFill(Color.WHITE);

        GridPane pane = new GridPane();
        pane.add(parkingTitleLabel, 0, 0);
        pane.add(parkingTitleField, 1, 0);
        pane.add(newTableLine, 0, 1);
        pane.add(removeTableLine, 1, 1);

        for (Node node : pane.getChildren())
            GridPane.setMargin(node, new Insets(4, 8, 4, 8));

        Text windowTitle = new Text("Création/édition de parking");
        windowTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        VBox.setMargin(windowTitle, new Insets(10));
        VBox.setMargin(okButton, new Insets(10));

        VBox vBox = new VBox(windowTitle, pane, parkingFloorTableView, okButton);
        vBox.setAlignment(Pos.CENTER);

        Platform.runLater(okButton::requestFocus);

        setScene(new Scene(vBox));
        sizeToScene();
    }

    @Override
    public void showAndWait() {
        super.showAndWait();
        applyChanges();
    }

    private void applyChanges() {
        SimpleParkingSpotFactory factory = new SimpleParkingSpotFactory();
        FloorParkingSpotIdProvider spotIdProvider = new FloorParkingSpotIdProvider();
        factory.setIdProvider(spotIdProvider);

        Map<Integer, Integer> countByFloor = new HashMap<>();

        if (parking != null) {
            parking.setName(getParkingName());
        }
        else {
            parking = ParkingApplicationManager.getInstance().newParking(getParkingName());
            SimpleVehicleParkingStrategy vehicleParkingStrategy = new SimpleVehicleParkingStrategy();
            parking.setVehicleParkingStrategy(vehicleParkingStrategy);
            parking.registerObserver(vehicleParkingStrategy::observe);
        }

        parkingFloorTableView.getItems().stream().filter(row -> !row.getLocked()).forEach(row -> {
            Integer currentCount = countByFloor.getOrDefault(row.getFloor(), 0);
            int finalQuantity = Math.min(99 - currentCount, row.getQuantity());
            countByFloor.put(row.getFloor(), finalQuantity);

            if (finalQuantity != 0) {
                factory.setParkingSpotType(row.getParkingSpotType());
                spotIdProvider.setFloor(row.getFloor());
                parking.newParkingSpot(factory, finalQuantity);
            }
        });
    }

    private void readFromParking(Parking parking) {
        ObservableList<ParkingTableViewRow> items = parkingFloorTableView.getItems();

        int previousParkingSpotFloor = -1;
        Class<? extends ParkingSpot> previousParkingSpotType = null;

        for (ParkingSpot parkingSpot : parking) {
            int floor = FloorParkingSpotIdProvider.ExtractFloor(parkingSpot.getId());

            if (floor == previousParkingSpotFloor && parkingSpot.getClass().equals(previousParkingSpotType)) {
                    ParkingTableViewRow row = items.get(items.size() - 1);
                    row.setQuantity(row.getQuantity() + 1);
            }
            else {
                items.add(new ParkingTableViewRow(floor, 1, parkingSpot.getClass(), true));
            }

            previousParkingSpotFloor = floor;
            previousParkingSpotType = parkingSpot.getClass();
        }
    }

    private String getParkingName() {
        return parkingName != null && !parkingName.isEmpty() ? parkingName : "Mon premier parking";
    }

    public Parking getParking() {
        return parking;
    }
}
