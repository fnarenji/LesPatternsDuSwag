package parking.implementation.gui.controls;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;
import org.joda.time.DateTime;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.exceptions.*;
import parking.implementation.business.Client;
import parking.implementation.gui.ClientManager;
import parking.implementation.gui.stages.ClientListStage;
import parking.implementation.gui.stages.SpotStage;
import parking.implementation.gui.stages.VehicleStage;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.business.parkingspot.CarrierParkingSpot;

import java.util.*;

/**
 * Created by on 14/01/15.
 * This class cares about the button in the GUI
 */
public class ButtonSpot extends MenuButton {
    private static Map<Class<? extends ParkingSpot>, String> colors = new HashMap<>();

    static {
        colors.put(CarParkingSpot.class, "#60FF05");
        colors.put(CarrierParkingSpot.class, "#0E4FFF");
        colors.put(ParkingSpot.class, "yellow");
    }

    private ParkingSpot parkingSpot;
    private String type;

    private DateTime dateTimeEnd = null;
    private Client client = null;

    private Collection<Client> clientCollection;
    
    private Window parent;
    private MenuItem park;
    private MenuItem book;
    private MenuItem infos;

    public ButtonSpot(ParkingSpot parkingSpot, Window parent) {
        super(parkingSpot.getId().toString());
        this.parkingSpot = parkingSpot;
        this.parent = parent;

        setStyle("-fx-background-color: " + colors.get(parkingSpot.getClass()));
        setMinSize(60, 50);
        setMaxSize(60, 50);
        setPrefSize(60, 50);

        createPark();
        createBook();
        createInfos();
        createClientCollection();

        parkingSpot.registerObserver(observable -> updateState());

        getItems().addAll(park, book, infos);
    }

    ButtonSpot(String text, Node graphic) {
        super(text, graphic);
    }

    private void setAvailable() {
        this.setStyle("-fx-background-color: " + colors.get(type));
        this.park.setText("Park");
    }

    private void setAvailableBook() {
        this.setStyle("-fx-background-color: " + colors.get(type));
        this.book.setText("Book");
    }

    private void setBooked() {
        this.setStyle("-fx-background-color: #fcff00");
        this.book.setText("Unbook");
    }
    
    private void setBusy() {
        this.setStyle("-fx-background-color: #ff0030");
        this.park.setText("Unpark");
    }

    private void createClientCollection(){
        clientCollection = new ArrayList<>();
        Iterator<Client> iteratorClient = ClientManager.getInstance().iterator();
        while (iteratorClient.hasNext()){
            clientCollection.add(iteratorClient.next());
        }
    }

    private void updateState() {
        if (this.parkingSpot.isVehicleParked() && !parkingSpot.getVehicle().getBrand().equals(""))
            this.setBusy();
        else if (this.parkingSpot.isBooked())
            this.setBooked();
        else if (!this.parkingSpot.isVehicleParked() && park.getText().equals("Unpark"))
            this.setAvailable();
        else
            this.setAvailableBook();
    }

    private void createPark() {
        this.park = new MenuItem("Park");
        this.park.setOnAction(event -> {
            VehicleStage parkStage = null;
            try {
                if (this.park.getText().equalsIgnoreCase("park")) {
                    parkStage = new VehicleStage(parent);
                    parkStage.showAndWait();
                    parkingSpot.park(parkStage.getVehicle());
                } else if (this.park.getText().equalsIgnoreCase("unpark")) {
                    parkingSpot.unpark();

                    Alert alert = new Alert(
                            Alert.AlertType.INFORMATION,
                            "Place libérée."
                    );
                    alert.show();

                    if(client != null){
                        long diffInMillis =  dateTimeEnd.getMillis() - DateTime.now().getMillis();
                        parkingSpot.book(client,new DateTime(DateTime.now().plusMillis((int)diffInMillis)));
                        this.park.setText("Park");
                    }
                }

                updateState();

            } catch (SpotNotEmptyException e1) {
                Alert alert = new Alert(
                        Alert.AlertType.ERROR,
                        "La place est déjà prise."
                );
                alert.show();
            } catch (SpotBookedException e1) {
                    if(parkStage.getClient().equals((Client)parkingSpot.getCurrentBooking().getOwner())){
                        try {
                            parkingSpot.unbook();
                            parkingSpot.park(parkStage.getVehicle());
                            updateState();
                        } catch (SpotNotEmptyException e) {
                            e.printStackTrace();
                        } catch (SpotBookedException e) {
                            e.printStackTrace();
                        } catch (VehicleNotFitException e) {
                            e.printStackTrace();
                        } catch (UnknownVehicleException e) {
                            e.printStackTrace();
                        } catch (SpotNotBookedException e) {
                            e.printStackTrace();
                        }
                        Alert alert = new Alert(
                                Alert.AlertType.INFORMATION,
                                "Vehicule garrée."
                        );
                        alert.show();
                    }
                    else {
                        Alert alert = new Alert(
                                Alert.AlertType.ERROR,
                                "Place reservée."
                        );
                        alert.show();
                    }
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
                    ClientListStage clientListStage = new ClientListStage(parent);
                    clientListStage.showAndWait();
                    if (clientListStage.getClient() != null){
                        dateTimeEnd = new DateTime(DateTime.now().plusDays(clientListStage.getDuration()));
                        client = clientListStage.getClient();
                        parkingSpot.book(clientListStage.getClient(),new DateTime(DateTime.now().plusDays(clientListStage.getDuration())));
                    }
                } else if (this.book.getText().equalsIgnoreCase("unbook")) {
                    this.parkingSpot.unbook();
                    dateTimeEnd = null;
                    client = null;

                    Alert alert = new Alert(
                            Alert.AlertType.INFORMATION,
                            "Place libérée."
                    );
                    alert.show();
                }

                updateState();

            } catch (SpotNotEmptyException e) {
                Alert alert = new Alert(
                        Alert.AlertType.ERROR,
                        "Place deja occupée."
                );
                alert.show();
            } catch (SpotBookedException e) {
                Alert alert = new Alert(
                        Alert.AlertType.ERROR,
                        "Place déjà réservée."
                );
                alert.show();
            } catch (SpotNotBookedException e) {
                Alert alert = new Alert(
                        Alert.AlertType.ERROR,
                        "Place non réservée."
                );
                alert.show();
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

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }
}
