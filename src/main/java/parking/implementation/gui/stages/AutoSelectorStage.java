package parking.implementation.gui.stages;

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
import javafx.util.StringConverter;
import parking.api.business.Utils;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;
import parking.implementation.business.Client;
import parking.implementation.business.vehicle.Car;
import parking.implementation.business.vehicle.Carrier;
import parking.implementation.business.vehicle.Motorbike;
import parking.implementation.gui.ClientManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by loic on 18/01/15.
 *
 * This create a choice box with the 3 type of vehicles, then find the best spot for the selected vehicle
 */
public class AutoSelectorStage extends Stage {

    private Collection<Class<? extends Vehicle>> vehicles = new ArrayList<>();
    
    private ChoiceBox<Class<? extends Vehicle>> vehicleChoiceBox;
    
    private Button submitButton;
    private Label title;

    private ChoiceBox<Client> clientChoiceBox;

    private void createTitle() {
        title = new Label("Select type of vehicle");
        title.setFont(Font.font("Arial", 30));
        title.setTextFill(Color.BLACK);
        title.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createChoiceBoxVehicle() {
        vehicleChoiceBox = new ChoiceBox<>();
        vehicleChoiceBox.setConverter(new StringConverter<Class<? extends Vehicle>>() {
            @Override
            public String toString(Class<? extends Vehicle> object) {
                return object.getSimpleName().replace("ParkingSpot", "");
            }

            @Override
            public Class<? extends Vehicle> fromString(String string) {
                try {
                    Class type = Class.forName(string + "ParkingSpot");
                    if (ParkingSpot.class.isAssignableFrom(type))
                        return Utils.<Class<? extends Vehicle>>uncheckedCast(type);
                } catch (ClassNotFoundException e) {
                    return null;
                }

                return null;
            }
        });

        vehicleChoiceBox.getItems().addAll(vehicles);
        vehicleChoiceBox.setValue(vehicles.iterator().next());
    }
    
    private void createChoiceBoxClient() {
        clientChoiceBox = new ChoiceBox<>();
        for (Client client : ClientManager.getInstance()) {
            clientChoiceBox.getItems().add(client);
        }
    }

    private void createButtonCreate() {
        submitButton = new Button();
        submitButton.setText("Create");

        //add action
        submitButton.setOnAction(event-> this.close());

        //style
        submitButton.setStyle("-fx-background-color: green");
        submitButton.setTextFill(Color.WHITE);
    }
    
    private void init(){
        vehicles.add(Car.class);
        vehicles.add(Motorbike.class);
        vehicles.add(Carrier.class);

        createButtonCreate();
        createChoiceBoxVehicle();
        createChoiceBoxClient();
        createTitle();
    }

    public AutoSelectorStage(Window owner) {
        this.initOwner(owner);

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        // add Nodes to FlowPane
        flowPane.getChildren().addAll(title, vehicleChoiceBox, clientChoiceBox, submitButton);

        // add FlowPane
        flowPane.alignmentProperty().setValue(Pos.CENTER);
        borderPane.setCenter(flowPane);

        // create scene
        Scene scene = new Scene(borderPane);

        setResizable(false);
        setScene(scene);
        setTitle("AutoSelector");
    }
    
    public Class<? extends Vehicle> getVehicleType(){
        return vehicleChoiceBox.getValue();
    }
    
    public Client getClient() { return clientChoiceBox.getValue(); }
    
}
