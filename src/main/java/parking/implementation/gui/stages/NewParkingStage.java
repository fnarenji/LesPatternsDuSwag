package parking.implementation.gui.stages;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.implementation.business.logistic.simple.SimpleParkingSpotSelector;

/**
 * Created by sknz on 1/18/15.
 */
public class NewParkingStage extends Stage {
    private Parking parking;
    private String parkingName;

    public NewParkingStage(Window owner) {
        this(owner, null);
    }

    public NewParkingStage(Window owner, Parking parking) {
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);

        parseParking(parking);
        parkingName = parkingName != null ? parking.getName() : "Mon premier parking";

        TableView tableView = new TableView();
        TableColumn floorColumn = new TableColumn("Etage");
        TableColumn amountColumn = new TableColumn("Quantité");
        TableColumn typeColumn = new TableColumn("Type");
        tableView.getColumns().addAll(floorColumn, amountColumn, typeColumn);
        tableView.setEditable(true);

        Text titleLabel = new Text("Titre du parking");
        TextField titleField = new TextField(parkingName);
        titleField.setOnAction(event -> parkingName = titleField.getText());
        titleField.setMaxWidth(Double.MAX_VALUE);

        GridPane pane = new GridPane();
        pane.add(titleLabel, 0, 0);
        pane.add(titleField, 1, 0);

        GridPane.setMargin(titleLabel, new Insets(4, 8, 4, 8));
        GridPane.setMargin(titleField, new Insets(4, 8, 4, 8));

        Text windowTitle = new Text("Création/édition de parking");
        windowTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        VBox.setMargin(windowTitle, new Insets(10));

        VBox vBox = new VBox(windowTitle, pane, tableView);
        vBox.setAlignment(Pos.CENTER);

        setScene(new Scene(vBox));
        sizeToScene();
    }

    private void parseParking(Parking parking) {
        this.parking = parking;
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
