package parking.implementation.gui.controls;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by sknz on 1/17/15.
 */
public class MainSplash extends Stage {
    public static final int EXIT = 0;
    public static final int OPEN = 1;
    public static final int NEW = 2;

    private int result = 0;
    private final BorderPane pane;

    public MainSplash() {
        Button openButton = new Button("Open scenario");
        Button newButton = new Button("New scenario");
        Button exitButton = new Button("Exit");

        openButton.setPrefWidth(120);
        newButton.setPrefWidth(120);
        exitButton.setPrefWidth(120);

        exitButton.setOnAction(event -> result = 0);
        openButton.setOnAction(event -> result = 1);
        newButton.setOnAction(event -> result = 2);

        VBox vbox = new VBox(newButton, openButton, exitButton);

        pane = new BorderPane();
        pane.setCenter(vbox);
    }

    public void run(Stage primaryStage) {
        Scene scene = new Scene(pane, 120, 0);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public int getResult() {
        return result;
    }
}
