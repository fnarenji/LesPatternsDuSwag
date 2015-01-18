package parking.implementation.gui.stages;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.dialog.Dialogs;
import parking.api.business.invoices.Invoice;
import parking.api.business.invoices.InvoiceExporter;
import parking.api.business.parkingspot.ParkingSpot;
import parking.implementation.business.invoices.HTMLExporter;
import parking.implementation.business.invoices.SFTPUploader;
import parking.implementation.business.invoices.languages.EnglishExporter;
import parking.implementation.business.invoices.languages.FrenchExporter;

/**
 * Created by loic on 18/01/15.
 *
 * This class groups the functions required to create bills
 */
public class InvoiceStage extends Stage {
    private ParkingSpot parkingSpot;
    private Invoice invoice;
    
    private Label title;
    private Label price;
    private Label invoiceNumber;
    private Label info;
    private Button ok;

    private RadioButton radioButtonTypeChoiceHTML;
    private RadioButton radioButtonTypeChoiceMarkdown;
    private CheckBox uploadCheckBox;
    private ToggleGroup typeChoiceGroup;
    private Label choice;

    private RadioButton radioButtonLanguageEnglish;
    private RadioButton radioButtonLanguageFrench;
    private ToggleGroup languageChoiceGroup;
    private Label languageChoiceLabel;

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
                choice,
                radioButtonTypeChoiceHTML,
                radioButtonTypeChoiceMarkdown,
                uploadCheckBox,
                languageChoiceLabel,
                radioButtonLanguageEnglish,
                radioButtonLanguageFrench,
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
        createExportChoice();
        languageChoice();
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

    private void createExportChoice() {

        choice = new Label();

        choice.setText("Selectionner le type d'export");

        typeChoiceGroup = new ToggleGroup();

        radioButtonTypeChoiceHTML = new RadioButton("HTML");
        radioButtonTypeChoiceHTML.setToggleGroup(typeChoiceGroup);
        radioButtonTypeChoiceHTML.setSelected(true);


        radioButtonTypeChoiceMarkdown = new RadioButton("Markdown");
        radioButtonTypeChoiceMarkdown.setToggleGroup(typeChoiceGroup);

        uploadCheckBox = new CheckBox("Envoyer sur Internet");

        choice.alignmentProperty().setValue(Pos.CENTER);
    }
    private void createButtonOK() {
        ok = new Button();
        ok.setText("Payer");

        //add action
        ok.setOnAction(event -> export());

        //style
        ok.setStyle("-fx-background-color: green");
        ok.setTextFill(Color.WHITE);
    }

    private void languageChoice() {
        languageChoiceLabel = new Label();
        languageChoiceLabel.setText("Selectionner le type d'export");


        languageChoiceGroup = new ToggleGroup();

        radioButtonLanguageFrench = new RadioButton("Français");
        radioButtonLanguageFrench .setToggleGroup(languageChoiceGroup);
        radioButtonLanguageFrench.setSelected(true);


        radioButtonLanguageEnglish = new RadioButton("Anglais");
        radioButtonLanguageEnglish.setToggleGroup(languageChoiceGroup);

        languageChoiceLabel.alignmentProperty().setValue(Pos.CENTER);

    }

    private void export() {
        InvoiceExporter invoiceExporter;

        if(languageChoiceGroup.getSelectedToggle() == radioButtonLanguageFrench){
           invoiceExporter = new FrenchExporter(invoice);
        } else {
            invoiceExporter = new EnglishExporter(invoice);
        }

        if(typeChoiceGroup.getSelectedToggle() == radioButtonTypeChoiceHTML){
            invoiceExporter = new HTMLExporter(invoiceExporter);
        }

        if(uploadCheckBox.isSelected()) {
            SFTPUploader sftpUploader = new SFTPUploader(invoiceExporter);
            String url = sftpUploader.export();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upload effectué");
            alert.setHeaderText("Votre facture a bien été envoyé");
            alert.setContentText("URL : " + url);

            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Facture générée");
            alert.setHeaderText("Votre facture a été générée");

            String content = invoiceExporter.export();


            Label label = new Label("Voici votre facture : ");

            TextArea textArea = new TextArea(content);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setExpandableContent(expContent);

            alert.showAndWait();
        }


        close();
    }

}
