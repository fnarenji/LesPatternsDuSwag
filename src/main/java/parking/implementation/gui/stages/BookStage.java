package parking.implementation.gui.stages;

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
import parking.implementation.business.Client;
import parking.implementation.gui.ClientManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by loic on 18/01/15.
 *
 * This class groups all the functions to create a booking
 */
public class BookStage extends Stage {
    private Collection<Client> clients = new ArrayList<>();

    private Label titleLabel;
    private Label label;
    private ChoiceBox<Client> clientChoiceBox;
    private ChoiceBox<Integer> durationChoiceBox;
    private Button createButton;
    private Button submitButton;
    private Button cancelButton;

    public BookStage(Window owner) {
        this.initOwner(owner);

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                titleLabel,
                clientChoiceBox,
                durationChoiceBox,
                submitButton,
                label,
                createButton,
                cancelButton
        );

        updateState();

        flowPane.setMaxSize(200, 400);

        //add FlowPane
        flowPane.alignmentProperty().setValue(Pos.CENTER);
        borderPane.setCenter(flowPane);

        //createButton scene
        Scene scene = new Scene(borderPane, 300, 200);

        this.setResizable(false);
        this.setScene(scene);
        this.setTitle("Select Client");
    }

    private void createTitle() {
        titleLabel = new Label("Select Client");
        titleLabel.setFont(Font.font("Arial", 30));
        titleLabel.setTextFill(Color.BLACK);
        titleLabel.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createLabel() {
        label = new Label("Il n'y a pas de clients.");
        label.setTextFill(Color.RED);
        label.setAlignment(Pos.CENTER);
    }

    private void createSelect() {
        clientChoiceBox = new ChoiceBox<>();
        if (ClientManager.getInstance().count() != 0){
            Iterator<Client> clientIterator = ClientManager.getInstance().iterator();
            while (clientIterator.hasNext()){
                Client tmp = clientIterator.next();
                clientChoiceBox.getItems().add(tmp);
                clients.add(tmp);
            }
        }

    }

    private void updateState() {
        if (clients.isEmpty()) {
            clientChoiceBox.setVisible(false);
            submitButton.setVisible(false);
            label.setVisible(true);
        } else {
            clientChoiceBox.setVisible(true);
            submitButton.setVisible(true);
            label.setVisible(false);

            clientChoiceBox.getItems().setAll(clients);
        }
    }

    private void createButtonCreate() {
        createButton = new Button();
        createButton.setText("New Client");

        //add action
        createButton.setOnAction(event -> {
            ClientStage clientStage = new ClientStage(this);
            clientStage.showAndWait();

            Client newClient = clientStage.getClient();

            if (newClient != null)
                clients.add(newClient);

            updateState();
        });

        //style
        createButton.setStyle("-fx-background-color: blue");
        createButton.setTextFill(Color.WHITE);
    }

    private void createButtonSubmit() {
        submitButton = new Button();
        submitButton.setText("Select");

        //add action
        submitButton.setOnAction(event -> {
            this.close();
        });

        //style
        submitButton.setStyle("-fx-background-color: green");
        submitButton.setTextFill(Color.WHITE);
    }

    private void createButtonCancel() {
        cancelButton = new Button();
        cancelButton.setText("Cancel");

        //add action
        cancelButton.setOnAction(event -> {
            this.close();
        });

        //style
        cancelButton.setStyle("-fx-background-color: red");
        cancelButton.setTextFill(Color.WHITE);
    }

    private void createDurationChoixBox() {
        durationChoiceBox = new ChoiceBox<>();
        Collection<Integer> during = new ArrayList<>();
        for (int i = 1; i < 50; i++)
            during.add(new Integer(i));
        durationChoiceBox.getItems().setAll(during);
    }

    private void init() {
        createTitle();
        createLabel();
        createSelect();
        createButtonSubmit();
        createButtonCreate();
        createButtonCancel();
        createDurationChoixBox();
    }

    public Client getClient() {
        return this.clientChoiceBox.getValue();
    }

    public Integer getDuration() {
        return durationChoiceBox.getValue();

    }
}

