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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

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

        Label headerLabel = new Label("Démarrage d'un serveur");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));


        /**
         * ServerType
         */

        ChoiceBox<ServerGame> serverTypeChoiceBox = new ChoiceBox<>();
        serverTypeChoiceBox.setPrefHeight(25.0);
        serverTypeChoiceBox.setPrefWidth(245.0);
        serverTypeChoiceBox.getItems().add(ServerGame.TEST);
        serverTypeChoiceBox.getItems().add(ServerGame.PLAY1);
        serverTypeChoiceBox.getItems().add(ServerGame.PLAY2);
        serverTypeChoiceBox.getItems().add(ServerGame.PLAY3);
        serverTypeChoiceBox.getItems().add(ServerGame.PLAY4);
        serverTypeChoiceBox.setValue(ServerGame.TEST);
        serverTypeChoiceBox.setStyle("-fx-background-color: transparent; -fx-border-color: #0598ff; -fx-border-width: 0px 0px 2px 0px;");
        serverTypeChoiceBox.setLayoutX(175.0);
        serverTypeChoiceBox.setLayoutY(148.0);
        GridPane.setHalignment(serverTypeChoiceBox, HPos.CENTER);
        GridPane.setMargin(serverTypeChoiceBox, new Insets(20, 0,20,0));
        gridPane.add(serverTypeChoiceBox, 0, 3, 2, 1);


        /**
         * Start server
         */

        Button createNewServer = new Button();
        createNewServer.setText("Démarrer un serveur");
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
            /*try {
                new KugaManager().startServer(getChoice(serverTypeChoiceBox));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            System.out.println("Demande de démarrage : " + getChoice(serverTypeChoiceBox));
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Erreur de création de serveur", "Un erreur est survenue lors de la création d'un serveur : " + getChoice(serverTypeChoiceBox).getGameName() + ". Veuillez réessayer ou redemarrer l'application.");
        });
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
        System.out.println("Nouveau rapport d'erreur.");
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
        System.out.println("Fermeture de l'application");
        System.exit(0);
    }
}
