package parking.implementation.gui.stages;

import javafx.event.ActionEvent;
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
import parking.implementation.logic.Client;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by loick on 14/01/15.
 */
public class ClientStage extends Stage {

    private Collection<String> civility_list = new ArrayList<>();

    private Label title;
    private Label label;
    private ChoiceBox<String> civility;
    private TextField lastname;
    private TextField firstname;
    private Button submit;
    private Button cancel;

    public ClientStage(Window owner) {
        this.initOwner(owner);

        init();

        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();

        //add Nodes to FlowPane
        flowPane.getChildren().addAll(
                title,
                civility,
                lastname,
                firstname,
                submit,
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
        this.setTitle("New Client");
    }

    private void createTitle() {
        title = new Label("New Client");
        title.setFont(Font.font("Arial", 30));
        title.setTextFill(Color.BLACK);
        title.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createLabel() {
        label = new Label();
        label.setTextFill(Color.RED);
        label.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createCivilite() {
        civility = new ChoiceBox<>();
        civility.getItems().addAll(this.civility_list);
        civility.setValue(this.civility_list.iterator().next());
    }

    private void createLastName() {
        lastname = new TextField();
        lastname.setPromptText("Last Name");

        //style
        lastname.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createFirstName() {
        firstname = new TextField();
        firstname.setPromptText("First Name");

        //style
        firstname.alignmentProperty().setValue(Pos.CENTER);
    }

    private void createButtonCreate() {
        submit = new Button();
        submit.setText("Create");

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
        cancel.setOnAction(event -> close());

        //style
        cancel.setStyle("-fx-background-color: red");
        cancel.setTextFill(Color.WHITE);
    }

    private EventHandler<ActionEvent> createSubmitEventHandler() {
        return event -> {
            if (!lastname.getText().isEmpty()
                    && !firstname.getText().isEmpty()
                    ) {
                this.close();
            } else {
                label.setText("Tous les champs ne sont pas renseignés");
            }
        };
    }

    private void init() {
        this.civility_list.add("M.");
        this.civility_list.add("Mme");
        this.civility_list.add("Mlle");

        createTitle();
        createLabel();
        createCivilite();
        createLastName();
        createFirstName();
        createButtonCreate();
        createButtonCancel();
    }

    public Client getClient() {
        if (civility.getValue() == null
                || lastname.getText() == null
                || firstname.getText() == null)
            return null;
        return new Client(
                civility.getValue().toString(),
                lastname.getText(),
                firstname.getText()
        );
    }
}
