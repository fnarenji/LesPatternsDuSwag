package parking.implementation.gui.controls;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * Created by sknz on 1/17/15.
 */
public class StartSplashStage extends Stage {
    public static final int EXIT = 0;
    public static final int OPEN = 1;
    public static final int NEW = 2;

    private int result = 0;


    public StartSplashStage(Window owner) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        initStyle(StageStyle.UTILITY);

        Text windowTitle = new Text("LPDS Parking");
        Hyperlink openLink = new Hyperlink ("Open scenario");
        Hyperlink newLink = new Hyperlink ("New scenario");
        Hyperlink exitLink = new Hyperlink ("Exit");

        windowTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        exitLink.setOnAction(event -> setResult(0));
        openLink.setOnAction(event -> setResult(1));
        newLink.setOnAction(event -> setResult(2));

        VBox vbox = new VBox(windowTitle, newLink, openLink, exitLink);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        for (Node node : vbox.getChildren())
            VBox.setMargin(node, new Insets(0, 0, 0, 8));

        setScene(new Scene(vbox));
        sizeToScene();
    }

    public int getResult() {
        return result;
    }

    private void setResult(int result) {
        this.result = result;
        hide();
    }
}
