package gui;



import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import java.util.*;


//Created by on 30/12/14.


public class ParkingGui extends Application {


    private Map<Integer,Client> listCl = new HashMap<Integer,Client>();

    private void changecolor(Button A)
    {
        A.setStyle("-fx-background-color: #ff0030");
    }

    private void provider(Stage primary, Button A){

        List<String> choices = new ArrayList<>();
        choices.add("Voiture");
        choices.add("Camion");
        choices.add("Réservation");



        Optional<String> response = Dialogs.create()
                .owner(primary)
                .title("Formulaire remplissage")
                .masthead("Formulaire remplissage")
                .message("Choisir un type:")
                .showChoices(choices);

        if (response.isPresent()) {
            if (response.get() == "Voiture") {
                Client client = new Client();
                Stage a = new Stage();
                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));

                final TextField firstName = new TextField();
                firstName.setPromptText("Entrer le prenom du propriétaire");
                firstName.setPrefColumnCount(10);
                GridPane.setConstraints(firstName, 0, 0);
                grid.getChildren().add(firstName);

                final TextField lastName = new TextField();
                lastName.setPromptText("Entrer le nom de propriétaire");
                GridPane.setConstraints(lastName, 0, 1);
                grid.getChildren().add(lastName);

                final TextField comment = new TextField();
                comment.setPrefColumnCount(15);
                comment.setPromptText("Entrer une remarque.");
                GridPane.setConstraints(comment, 0, 2);
                grid.getChildren().add(comment);

                Button submit = new Button("Submit");
                GridPane.setConstraints(submit, 1, 0);
                grid.getChildren().add(submit);
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        if(!firstName.getText().equals("") && !lastName.getText().equals("")){
                            client.setFirstName(firstName.getText());
                            client.setLastName(lastName.getText());
                            listCl.put(Integer.parseInt(A.getText()), client);
                            A.setStyle("-fx-background-color: #ff0030");
                            a.close();
                        }
                    }
                });

                Scene ze = new Scene(grid);
                a.setScene(ze);
                a.setTitle("Inserer voiture");
                a.show();

            }
            else if (response.get() == "Camion"){

            }
            else {
                A.setStyle("-fx-background-color: #fcff00");
            }
        }
    }

    private void providerNope(Stage primary, Button current){
        Action response = Dialogs.create()
                .owner(primary)
                .title("Confirm Dialog")
                .message("Etes vous sûr de vouloir modifier cette place ?")
                .showConfirm();

        if (response == Dialog.ACTION_YES) {
            Stage price = new Stage();
            BorderPane Paned = new BorderPane();

            Client temp = listCl.get(Integer.parseInt(current.getText()));
            Text text = new Text("Prénom : " +  temp.getFirstName() + "\n" + "Nom : " + temp.getLastName() + "\n" + "Prix: ");

            Button quit = new Button("Liberer");
            BorderPane.setAlignment(quit, Pos.BOTTOM_RIGHT);
            BorderPane.setMargin(quit, new Insets(12,12,12,12));
            quit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    current.setStyle("-fx-background-color: #60ff05");
                    // DO TRAITEMENT EX PAIEMENT
                    price.close();
                }
            });
            Paned.setCenter(text);
            Paned.setBottom(quit);
            Scene n = new Scene(Paned);
            price.setScene(n);
            price.show();

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {



        int SIZE = 10;
        int length = SIZE;
        int width = SIZE;

        GridPane lol = new GridPane();

        int count = 0;
        Button[][] matrix = new Button[width][length];
        ColumnConstraints column = new ColumnConstraints(50);
        RowConstraints row = new RowConstraints(30);
        lol.getRowConstraints().add(row);
        lol.getColumnConstraints().add(column);

        for(int y = 0; y < length; y++)
        {
            lol.getColumnConstraints().add(column);
            for(int x = 0; x < width; x++)
            {
                lol.getRowConstraints().add(row);

                matrix[x][y] = new Button(Integer.toString(count++));
                matrix[x][y].setStyle("-fx-background-color: #60ff05");
                Button lel = matrix[x][y];
                matrix[x][y].setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent e) {

                        if(lel.getStyle().equals(new String("-fx-background-color: #60ff05"))){
                           provider(primaryStage,lel);
                        }

                        else{
                            providerNope(primaryStage,lel);
                        }
                    }
                });

                lol.add(matrix[x][y], y, x);;
            }
        }


        BorderPane root = new BorderPane();
        VBox topContainer = new VBox();  //Creates a container to hold all Menu Objects.
        MenuBar mainMenu = new MenuBar();  //Creates our main menu to hold our Sub-Menus.
        ToolBar toolBar = new ToolBar();  //Creates our tool-bar to hold the buttons.

        topContainer.getChildren().add(mainMenu);
        topContainer.getChildren().add(toolBar);
        root.setTop(topContainer);
        root.setCenter(lol);

        Menu client = new Menu("Client");
        MenuItem newCl = new MenuItem("Nouveau");
        MenuItem changeCl = new MenuItem("Modifier");
        Menu conf = new Menu("Config");
        MenuItem changePark = new MenuItem("Modifier Parking");
        Menu help = new Menu("Help");

        newCl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               System.out.println("LOL");
            }
        });

        changeCl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage modifCl = new Stage();
                BorderPane BP = new BorderPane();
                ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("Liste de clients"));
                BP.setCenter(cb);
                Scene temp = new Scene(BP,30,40);
                modifCl.setScene(temp);
                modifCl.show();
            }
        });

        client.getItems().add(newCl);
        client.getItems().add(changeCl);
        mainMenu.getMenus().addAll(client, conf, help);

        Scene sc = new Scene(root,500,500);

        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
        primaryStage.setTitle("SWAG-- GUI");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
