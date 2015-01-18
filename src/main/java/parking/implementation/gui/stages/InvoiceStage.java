package parking.implementation.gui.stages;

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
import parking.api.business.invoices.Invoice;
import parking.api.business.parkingspot.ParkingSpot;
import parking.implementation.business.Client;

/**
 * Created by loic on 18/01/15.
 */
public class InvoiceStage extends Stage {
    private ParkingSpot parkingSpot;
    private Invoice invoice;
    
    private Label title;
    private Label price;
    private Label invoiceNumber;
    private Label info;
    private Button ok;

    public InvoiceStage(Window owner, ParkingSpot parkingSpot, Invoice invoice) {
        this.initOwner(owner);
        this.parkingSpot = parkingSpot;
        this.invoice = invoice;

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                title,
                invoiceNumber,
                price,
                info,
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
        this.setTitle("Invoice");
    }
    
    private void init(){
        createTitle();
        createInvoice();
        createInfos();
        createButtonOK();
    }

    private void createTitle() {
        title = new Label("Invoice");
        title.setFont(Font.font("Arial", 50));
        title.setTextFill(Color.BLACK);
        title.alignmentProperty().setValue(Pos.CENTER);
    }
    
    private void createInvoice(){
        price = new Label();
        invoiceNumber = new Label();

        invoiceNumber.setText("Invoice Number : " + invoice.getInvoiceNumber() + '\n');
        price.setText(" - Price : " + invoice.getInvoicePrice() + '\n');
        
        price.alignmentProperty().setValue(Pos.CENTER);
        invoiceNumber.alignmentProperty().setValue(Pos.CENTER);
    }
    
    private void createInfos(){
        info = new Label();
        
        if(parkingSpot.getVehicle() == null)
            info.setText("Information : " + " non disponible " + '\n');
        else
            info.setText("Information : " + parkingSpot.getVehicle() + '\n');

        info.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createButtonOK() {
        ok = new Button();
        ok.setText("Payer");

        //add action
        ok.setOnAction(event -> close());

        //style
        ok.setStyle("-fx-background-color: green");
        ok.setTextFill(Color.WHITE);
    }
    
}
