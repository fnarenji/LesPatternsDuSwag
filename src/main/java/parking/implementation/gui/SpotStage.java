package parking.implementation.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import parking.api.business.contract.ParkingSpot;

/**
 * Created by loick on 14/01/15.
 */
class SpotStage extends Stage {
    private ParkingSpot parkingSpot;
    private Label title;
    private Label id;
    private Label state;
    private Label booking;
    private Button ok;

    public SpotStage(Window owner, ParkingSpot parkingSpot) {
        this.initOwner(owner);
        this.parkingSpot = parkingSpot;

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                title,
                state,
                booking,
                ok
        );

        flowPane.setMaxSize(200, 400);

        //add FlowPane
        flowPane.alignmentProperty().setValue(Pos.CENTER);
        borderPane.setCenter(flowPane);

        //create scene
        Scene scene = new Scene(borderPane, 300, 200);

        this.setResizable(false);
        this.setScene(scene);
        this.setTitle("Parking Spot");
    }

    private void createTitle() {
        title = new Label("Parking Spot");
        title.setFont(Font.font("Arial", 30));
        title.setTextFill(Color.BLACK);
        title.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createID() {
        id = new Label();

        //set text
        id.setText("ID : " + parkingSpot.getId());

        id.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createState() {
        state = new Label();

        //set text
        if (parkingSpot.isVehicleParked()) {
            state.setText("Place Occupée");
            state.setTextFill(Color.RED);
        } else {
            state.setText("Place Libre");
            state.setTextFill(Color.GREEN);
        }

        state.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createBooking() {
        booking = new Label();

        //set text
        if (parkingSpot.isBooked()) {
            booking.setText("Place Réservée");
            booking.setPrefWidth(200);
            booking.setTextFill(Color.RED);
        } else {
            booking.setText("Place Non Réservée");
            booking.setPrefWidth(200);
            booking.setTextFill(Color.GREEN);
        }

        booking.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createButtonOK() {
        ok = new Button();
        ok.setText("OK");

        //add action
        ok.setOnAction(event -> close());

        //style
        ok.setStyle("-fx-background-color: green");
        ok.setTextFill(Color.WHITE);
    }

    private void init() {
        createTitle();
        createID();
        createState();
        createBooking();

        createButtonOK();
    }
}
