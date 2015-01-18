package parking.implementation.gui.stages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.StringConverter;
import parking.api.business.parking.Parking;
import parking.api.business.parking.ParkingManager;
import parking.api.exceptions.ParkingNotPresentException;

import java.awt.*;

/**
 * Created by loick on 14/01/15.
 *
 * Change the current parking
 */
public class ChangeParkingStage extends Stage {
    ChoiceBox<Parking> parkingNumberChoiceBox;

    public ChangeParkingStage(Window owner) {
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UTILITY);

        Text windowTitle = createWindowTitleNode();
        Button okButton = createOkButton();
        Button newParkingButton = createNewParkingButton();
        parkingNumberChoiceBox = createParkingNumberChoiceBox();

        refreshChoices();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(parkingNumberChoiceBox, 0, 0);
        gridPane.add(newParkingButton, 1, 0);

        for (Node node : gridPane.getChildren())
            GridPane.setMargin(node, new Insets(0, 0, 0, 8));

        VBox vBox = new VBox(windowTitle, gridPane, okButton);
        vBox.setAlignment(Pos.CENTER);

        for (Node node : vBox.getChildren())
            VBox.setMargin(node, new Insets(4, 8, 4, 8));

        Scene scene = new Scene(vBox);

        setResizable(false);
        setScene(scene);
        sizeToScene();
        setTitle("Select Parking");
    }

    private Text createWindowTitleNode() {
        Text windowTitle = new Text("Select Parking");

        windowTitle.setFont(Font.font("Arial", 30));

        return windowTitle;
    }

    private Button createOkButton() {
        Button okButton = new Button("Done !");

        okButton.setOnAction(event -> this.close());
        okButton.setStyle("-fx-background-color: royalblue");
        okButton.setTextFill(Color.WHITE);

        return okButton;
    }

    private Button createNewParkingButton() {
        Button newParkingButton = new Button("Nouveau parking");

        newParkingButton.setOnAction(event -> {
            NewParkingStage newParkingStage = new NewParkingStage(this);
            newParkingStage.showAndWait();

            refreshChoices();
        });

        return newParkingButton;
    }

    private void refreshChoices() {
        parkingNumberChoiceBox.getItems().clear();
        ParkingManager.getInstance().forEach(parking -> parkingNumberChoiceBox.getItems().add(parking));
    }

    public Parking getChoice() {
        return parkingNumberChoiceBox.getValue();
    }

    private ChoiceBox<Parking> createParkingNumberChoiceBox() {
        ChoiceBox<Parking> parkingNumberChoiceBox = new ChoiceBox<>();
        parkingNumberChoiceBox.setPrefWidth(150);

        parkingNumberChoiceBox.setConverter(new StringConverter<Parking>() {
            @Override
            public String toString(Parking object) {
                return "(" + object.getId() + ") " + object.getName();
            }

            @Override
            public Parking fromString(String string) {
                Integer id = Integer.valueOf(string.substring(1, string.indexOf(')')));

                try {
                    return ParkingManager.getInstance().getParkingById(id);
                } catch (ParkingNotPresentException e) {
                    new Alert(Alert.AlertType.ERROR, "Vous avez sélectionné un parking inexistant. \n" + e);
                }
                return null;
            }
        });

        return parkingNumberChoiceBox;
    }
}