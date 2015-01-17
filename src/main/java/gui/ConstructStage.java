package gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by  on 15/01/15.
 */
class ConstructStage extends Stage {
    private Label titleLabel;
    private Label label;
    private Button constructButton;
    private Button cancelButton;
    private TextField truckNumberField;
    private TextField carNumberField;

    private void createTitle() {
        titleLabel = new Label("New Parking");
        titleLabel.setFont(Font.font("Arial", 30));
        titleLabel.setTextFill(Color.BLACK);
        titleLabel.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createTextFieldPlate() {
        carNumberField = new TextField();
        carNumberField.setPromptText("Place for car");

        //style
        carNumberField.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createTextFieldBrand() {
        truckNumberField = new TextField();
        truckNumberField.setPromptText("Place for truck");

        //style
        truckNumberField.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createLabel() {
        label = new Label();
        label.setTextFill(Color.RED);
        label.alignmentProperty().setValue(Pos.CENTER);
    }
    private void createConstructButton() {
        constructButton = new Button();
        constructButton.setText("Create");

        //add action
        constructButton.setOnAction(event -> {
            if (!carNumberField.getText().isEmpty() && !truckNumberField.getText().isEmpty())
                close();
            else
                label.setText("Tous les champs ne sont pas renseignés");
        });

        //style
        constructButton.setStyle("-fx-background-color: green");
        constructButton.setTextFill(Color.WHITE);
    }

    private EventHandler createCancelEventHandler() {
        return event -> this.close();
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


    private void init() {

        createTitle();
        createLabel();

        createTextFieldPlate();
        createTextFieldBrand();
        createConstructButton();
        createButtonCancel();
    }

    public ConstructStage(Window owner) {
        this.initOwner(owner);

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();
        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                titleLabel,
                carNumberField,
                truckNumberField,
                constructButton,
                cancelButton
        );

        flowPane.setMaxSize(200, 400);

        //add FlowPane
        flowPane.alignmentProperty().setValue(Pos.CENTER);
        borderPane.setCenter(flowPane);
        //add Label error
        borderPane.setBottom(label);
        borderPane.setAlignment(label, Pos.CENTER);


        //create scene
        Scene scene = new Scene(borderPane, 300, 200);

        this.setResizable(false);
        this.setScene(scene);
        this.setTitle("New Parking");
    }

    public int getTruckNumberField() {
        if (truckNumberField.getText().equalsIgnoreCase(""))
            return 0;
        return Integer.parseInt(truckNumberField.getText());
    }

    public int getCarNumberField() {
        if (carNumberField.getText().equalsIgnoreCase(""))
            return 0;
        return Integer.parseInt(carNumberField.getText());
    }

}
