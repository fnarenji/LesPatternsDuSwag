package parking.implementation.gui.controls;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by sknz on 1/17/15.
 */
public class MainSplash extends Stage {
    public static final int EXIT = 0;
    public static final int OPEN = 1;
    public static final int NEW = 2;

    private int result = 0;
    private final VBox vbox;

    public MainSplash() {
        Text title = new Text("LPDS Parking");
        Hyperlink openLink = new Hyperlink ("Open scenario");
        Hyperlink newLink = new Hyperlink ("New scenario");
        Hyperlink exitLink = new Hyperlink ("Exit");

        vbox = new VBox(newLink, openLink, exitLink);


        vbox.setMargin(openLink, new Insets(0, 0, 0, 8));
        vbox.setMargin(newLink, new Insets(0, 0, 0, 8));
        vbox.setMargin(exitLink, new Insets(0, 0, 0, 8));

        exitLink.setOnAction(event -> result = 0);
        openLink.setOnAction(event -> result = 1);
        newLink.setOnAction(event -> result = 2);
    }

    public void run(Stage primaryStage) {
        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public int getResult() {
        return result;
    }
}
