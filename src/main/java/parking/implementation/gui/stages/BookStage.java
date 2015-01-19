package parking.implementation.gui.stages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import org.joda.time.DateTime;
import parking.api.business.Utils;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.business.vehicle.Vehicle;
import parking.implementation.business.Client;
import parking.implementation.business.vehicle.Car;
import parking.implementation.gui.ClientManager;
import parking.implementation.gui.MainApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by loic on 18/01/15.
 * <p>
 * This class groups all the functions to create a booking
 */
public class BookStage extends Stage {
    private Collection<Client> clients = new ArrayList<>();

    private Label titleLabel;
    private Label label;
    private ChoiceBox<Client> clientChoiceBox;
    private DatePicker picker;
    private Button createButton;
    private Button submitButton;
    private Button cancelButton;
    private CheckBox checkBox;
    private ChoiceBox<Class<? extends Vehicle>> vehicleChoiceBox;
    private Boolean cancelled = false;

    public BookStage(Window owner, Boolean askVehicle) {
        this.initOwner(owner);

        if (askVehicle) {
            createChoiceBoxVehicle();
            vehicleChoiceBox.getSelectionModel().select(Car.class);
        }

        createTitle();
        createLabel();
        createSelect();
        createButtonSubmit();
        createButtonCreate();
        createButtonCancel();
        createDatePicker();
        createCheckBox();

        VBox vBoxCenter = new VBox(new HBox(clientChoiceBox, createButton), checkBox, picker);
        if (askVehicle)
            vBoxCenter.getChildren().add(vehicleChoiceBox);

        HBox.setMargin(clientChoiceBox, new Insets(8));
        HBox.setMargin(createButton, new Insets(8));
        VBox.setMargin(clientChoiceBox, new Insets(8));
        VBox.setMargin(checkBox, new Insets(8));
        VBox.setMargin(picker, new Insets(8));
        vBoxCenter.setAlignment(Pos.CENTER);

        HBox hBoxBottom = new HBox(submitButton, cancelButton);
        HBox.setMargin(submitButton, new Insets(8));
        HBox.setMargin(cancelButton, new Insets(8));
        hBoxBottom.setAlignment(Pos.CENTER);

        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setTop(titleLabel);
        pane.setCenter(vBoxCenter);
        pane.setBottom(hBoxBottom);

        updateState();

        //createButton scene
        Scene scene = new Scene(pane);

        setResizable(false);
        sizeToScene();
        setScene(scene);
        setTitle("Select Client");
    }

    private void createTitle() {
        titleLabel = new Label("Select Client");
        titleLabel.setFont(Font.font("Arial", 30));
        titleLabel.setTextFill(Color.BLACK);
        titleLabel.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createLabel() {
        label = new Label("Il n'y a pas de clients.");
        label.setTextFill(Color.RED);
        label.setAlignment(Pos.CENTER);
    }

    private void createDatePicker() {
        picker = new DatePicker(LocalDate.now());
    }

    private void createSelect() {
        clientChoiceBox = new ChoiceBox<>();
        if (ClientManager.getInstance().count() != 0) {
            Iterator<Client> clientIterator = ClientManager.getInstance().iterator();
            while (clientIterator.hasNext()) {
                Client tmp = clientIterator.next();
                clientChoiceBox.getItems().add(tmp);
                clients.add(tmp);
            }
        }

    }

    private void updateState() {
        if (clients.isEmpty()) {
            clientChoiceBox.setVisible(false);
            submitButton.setVisible(false);
            label.setVisible(true);
        } else {
            clientChoiceBox.setVisible(true);
            submitButton.setVisible(true);
            label.setVisible(false);

            clientChoiceBox.getItems().setAll(clients);
        }
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

    private void createButtonCreate() {
        createButton = new Button();
        createButton.setText("New Client");

        //add action
        createButton.setOnAction(event -> {
            ClientStage clientStage = new ClientStage(this);
            clientStage.showAndWait();

            Client newClient = clientStage.getClient();

            if (newClient != null)
                clients.add(newClient);

            updateState();
        });

        //style
        createButton.setStyle("-fx-background-color: blue");
        createButton.setTextFill(Color.WHITE);
    }

    private void createCheckBox() {
        checkBox = new CheckBox("Infini");
        checkBox.setSelected(false);
        checkBox.selectedProperty().addListener(event -> picker.setDisable(checkBox.isSelected()));
    }

    private void createButtonSubmit() {
        submitButton = new Button();
        submitButton.setText("Select");

        //add action
        submitButton.setOnAction(event -> this.close());

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
            this.close();
        });

        //style
        cancelButton.setStyle("-fx-background-color: red");
        cancelButton.setTextFill(Color.WHITE);
    }

    public Client getClient() {
        return this.clientChoiceBox.getValue();
    }

    public DateTime getDuration() {
        return checkBox.isSelected() ? null : new org.joda.time.LocalDate(picker.getValue()).toDateTimeAtCurrentTime();
    }

    public Class<? extends Vehicle> getVehicleType() {
        return vehicleChoiceBox.getValue();
    }

    public Boolean getCancelled() {
        return cancelled;
    }
}

