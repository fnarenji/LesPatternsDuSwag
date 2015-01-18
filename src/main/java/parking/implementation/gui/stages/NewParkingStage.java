package parking.implementation.gui.stages;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.api.business.parkingspot.ParkingSpot;
import parking.implementation.business.logistic.floor.FloorParkingSpotIdProvider;
import parking.implementation.business.logistic.simple.SimpleParkingSpotSelector;
import parking.implementation.gui.controls.ParkingFloorTableView;
import parking.implementation.gui.controls.ParkingTableViewRow;

/**
 * Created by sknz on 1/18/15.
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

        parkingName = parkingName != null ? parking.getName() : "Mon premier parking";

        Text titleLabel = new Text("Titre du parking");
        TextField titleField = new TextField(parkingName);
        titleField.setOnAction(event -> parkingName = titleField.getText());
        titleField.setMaxWidth(Double.MAX_VALUE);
        Button newTableLine = new Button("Nouvelle entrée");
        Button removeTableLine = new Button("Suppr. séléction");

        newTableLine.setOnAction(event -> parkingFloorTableView.addNewLine());
        removeTableLine.setOnAction(event -> parkingFloorTableView.removeSelectedLine());

        GridPane pane = new GridPane();
        pane.add(titleLabel, 0, 0);
        pane.add(titleField, 1, 0);
        pane.add(newTableLine, 0, 1);
        pane.add(removeTableLine, 1, 1);

        for (Node node : pane.getChildren())
            GridPane.setMargin(node, new Insets(4, 8, 4, 8));

        Text windowTitle = new Text("Création/édition de parking");
        windowTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        VBox.setMargin(windowTitle, new Insets(10));

        VBox vBox = new VBox(windowTitle, pane, parkingFloorTableView);
        vBox.setAlignment(Pos.CENTER);

        setScene(new Scene(vBox));
        sizeToScene();
    }

    private void readFromParking(Parking parking) {
        ObservableList<ParkingTableViewRow> items = parkingFloorTableView.getItems();

        int previousParkingSpotFloor = -1;
        Class<? extends ParkingSpot> previousParkingSpotType = null;

        for (ParkingSpot parkingSpot : parking) {
            int floor = FloorParkingSpotIdProvider.ExtractFloor(parkingSpot.getId());
            if (floor == previousParkingSpotFloor) {
                if (parkingSpot.getClass().equals(previousParkingSpotType)) {
                    ParkingTableViewRow row = items.get(items.size() - 1);
                    row.setQuantity(row.getQuantity() + 1);
                }
            }

            items.add(new ParkingTableViewRow(floor, 1, parkingSpot.getClass(), true));

            previousParkingSpotFloor = floor;
            previousParkingSpotType = parkingSpot.getClass();
        }
    }

    public void applyChanges() {
        if (parking != null) {
            parking.setName(getParkingName());
        }
        else {
            parking = ParkingManager.getInstance().newParking(getParkingName());
            parking.setParkingSpotSelector(new SimpleParkingSpotSelector());
        }
    }

    public String getParkingName() {
        return parkingName != null && !parkingName.isEmpty() ? parkingName : "Mon premier parking";
    }
}
