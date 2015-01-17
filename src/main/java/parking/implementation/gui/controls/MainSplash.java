package parking.implementation.gui.controls;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by sknz on 1/17/15.
 */
public class MainSplash extends Stage {
    public static final int EXIT = 0;
    public static final int OPEN = 1;
    public static final int NEW = 2;

    private int result = 0;
    private final VBox vbox;

    public MainSplash(Stage owner) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        initStyle(StageStyle.UTILITY);

        Text title = new Text("LPDS Parking");
        Hyperlink openLink = new Hyperlink ("Open scenario");
        Hyperlink newLink = new Hyperlink ("New scenario");
        Hyperlink exitLink = new Hyperlink ("Exit");

        vbox = new VBox(title, newLink, openLink, exitLink);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        for (Node node : vbox.getChildren())
            vbox.setMargin(node, new Insets(0, 0, 0, 8));

        exitLink.setOnAction(event -> result = 0);
        openLink.setOnAction(event -> result = 1);
        newLink.setOnAction(event -> result = 2);

    }

    public int getResult() {
        return result;
    }
}
