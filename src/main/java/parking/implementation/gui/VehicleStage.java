package parking.implementation.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import parking.api.business.contract.Vehicle;
import parking.api.exceptions.UnknownVehicleException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by loick on 14/01/15.
 */
public class VehicleStage extends Stage {

    private Collection<String> vehicles = new ArrayList<>();

    private Label titleLabel;
    private Label label;
    private ChoiceBox<String> vehicleChoiceBox;
    private TextField plateField;
    private TextField brandField;
    private TextField modelField;
    private Button submitButton;
    private Button cancelButton;

    public VehicleStage(Window owner) {
        this.initOwner(owner);

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                titleLabel,
                vehicleChoiceBox,
                plateField,
                brandField,
                modelField,
                submitButton,
                cancelButton
        );

        flowPane.setMaxSize(200, 400);

        //add FlowPane
        flowPane.alignmentProperty().setValue(Pos.CENTER);
        borderPane.setCenter(flowPane);
        //add Label error
        borderPane.setBottom(label);
        BorderPane.setAlignment(label, Pos.CENTER);

        //create scene
        Scene scene = new Scene(borderPane, 300, 200);

        setResizable(false);
        setScene(scene);
        setTitle("New Vehicle");
    }

    private void createTitle() {
        titleLabel = new Label("New Vehicle");
        titleLabel.setFont(Font.font("Arial", 30));
        titleLabel.setTextFill(Color.BLACK);
        titleLabel.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createLabel() {
        label = new Label();
        label.setTextFill(Color.RED);
        label.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createChoiceBoxVehicle() {
        vehicleChoiceBox = new ChoiceBox<String>();
        vehicleChoiceBox.getItems().addAll(vehicles);
        vehicleChoiceBox.setValue(vehicles.iterator().next());
    }

    private void createTextFieldPlate() {
        plateField = new TextField();
        plateField.setPromptText("Plate");

        //style
        plateField.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createTextFieldBrand() {
        brandField = new TextField();
        brandField.setPromptText("Brand");

        //style
        brandField.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createTextFieldModel() {
        modelField = new TextField();
        modelField.setPromptText("Model");

        //style
        modelField.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createButtonCreate() {
        submitButton = new Button();
        submitButton.setText("Create");

        //add action
        submitButton.setOnAction(createSubmitEventHandler());

        //style
        submitButton.setStyle("-fx-background-color: green");
        submitButton.setTextFill(Color.WHITE);
    }

    private void createButtonCancel() {
        cancelButton = new Button();
        cancelButton.setText("Cancel");

        //add action
        cancelButton.setOnAction(createCancelEventHandler());

        //style
        cancelButton.setStyle("-fx-background-color: red");
        cancelButton.setTextFill(Color.WHITE);
    }

    private EventHandler<ActionEvent> createSubmitEventHandler() {
        return event -> {
            if (!plateField.getText().isEmpty()
                    && !brandField.getText().isEmpty()
                    && !modelField.getText().isEmpty()
                    ) {
                close();
            } else {
                label.setText("Tous les champs ne sont pas renseignés");
            }
        };
    }

    private EventHandler<ActionEvent> createCancelEventHandler() {
        return event -> close();
    }

    private void init() {
        vehicles.add("Voiture");
        vehicles.add("Moto");
        vehicles.add("Camion");

        createTitle();
        createLabel();
        createChoiceBoxVehicle();
        createTextFieldPlate();
        createTextFieldBrand();
        createTextFieldModel();
        createButtonCreate();
        createButtonCancel();
    }

    public Vehicle getVehicle() throws UnknownVehicleException {
        if (vehicleChoiceBox.getValue() == null
                || plateField.getText() == null
                || brandField.getText() == null
                || modelField.getText() == null)
            return null;

        VehicleFactory vehicleFactory = new parking.implementation.VehicleFactory();

        Vehicle vehicle = vehicleFactory.createVehicle(vehicleChoiceBox.getValue().toString());
        vehicle.setPlate(plateField.getText());
        vehicle.setBrand(brandField.getText());
        vehicle.setModel(brandField.getText());

        return vehicle;
    }
}
