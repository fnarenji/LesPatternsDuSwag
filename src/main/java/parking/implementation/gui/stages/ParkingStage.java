package parking.implementation.gui.stages;

import javafx.event.ActionEvent;
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
public class ParkingStage extends Stage {

    Label title;
    Label label;
    Button construct;
    Button cancel;
    TextField nbTruck;
    TextField nbCar;

    public ParkingStage(Window owner) {
        this.initOwner(owner);

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();
        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                title,
                nbCar,
                nbTruck,
                construct,
                cancel
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

    private void createTitle() {
        title = new Label("New Parking");
        title.setFont(Font.font("Arial", 30));
        title.setTextFill(Color.BLACK);
        title.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createTextFieldPlate() {
        nbCar = new TextField();
        nbCar.setPromptText("Place for car");

        //style
        nbCar.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createTextFieldBrand() {
        nbTruck = new TextField();
        nbTruck.setPromptText("Place for truck");

        //style
        nbTruck.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createLabel() {
        label = new Label();
        label.setTextFill(Color.RED);
        label.alignmentProperty().setValue(Pos.CENTER);
    }

    private EventHandler<ActionEvent> createSubmitEventHandler() {
        return event -> {
            if (!nbCar.getText().isEmpty()
                    && !nbTruck.getText().isEmpty()
                    ) {
                this.close();
            } else {
                label.setText("Tous les champs ne sont pas renseignés");
            }
        };
    }

    private void createConstructButton() {
        construct = new Button();
        construct.setText("Create");

        //add action
        construct.setOnAction(createSubmitEventHandler());

        //style
        construct.setStyle("-fx-background-color: green");
        construct.setTextFill(Color.WHITE);
    }

    private void createButtonCancel() {
        cancel = new Button();
        cancel.setText("Cancel");

        //add action
        cancel.setOnAction(event -> this.close());

        //style
        cancel.setStyle("-fx-background-color: red");
        cancel.setTextFill(Color.WHITE);
    }

    private void init() {

        createTitle();
        createLabel();

        createTextFieldPlate();
        createTextFieldBrand();
        createConstructButton();
        createButtonCancel();
    }

    public int getNbTruck() {
        if (nbTruck.getText().equalsIgnoreCase(""))
            return 0;
        return Integer.parseInt(nbTruck.getText());
    }

    public int getNbCar() {
        if (nbCar.getText().equalsIgnoreCase(""))
            return 0;
        return Integer.parseInt(nbCar.getText());
    }

}