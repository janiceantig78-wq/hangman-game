package fr.quentincillierre.hangman.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    private static Stage primaryStage;

    
        // Load the Menu first
         @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Hangman Game");
        
        // Remove the forward slash so JavaFX searches the resources package root path correctly
        switchScene("menu-view.fxml");
        primaryStage.show();
    }

    // This is the missing method that fixes your compilation error!
    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Parent root = loader.load();
            
            Scene scene = primaryStage.getScene();
            if (scene == null) {
                scene = new Scene(root);
                primaryStage.setScene(scene);
            } else {
                scene.setRoot(root);
            }
            
            // Re-apply styles if style.css exists
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
