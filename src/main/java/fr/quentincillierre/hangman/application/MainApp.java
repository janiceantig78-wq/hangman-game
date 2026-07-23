package fr.quentincillierre.hangman.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Hangman Game");
        switchScene("/menu-view.fxml");
        primaryStage.show();
    }

    public static void switchScene(String fxmlPath) {
        try {
            java.net.URL fxmlUrl = MainApp.class.getResource(fxmlPath);
            if (fxmlUrl == null) {
                throw new IllegalStateException("Cannot find layout asset file at path: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Scene scene = primaryStage.getScene();
            if (scene == null) {
                scene = new Scene(root);
                primaryStage.setScene(scene);
            } else {
                scene.setRoot(root);
            }

            if (MainApp.class.getResource("/style.css") != null) {
                scene.getStylesheets().add(MainApp.class.getResource("/style.css").toExternalForm());
            }

        } catch (Exception e) {
            System.err.println("Error changing scenes to " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}