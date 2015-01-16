package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import parking.api.business.concrete.ParkingManager;
import parking.api.business.contract.ParkingSpot;
import parking.api.exceptions.*;
import parking.implementation.ParkingSpotFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//Created by on 30/12/14.

public class ParkingGui extends Application {


    final TextField nbCar = new TextField();
    final TextField nbTruck = new TextField();
    private int countP = 0;
    private Collection<ButtonSpot> currentsButtonSpot = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        int SIZE = 10;
        int length = SIZE;
        int width = SIZE;

        GridPane gridPane = new GridPane();

        int count = 0;
        MenuButton[][] matrix = new MenuButton[width][length];


        BorderPane root = new BorderPane();
        VBox topContainer = new VBox();
        MenuBar mainMenu = new MenuBar();
        ToolBar toolBar = new ToolBar();

        topContainer.getChildren().add(mainMenu);
        topContainer.getChildren().add(toolBar);
        root.setTop(topContainer);
        root.setCenter(gridPane);

        Menu client = new Menu("Client");
        MenuItem newCl = new MenuItem("Nouveau");

        Menu conf = new Menu("Parking");
        MenuItem changePark = new MenuItem("Nouveau");

        Menu autoSelector = new Menu("AutoSelector");
        MenuItem find = new MenuItem("Find a place");
        MenuItem undo = new MenuItem("Unselect place");

        Menu quit = new Menu("Quit");
        quit.setOnAction(event -> {
            System.out.println("lol");
            primaryStage.close();
        });

        changePark.setOnAction(e -> {
            // Create the custom dialog.
                constructStage tmpe = new constructStage(primaryStage);

                ParkingManager parkingManager = ParkingManager.getInstance();
                try {
                    parkingManager.newParking(countP++,"Default");
                } catch (ParkingExistsException e1) {
                    e1.printStackTrace();
                }

                Collection<ParkingSpot> currentsSpot = null;

            ParkingSpotFactory parkingSpotFactory = new ParkingSpotFactory();
            try {
                currentsSpot = parkingManager.getParkingById(0).newParkingSpot(parkingSpotFactory, Integer.parseInt(tmpe.getNbCar()), "Car");
            } catch (ParkingNotPresentException e1) {
                e1.printStackTrace();
            }
            for(Iterator<ParkingSpot> i = currentsSpot.iterator();i.hasNext();){
                ButtonSpot a = new ButtonSpot(i.next());
                a.setStyle("-fx-background-color: #60ff05");
                currentsButtonSpot.add(a);
            }

            try{
                currentsSpot = parkingManager.getParkingById(0).newParkingSpot(parkingSpotFactory, Integer.parseInt(tmpe.getNbTruck()), "Carrier");
            } catch (ParkingNotPresentException e1) {
                e1.printStackTrace();
            }
            for(Iterator<ParkingSpot> i = currentsSpot.iterator();i.hasNext();){
                ButtonSpot a = new ButtonSpot(i.next());
                a.setStyle("-fx-background-color: #0e4fff");
                currentsButtonSpot.add(a);
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
                        ButtonSpot lel = (ButtonSpot)matrix[x][y];
                        MenuItem park = new MenuItem("Park");
                        park.setOnAction(event -> {
                            VehiculeStage parkStage = new VehiculeStage(primaryStage);
                            parkStage.showAndWait();
                            try {
                                lel.getParkingSpot().park(parkStage.getVehicule());
                            } catch (SpotNotEmptyException e1) {
                                e1.printStackTrace();
                            } catch (SpotBookedException e1) {
                                e1.printStackTrace();
                            } catch (UnknowVehiculeException e1) {
                                e1.printStackTrace();
                            } catch (VehicleNotFitException e1) {
                                e1.printStackTrace();
                            }
                            lel.setStyle("-fx-background-color: #ff0030");
                            park.setText("Unpark");
                        });
                       // MenuItem unpark = new MenuItem("Unpark");
                        MenuItem book = new MenuItem("Book");
                        MenuItem info = new MenuItem("Info");
                        info.setOnAction(event ->{
                            SpotStage spotStage = new SpotStage(primaryStage,lel.getParkingSpot());
                            spotStage.showAndWait();
                        });
                        lel.getItems().addAll(park,book,info);
                        gridPane.add(matrix[x][y], y, x);
                    }
                }

        });

        client.getItems().add(newCl);
        conf.getItems().add(changePark);
        autoSelector.getItems().add(find);
        autoSelector.getItems().add(undo);

        mainMenu.getMenus().addAll(client, conf, autoSelector,quit);

        Scene sc = new Scene(root, 500, 500);

        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
        primaryStage.setTitle("SWAG-- GUI");
        primaryStage.show();

    }

}
