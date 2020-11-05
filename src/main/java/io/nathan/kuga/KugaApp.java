package io.supreme.kuga;

import io.supreme.kuga.manager.KugaManager;
import io.supreme.kuga.server.ServerGame;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sun.audio.AudioPlayer;

import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;

/**
 * Cette application en java permet de lancer un serveur en un clique.
 */
public class KugaApp extends Application {

    private Stage primaryStage;
    private Scene scene;
    private
    boolean closing = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Kuga");
        GridPane gridPane = createPage();
        this.scene = new Scene(gridPane, 800, 500);
        addUIControls(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        /*Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ui/Interface.fxml"));
        Scene scene = new Scene(root);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED);
        primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.setTitle("Kuga");
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }

    private void addUIControls(GridPane gridPane) {

        /**
         * Text
         */

        Label headerLabel = new Label("Starting a server");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));


        /**
         * ServerGame
         */

        ChoiceBox<ServerGame> serverGameChoiceBox = new ChoiceBox<>();
        serverGameChoiceBox.setPrefHeight(25.0);
        serverGameChoiceBox.setPrefWidth(245.0);
        serverGameChoiceBox.getItems().add(ServerGame.TEST);
        serverGameChoiceBox.getItems().add(ServerGame.PLAY1);
        serverGameChoiceBox.getItems().add(ServerGame.PLAY2);
        serverGameChoiceBox.getItems().add(ServerGame.PLAY3);
        serverGameChoiceBox.getItems().add(ServerGame.PLAY4);
        serverGameChoiceBox.setValue(ServerGame.TEST);
        serverGameChoiceBox.setStyle("-fx-background-color: transparent; -fx-border-color: #0598ff; -fx-border-width: 0px 0px 2px 0px;");
        serverGameChoiceBox.setLayoutX(175.0);
        serverGameChoiceBox.setLayoutY(148.0);
        GridPane.setHalignment(serverGameChoiceBox, HPos.CENTER);
        GridPane.setMargin(serverGameChoiceBox, new Insets(20, 0,20,0));
        gridPane.add(serverGameChoiceBox, 0, 3, 2, 1);


        /**
         * Start server
         */

        Button createNewServer = new Button();
        createNewServer.setText("Starting a server");
        createNewServer.setPrefHeight(44.0);
        createNewServer.setDefaultButton(true);
        createNewServer.setPrefWidth(144.0);
        createNewServer.setStyle("-fx-background-color: transparent; -fx-border-color: #0598ff; -fx-border-width: 0px 0px 2px 0px;");
        createNewServer.setLayoutX(225.0);
        createNewServer.setLayoutY(273.0);
        GridPane.setHalignment(createNewServer, HPos.CENTER);
        GridPane.setMargin(createNewServer, new Insets(20, 0,20,0));
        gridPane.add(createNewServer, 0, 4, 2, 1);


        /**
         * Actions
         */

        createNewServer.setOnAction(event -> {
            try {
                new KugaManager().startServer(getChoice(serverGameChoiceBox));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Start-up request : " + getChoice(serverGameChoiceBox));
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Server creation error", "An error occurred during the creation of a server : " + getChoice(serverGameChoiceBox).getGameName() + ". Please try again or restart the application.");
        });
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
        System.out.println("New error report.");
    }

    private GridPane createPage() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        return gridPane;
    }

    private ServerGame getChoice(ChoiceBox<ServerGame> serverGameChoiceBox) {
        return serverGameChoiceBox.getValue();
    }

    public void quit() {
        closing = true;
        System.out.println("Closing the application.");
        System.exit(0);
    }
}
