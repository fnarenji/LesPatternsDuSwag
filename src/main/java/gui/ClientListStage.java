package gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by loick on 14/01/15.
 */
public class ClientListStage extends Stage {

    private Collection<Client> clients;
    private Client selected;

    private Label title;
    private ChoiceBox select;
    private Button submit;
    private Button cancel;

    private void createTitle() {
        title = new Label("Select Client");
        title.setFont(Font.font("Arial", 30));
        title.setTextFill(Color.BLACK);
        title.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createSelect() {
        select = new ChoiceBox();
        select.getItems().addAll(this.clients);
        select.setValue(this.clients.iterator().next());
    }

    private void createButtonCreate() {
        submit = new Button();
        submit.setText("Select");

        //add action
        submit.setOnAction(createSubmitEventHandler());

        //style
        submit.setStyle("-fx-background-color: green");
        submit.setTextFill(Color.WHITE);
    }

    private void createButtonCancel() {
        cancel = new Button();
        cancel.setText("Cancel");

        //add action
        cancel.setOnAction(createCancelEventHandler());

        //style
        cancel.setStyle("-fx-background-color: red");
        cancel.setTextFill(Color.WHITE);
    }

    private EventHandler createSubmitEventHandler() {
        return event -> this.close();
    }

    private EventHandler createCancelEventHandler() {
        return event -> this.close();
    }

    private void init() {

        createTitle();
        createSelect();
        createButtonCreate();
        createButtonCancel();
    }

    public ClientListStage(Window owner) {
        this.initOwner(owner);

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                title,
                select,
                submit,
                cancel
        );

        flowPane.setMaxSize(200, 400);

        //add FlowPane
        flowPane.alignmentProperty().setValue(Pos.CENTER);
        borderPane.setCenter(flowPane);

        //create scene
        Scene scene = new Scene(borderPane, 300, 200);

        this.setResizable(false);
        this.setScene(scene);
        this.setTitle("Select Client");
    }

    public Client getClient() {
        return this.selected;
    }
}
