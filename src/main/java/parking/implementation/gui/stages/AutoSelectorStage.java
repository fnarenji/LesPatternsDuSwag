package parking.implementation.gui.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.stage.Window;
import parking.api.business.parkingspot.ParkingSpot;
import parking.implementation.gui.controls.ButtonSpot;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by loic on 18/01/15.
 */
public class AutoSelectorStage extends Stage {

    private Collection<String> vehicles = new ArrayList<>();
    
    private ChoiceBox<String> vehicleChoiceBox;
    
    private Button submitButton;
    private Label title;

    private void createTitle() {
        title = new Label("Select type of vehicle");
        title.setFont(Font.font("Arial", 30));
        title.setTextFill(Color.BLACK);
        title.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createChoiceBoxVehicle() {
        vehicleChoiceBox = new ChoiceBox<String>();
        vehicleChoiceBox.getItems().addAll(vehicles);
        vehicleChoiceBox.setValue(vehicles.iterator().next());
    }
    

    private void createButtonCreate() {
        submitButton = new Button();
        submitButton.setText("Create");

        //add action
        submitButton.setOnAction(event->{
            this.close();
        });

        //style
        submitButton.setStyle("-fx-background-color: green");
        submitButton.setTextFill(Color.WHITE);
    }
    
    private void init(){
        vehicles.add("Aucun");
        vehicles.add("Voiture");
        vehicles.add("Moto");
        vehicles.add("Camion");

        createButtonCreate();
        createChoiceBoxVehicle();
        createTitle();
    }

    public AutoSelectorStage(Window owner) {
        this.initOwner(owner);

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                title,
                vehicleChoiceBox,
                submitButton
        );

        flowPane.setMaxSize(200, 400);

        //add FlowPane
        flowPane.alignmentProperty().setValue(Pos.CENTER);
        borderPane.setCenter(flowPane);

        //create scene
        Scene scene = new Scene(borderPane, 300, 200);

        setResizable(false);
        setScene(scene);
        setTitle("AutoSelector");
    }
    
    public String getVehicleType(){
        return vehicleChoiceBox.getValue();
    }
    
}
