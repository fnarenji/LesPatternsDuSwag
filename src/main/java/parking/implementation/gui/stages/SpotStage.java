package parking.implementation.gui.stages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;
import parking.implementation.business.Client;

/**
 * Created by loick on 14/01/15.
 * <p>
 * Give information about a spot (reserved or parked)
 */
public class SpotStage extends Stage {
    private ParkingSpot parkingSpot;
    private Label titleLabel;
    private Label idLabel;
    private Label stateLabel;
    private Label bookingLabel;
    private Label clientLabel;
    private Button okButton;

    public SpotStage(Window owner, ParkingSpot parkingSpot) {
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UTILITY);
        this.parkingSpot = parkingSpot;

        createTitle();
        createID();
        createState();
        createBooking();
        createClient();
        createButtonOK();

        VBox vBox = new VBox(titleLabel, stateLabel, bookingLabel, clientLabel, okButton);
        for (Node node : vBox.getChildren())
            VBox.setMargin(node, new Insets(8));
        vBox.alignmentProperty().setValue(Pos.CENTER);

        //create scene
        Scene scene = new Scene(vBox);

        sizeToScene();
        this.setResizable(false);
        this.setScene(scene);
        this.setTitle("Parking Spot");
    }

    private void createTitle() {
        titleLabel = new Label("Parking Spot");
        titleLabel.setFont(Font.font("Arial", 30));
        titleLabel.setTextFill(Color.BLACK);
        titleLabel.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createID() {
        idLabel = new Label();

        //set text
        idLabel.setText("ID : " + parkingSpot.getId());

        idLabel.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createState() {
        stateLabel = new Label();

        //set text
        if (parkingSpot.isVehicleParked()) {
            Vehicle vehicle = parkingSpot.getVehicle();
            stateLabel.setText(String.format("Place Occupée par %s %s (%s)", vehicle.getBrand(), vehicle.getModel(), vehicle.getPlate()));
            stateLabel.setTextFill(Color.RED);
        } else {
            stateLabel.setText("Place Libre");
            stateLabel.setTextFill(Color.GREEN);
        }

        stateLabel.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createBooking() {
        bookingLabel = new Label();

        //set text
        if (parkingSpot.isBooked()) {
            bookingLabel.setText("Place Réservée");
            bookingLabel.setPrefWidth(200);
            bookingLabel.setTextFill(Color.RED);
        } else {
            bookingLabel.setText("Place Non Réservée");
            bookingLabel.setPrefWidth(200);
            bookingLabel.setTextFill(Color.GREEN);
        }

        bookingLabel.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createClient() {
        clientLabel = new Label();

        Client c = null;
        if (parkingSpot.isBooked()) c = (Client) parkingSpot.getCurrentBooking().getOwner();
        else if (parkingSpot.isVehicleParked()) c = (Client) parkingSpot.getVehicle().getOwner();

        clientLabel = new Label(c == null ? "Pas de client" : String.format("%s %s %s", c.getCivility(), c.getFirstName(), c.getLastName()));
    }

    private void createButtonOK() {
        okButton = new Button();
        okButton.setText("OK");

        //add action
        okButton.setOnAction(event -> close());

        //style
        okButton.setStyle("-fx-background-color: green");
        okButton.setTextFill(Color.WHITE);
    }
}
