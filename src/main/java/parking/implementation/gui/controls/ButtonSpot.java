package parking.implementation.gui.controls;

import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;
import org.joda.time.DateTime;
import parking.api.business.invoices.Invoice;
import parking.api.business.invoices.InvoiceStrategy;
import parking.api.business.parkingspot.ParkingSpot;
import parking.api.exceptions.SpotBookedException;
import parking.api.exceptions.SpotNotBookedException;
import parking.api.exceptions.SpotNotEmptyException;
import parking.api.exceptions.VehicleNotFitException;
import parking.implementation.business.Client;
import parking.implementation.business.logistic.simple.SimpleInvoiceStrategy;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.business.parkingspot.CarrierParkingSpot;
import parking.implementation.gui.ClientManager;
import parking.implementation.gui.stages.BookStage;
import parking.implementation.gui.stages.InvoiceStage;
import parking.implementation.gui.stages.SpotStage;
import parking.implementation.gui.stages.VehicleStage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by on 14/01/15.
 * This class cares about the button in the GUI
 */
public class ButtonSpot extends MenuButton {
    private static Map<Class<? extends ParkingSpot>, String> colors = new HashMap<>();

    static {
        colors.put(CarParkingSpot.class, "#2FEB8A");
        colors.put(CarrierParkingSpot.class, "#61D8F2");
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
        //setPrefSize(80, 60);

        createPark();
        createBook();
        createInfos();
        createClientCollection();

        parkingSpot.registerObserver(observable -> updateState());
        updateState();

        getItems().addAll(park, book, infos);
    }

    private void setAvailable() {
        this.setStyle("-fx-background-color: " + colors.get(parkingSpot.getClass()));
        this.park.setText("Park");
    }

    private void setAvailableBook() {
        this.setStyle("-fx-background-color: " + colors.get(parkingSpot.getClass()));
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

    private void createClientCollection() {
        clientCollection = new ArrayList<>();
        for (Client client1 : ClientManager.getInstance()) {
            clientCollection.add(client1);
        }
    }

    private void updateState() {
        if (parkingSpot.isVehicleParked())
            setBusy();
        else if (parkingSpot.isBooked())
            setBooked();
        else if (!parkingSpot.isVehicleParked())
            setAvailable();
        else
            setAvailableBook();
    }

    private void createPark() {
        this.park = new MenuItem("Park");
        this.park.setOnAction(event -> {
            VehicleStage vehicleStage = null;
            try {
                if (!parkingSpot.isVehicleParked()) {
                    vehicleStage = new VehicleStage(parent);
                    vehicleStage.showAndWait();

                    if (vehicleStage.getVehicle() != null)
                        parkingSpot.park(vehicleStage.getVehicle());
                } else if (parkingSpot.isVehicleParked()) {
                    if (client == null) {
                        InvoiceStrategy invoiceStrategy = new SimpleInvoiceStrategy(5);
                        Invoice invoice = invoiceStrategy.computeInvoice(parkingSpot.getVehicle(), parkingSpot);
                        InvoiceStage invoiceStage = new InvoiceStage(parent, parkingSpot, invoice);
                        invoiceStage.showAndWait();
                    }

                    parkingSpot.unpark();
                    new Alert(Alert.AlertType.INFORMATION, "Place libérée.").show();

                    if (client != null) {
                        long diffInMillis = dateTimeEnd.getMillis() - DateTime.now().getMillis();
                        parkingSpot.book(client, new DateTime(DateTime.now().plusMillis((int) diffInMillis)));
                        park.setText("Park");
                    }
                }

                updateState();

            } catch (SpotNotEmptyException e1) {
                new Alert(Alert.AlertType.ERROR, "La place est déjà prise.").show();
            } catch (SpotBookedException e1) {
                if (vehicleStage.getClient().equals(parkingSpot.getCurrentBooking().getOwner())) {
                    try {
                        parkingSpot.unbook();
                        parkingSpot.park(vehicleStage.getVehicle());
                        updateState();
                    } catch (SpotNotEmptyException | SpotBookedException | SpotNotBookedException | VehicleNotFitException e) {
                        e.printStackTrace();
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Vehicule garrée.").show();
                } else {
                    Alert alert = new Alert(
                            Alert.AlertType.ERROR,
                            "Place reservée."
                    );
                    alert.show();
                }
            } catch (VehicleNotFitException e1) {
                new Alert(Alert.AlertType.ERROR, "Place incorrecte.").show();
            }
        });
    }

    private void createBook() {
        this.book = new MenuItem("Book");
        book.setOnAction(event -> {
            try {
                if (!parkingSpot.isBooked()) {
                    BookStage bookStage = new BookStage(parent, false);
                    bookStage.showAndWait();
                    if (bookStage.getClient() != null) {
                        dateTimeEnd = bookStage.getDuration();
                        client = bookStage.getClient();
                        parkingSpot.book(bookStage.getClient(), dateTimeEnd);
                    }
                } else if (parkingSpot.isBooked()) {
                    if (client != null) {
                        SimpleInvoiceStrategy invoiceStrategy = new SimpleInvoiceStrategy(5);
                        Invoice invoice = invoiceStrategy.computeInvoice(parkingSpot.getVehicle(), parkingSpot);
                        InvoiceStage invoiceStage = new InvoiceStage(parent, parkingSpot, invoice);
                        invoiceStage.showAndWait();
                    }

                    parkingSpot.unbook();
                    dateTimeEnd = null;
                    client = null;

                    new Alert(Alert.AlertType.INFORMATION, "Place libérée").show();
                }

                updateState();

            } catch (SpotNotEmptyException e) {
                new Alert(Alert.AlertType.ERROR, "Place deja occupée.").show();
            } catch (SpotBookedException e) {
                new Alert(Alert.AlertType.ERROR, "Place déjà réservée.").show();
            } catch (SpotNotBookedException e) {
                new Alert(Alert.AlertType.ERROR, "Place non réservée.").show();
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

    public void highlight() {
        setStyle("-fx-background-color: #4D22A3");
    }
}
