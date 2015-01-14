package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import parking.api.business.concrete.Parking;
import parking.api.business.concrete.ParkingManager;
import parking.api.business.contract.ParkingSpot;
import parking.api.business.contract.ParkingSpotFactory;
import parking.api.business.contract.ParkingSpotIdProvider;
import parking.api.exceptions.ParkingExistsException;
import parking.api.exceptions.ParkingNotPresentException;
import parking.implementation.CarParkingSpot;
import parking.implementation.SimpleParkingSpotIdProvider;

import java.awt.event.MouseEvent;
import java.util.*;

//Created by on 30/12/14.

public class ParkingGui extends Application {


    private  int countP = 0;
    private Collection<ButtonSpot> currentsButtonSpot = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) throws Exception {



        int SIZE = 10;
        int length = SIZE;
        int width = SIZE;

        GridPane gridPane = new GridPane();

        int count = 0;
        MenuButton[][] matrix = new MenuButton[width][length];


        BorderPane root = new BorderPane();
        VBox topContainer = new VBox();  //Creates a container to hold all Menu Objects.
        MenuBar mainMenu = new MenuBar();  //Creates our main menu to hold our Sub-Menus.
        ToolBar toolBar = new ToolBar();  //Creates our tool-bar to hold the buttons.

        topContainer.getChildren().add(mainMenu);
        topContainer.getChildren().add(toolBar);
        root.setTop(topContainer);
        root.setCenter(gridPane);

        Menu client = new Menu("Client");
        MenuItem newCl = new MenuItem("Nouveau");

        Menu conf = new Menu("Parking");
        MenuItem changePark = new MenuItem("Nouveau");

        Menu help = new Menu("Help");

        changePark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Optional<String> response = Dialogs.create()
                        .owner(primaryStage)
                        .title("Création du parking")
                        .message("Entrer la taille du parking:")
                        .showTextInput("");

                //GET VALUE OF RESPONSE
                if (response.isPresent()) {

                    ParkingManager parkingManager = ParkingManager.getInstance();
                    try {
                        parkingManager.newParking(countP++,"Default");
                    } catch (ParkingExistsException e1) {
                        e1.printStackTrace();
                    }

                    class SpotFactory implements ParkingSpotFactory{

                        ParkingSpotIdProvider IdProvide = new SimpleParkingSpotIdProvider();

                        @Override
                        public void setIdProvider(ParkingSpotIdProvider provider) {
                            IdProvide = provider;
                        }

                        @Override
                        public ParkingSpot createParkingSpot() {
                            return new CarParkingSpot(IdProvide.nextId());
                        }
                    }

                    Collection<ParkingSpot> currentsSpot = null;
                    try {
                        currentsSpot = parkingManager.getParkingById(0).newParkingSpot(new SpotFactory(), Integer.parseInt(response.get()));
                    } catch (ParkingNotPresentException e1) {
                        e1.printStackTrace();
                    }
                    for(Iterator<ParkingSpot> i = currentsSpot.iterator();i.hasNext();){
                        currentsButtonSpot.add(new ButtonSpot(i.next()));
                    }

                    Iterator<ButtonSpot> tmp = currentsButtonSpot.iterator();

                    ColumnConstraints column = new ColumnConstraints();
                    column.setPercentWidth(50);
                    RowConstraints row = new RowConstraints();
                    row.setPercentHeight(50);
                    gridPane.getRowConstraints().add(row);
                    gridPane.getColumnConstraints().add(column);

                    for(int x = 0; x < length; x++)
                    {
                        gridPane.getRowConstraints().add(row);
                        for(int y = 0; y < width; y++)
                        {
                            gridPane.getColumnConstraints().add(column);

                            if(!tmp.hasNext()) break;
                            matrix[x][y] = tmp.next();
                            matrix[x][y].setMinSize(60,60);
                            matrix[x][y].setTextAlignment(TextAlignment.CENTER);
                            matrix[x][y].setStyle("-fx-background-color: #60ff05");
                            MenuButton lel = matrix[x][y];
                            MenuItem park = new MenuItem("Park");
                           // MenuItem park = new MenuItem("Park");
                            MenuItem book = new MenuItem("Book");
                            MenuItem info = new MenuItem("Info");
                            lel.getItems().addAll(park,book,info);
                            gridPane.add(matrix[x][y], y, x);;
                        }
                    }

                }

            }
        });

        client.getItems().add(newCl);
        conf.getItems().add(changePark);

        mainMenu.getMenus().addAll(client, conf, help);

        Scene sc = new Scene(root, 500, 500);

        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
        primaryStage.setTitle("SWAG-- GUI");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
