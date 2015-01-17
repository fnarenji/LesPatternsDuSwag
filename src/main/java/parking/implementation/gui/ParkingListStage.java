package parking.implementation.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import parking.api.business.concrete.Parking;
import parking.api.business.concrete.ParkingManager;
import parking.api.exceptions.ParkingExistsException;
import parking.api.exceptions.ParkingNotPresentException;
import parking.implementation.logic.Client;
import parking.implementation.logic.ParkingSpotFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by loick on 14/01/15.
 */
public class ParkingListStage extends Stage {

    private Label title;
    private ChoiceBox select;
    private Button ok;

    private void createTitle() {
        title = new Label("Select Parking");
        title.setFont(Font.font("Arial", 30));
        title.setTextFill(Color.BLACK);
        title.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createSelect() {
        select = new ChoiceBox();

        ParkingManager.getInstance().forEach(parking -> {
                    select.getItems().add(parking.getId());
                }
        );
    }

    private void createButtonOK() {
        ok = new Button();
        ok.setText("OK");

        //add action
        ok.setOnAction(event -> {
            this.close();
        });

        //style
        ok.setStyle("-fx-background-color: royalblue");
        ok.setTextFill(Color.WHITE);
    }

    private void init() {
        createTitle();
        createSelect();
        createButtonOK();
    }

    public ParkingListStage(Window owner) {
        this.initOwner(owner);

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                title,
                select,
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
        this.setTitle("Select Parking");
    }

    public Object getChoice() {
        return select.getValue();
    }
}