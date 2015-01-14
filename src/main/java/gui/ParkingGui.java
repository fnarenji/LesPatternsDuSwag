package gui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingGui extends Application {


    private void changecolor(Button A)
    {
        A.setStyle("-fx-background-color: #ff0030");
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

                        //System.out.println(lel.getStyle());
                        if(lel.getStyle().equals(new String("-fx-background-color: #60ff05"))){
                            List<String> choices = new ArrayList<>();
                            choices.add("Voiture");
                            choices.add("Camion");
                            choices.add("Reservation");



                            Optional<String> response = Dialogs.create()
                                    .owner(primaryStage)
                                    .title("Formulaire remplissage")
                                    .masthead("Formulaire remplissage")
                                    .message("Choisir un type:")
                                    .showChoices(choices);

                            if (response.isPresent()) {
                                if (response.get() == "Voiture") {
                                    Stage a = new Stage();
                                    //Creating a GridPane container
                                    GridPane grid = new GridPane();
                                    grid.setPadding(new Insets(10, 10, 10, 10));

                                    final TextField name = new TextField();
                                    name.setPromptText("Entrer le nom du proprio.");
                                    name.setPrefColumnCount(10);
                                    name.getText();
                                    GridPane.setConstraints(name, 0, 0);
                                    grid.getChildren().add(name);

                                    final TextField lastName = new TextField();
                                    lastName.setPromptText("Entrer le nom de la Voiture");
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
                                            System.out.println(name.getText() + " " + lastName.getText());
                                        }
                                    });

                                    Scene ze = new Scene(grid);
                                    a.setScene(ze);
                                    a.show();
                                    lel.setStyle("-fx-background-color: #ff0030");
                                }
                            }
                        }
                        else{
                            Action response = Dialogs.create()
                                    .owner(primaryStage)
                                    .title("Confirm Dialog")
                                    .message("Etes vous sûr de vouloir modifier cette place ?")
                                    .showConfirm();

                            if (response == Dialog.ACTION_YES) {
                                Stage price = new Stage();
                                BorderPane Paned = new BorderPane();

                                Text exemple = new Text("Ici le prix et autres infos");
                                Button quit = new Button("Liberer");
                                quit.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent e) {
                                        lel.setStyle("-fx-background-color: #60ff05");
                                        // DO TRAITEMENT EX PAIEMENT
                                        price.close();
                                    }
                                });
                                Paned.setCenter(exemple);
                                Paned.setBottom(quit);
                                Scene n = new Scene(Paned);
                                price.setScene(n);
                                price.show();

                            }
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
        MenuItem newcl = new MenuItem("Nouveau");
        Menu conf = new Menu("Config");
        Menu help = new Menu("Help");

        newcl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               System.out.println("LOL");
            }
        });
        client.getItems().add(newcl);
        mainMenu.getMenus().addAll(client, conf, help);

        Scene sc = new Scene(root);

        primaryStage.setScene(sc);
        primaryStage.setTitle("SWAG-- GUI");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
