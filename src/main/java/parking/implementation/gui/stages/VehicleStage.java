package parking.implementation.gui.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import parking.implementation.gui.ClientManager;
import parking.implementation.gui.MainApplication;

/**
 * Created by loick on 14/01/15.
 * <p>
 * Enter information when park a vehicle
 */
public class VehicleStage extends Stage {
    private Label titleLabel;
    private Label label;
    private ChoiceBox<Client> clientChoiceBox;
    private ChoiceBox<Class<? extends Vehicle>> vehicleChoiceBox;
    private TextField plateField;
    private TextField brandField;
    private TextField modelField;
    private Button submitButton;
    private Button cancelButton;
    private Boolean cancelled = false;

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
                clientChoiceBox,
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

    private void createClientChoiceBox() {
        clientChoiceBox = new ChoiceBox<>();
        if (ClientManager.getInstance().count() != 0) {
            for (Client client : ClientManager.getInstance())
                clientChoiceBox.getItems().add(client);
        }

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
        vehicleChoiceBox.getItems().addAll(MainApplication.VehicleTypes);
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
        cancelButton.setOnAction(event -> {
            cancelled = true;
            close();
        });

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

    private void init() {
        createTitle();
        createLabel();
        createChoiceBoxVehicle();
        createTextFieldPlate();
        createTextFieldBrand();
        createTextFieldModel();
        createButtonCreate();
        createButtonCancel();
        createClientChoiceBox();
    }

    public Vehicle getVehicle() {
        if (vehicleChoiceBox.getValue() == null
                || plateField.getText() == null
                || brandField.getText() == null
                || modelField.getText() == null)
            return null;


        Vehicle vehicle = null;
        try {
            vehicle = vehicleChoiceBox.getValue().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            new Alert(Alert.AlertType.ERROR, e.toString());
        }

        assert vehicle != null;
        vehicle.setPlate(plateField.getText());
        vehicle.setBrand(brandField.getText());
        vehicle.setModel(brandField.getText());

        return vehicle;
    }

    public Client getClient() {
        return clientChoiceBox.getValue();
    }

    public Boolean getCancelled() {
        return cancelled;
    }
}
