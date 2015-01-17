package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import parking.implementation.Client;

import java.util.Collection;

/**
 * Created by loick on 14/01/15.
 */
public class ClientListStage extends Stage {

    private Collection<Client> clients;

    private Label title;
    private Label label;
    private ChoiceBox select;
    private Button create;
    private Button submit;
    private Button cancel;

    private void createTitle() {
        title = new Label("Select Client");
        title.setFont(Font.font("Arial", 30));
        title.setTextFill(Color.BLACK);
        title.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createLabel() {
        label = new Label("Il n'y a pas de clients.");
        label.setTextFill(Color.RED);
        label.setAlignment(Pos.CENTER);
    }

    private void createSelect() {
        select = new ChoiceBox();
        if (!clients.isEmpty()) {
            select.getItems().setAll(this.clients);
        }
    }

    private void updateState() {
        if (clients.isEmpty()) {
            this.select.setVisible(false);
            this.submit.setVisible(false);
            this.label.setVisible(true);
        } else {
            this.select.setVisible(true);
            this.submit.setVisible(true);
            this.label.setVisible(false);

            select.getItems().setAll(clients);
        }
    }

    private void createButtonCreate() {
        create = new Button();
        create.setText("New Client");

        //add action
        create.setOnAction(event -> {
            ClientStage clientStage = new ClientStage(this);
            clientStage.showAndWait();

            Client newClient = clientStage.getClient();

            if (newClient != null)
                clients.add(newClient);

            updateState();
        });

        //style
        create.setStyle("-fx-background-color: blue");
        create.setTextFill(Color.WHITE);
    }

    private void createButtonSubmit() {
        submit = new Button();
        submit.setText("Select");

        //add action
        submit.setOnAction(event -> {
            this.close();
        });

        //style
        submit.setStyle("-fx-background-color: green");
        submit.setTextFill(Color.WHITE);
    }

    private void createButtonCancel() {
        cancel = new Button();
        cancel.setText("Cancel");

        //add action
        cancel.setOnAction(event -> {
            this.close();
        });

        //style
        cancel.setStyle("-fx-background-color: red");
        cancel.setTextFill(Color.WHITE);
    }

    private void init() {
        createTitle();
        createLabel();
        createSelect();
        createButtonSubmit();
        createButtonCreate();
        createButtonCancel();
    }

    public ClientListStage(Window owner, Collection<Client> clients) {
        this.initOwner(owner);
        this.clients = clients;

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                title,
                select,
                submit,
                label,
                create,
                cancel
        );

        updateState();

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
        return (Client) this.select.getValue();
    }
}
