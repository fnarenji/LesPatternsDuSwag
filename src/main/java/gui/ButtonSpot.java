package gui;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;
import parking.api.business.contract.ParkingSpot;
import parking.api.exceptions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by on 14/01/15.
 */
class ButtonSpot extends MenuButton {

    private Map<String, String> colors = new HashMap<>();
    private String defaultColor = "yellow";

    private ParkingSpot parkingSpot;
    private String type;

    private Window parent;
    private MenuItem park;
    private MenuItem book;
    private MenuItem infos;

    public ButtonSpot(ParkingSpot parkingSpot, String type, Window parent) {
        super(parkingSpot.getId().toString());
        this.parkingSpot = parkingSpot;
        this.type = type;
        this.parent = parent;

        this.colors.put("Car", "#60ff05");
        this.colors.put("Carrier", "#0e4fff");

        this.setStyle("-fx-background-color: " + this.colors.getOrDefault(this.type, this.defaultColor));
        this.setMinSize(60, 50);
        this.setMaxSize(60, 50);
        this.setPrefSize(60, 50);

        createPark();
        createBook();
        createInfos();

        this.getItems().addAll(
                this.park,
                this.book,
                this.infos
        );
    }

    private void setAvailable() {
        this.setStyle("-fx-background-color: " + this.colors.getOrDefault(this.type, this.defaultColor));
        this.park.setText("Park");
    }

    private void setBooked() {
        this.setStyle("-fx-background-color: #ff0030");
        this.book.setText("Unbook");
    }

    private void setBusy() {
        this.setStyle("-fx-background-color: #ff0030");
        this.park.setText("Unpark");
    }

    private void updateState() {
        if (this.parkingSpot.isVehicleParked())
            this.setBusy();
        else if (this.parkingSpot.isBooked())
            this.setBooked();
        else
            this.setAvailable();
    }

    private void createPark() {
        this.park = new MenuItem("Park");
        this.park.setOnAction(event -> {
            try {
                if (this.park.getText().equalsIgnoreCase("park")) {
                    VehicleStage parkStage = new VehicleStage(this.parent);
                    parkStage.showAndWait();
                    parkingSpot.park(parkStage.getVehicle());
                } else if (this.park.getText().equalsIgnoreCase("unpark")) {
                    parkingSpot.unpark();

                    Alert alert = new Alert(
                            Alert.AlertType.INFORMATION,
                            "Place libérée."
                    );
                    alert.show();
                }

                updateState();

            } catch (SpotNotEmptyException e1) {
                Alert alert = new Alert(
                        Alert.AlertType.ERROR,
                        "La place est déjà prise."
                );
                alert.show();
            } catch (SpotBookedException e1) {
                Alert alert = new Alert(
                        Alert.AlertType.ERROR,
                        "Place reservée."
                );
                alert.show();
            } catch (UnknownVehicleException e1) {
                Alert alert = new Alert(
                        Alert.AlertType.ERROR,
                        "Véhicule inconnu."
                );
                alert.show();
            } catch (VehicleNotFitException e1) {
                Alert alert = new Alert(
                        Alert.AlertType.ERROR,
                        "Place incorrecte."
                );
                alert.show();
            }
        });
    }

    private void createBook() {
        this.book = new MenuItem("Book");
        book.setOnAction(event -> {
            try {
                if (this.book.getText().equalsIgnoreCase("book")) {

                } else if (this.book.getText().equalsIgnoreCase("unbook")) {
                    this.parkingSpot.unbook();
                }
            } catch (SpotNotEmptyException e) {
                e.printStackTrace();
            } catch (SpotNotBookedException e) {
                e.printStackTrace();
            }
        });
    }

    private void createInfos() {
        this.infos = new MenuItem("Infos");
        this.infos.setOnAction(event -> {
            SpotStage spotStage = new SpotStage(this.parent, this.parkingSpot);
            spotStage.show();
        });
    }

    private ButtonSpot(String text) {
        super(text);
    }

    ButtonSpot(String text, Node graphic) {
        super(text, graphic);
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }
}
