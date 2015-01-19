package parking.implementation.gui.stages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import parking.api.business.parkingspot.ParkingSpot;

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

        createButtonOK();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        //add Nodes to FlowPane
        flowPane.getChildren().addAll(titleLabel, stateLabel, bookingLabel, okButton);

        //add FlowPane
        flowPane.alignmentProperty().setValue(Pos.CENTER);
        borderPane.setCenter(flowPane);

        //create scene
        Scene scene = new Scene(borderPane);

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
            stateLabel.setText("Place Occupée");
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
