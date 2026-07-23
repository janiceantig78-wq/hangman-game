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
        
        // 1. Preload audio clips at startup for instant, zero-lag playback
        preloadGameAudio();

        // 2. Load the initial view
        switchScene("/menu-view.fxml");
        primaryStage.show();
    }

    /**
     * Preloads all game sound effects into memory.
     */
  private void preloadGameAudio() {
    SoundEngine.preload("correct.wav");
    SoundEngine.preload("wrong.wav");
    SoundEngine.preload("victory.wav");  // Fixed name
    SoundEngine.preload("gameover.wav");
    // SoundEngine.preload("click.wav");  // Comment out or remove until click.wav is added
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
            
            // Re-apply style.css cleanly without duplicating stylesheet entries
            java.net.URL cssUrl = MainApp.class.getResource("/style.css");
            if (cssUrl != null) {
                String cssPath = cssUrl.toExternalForm();
                if (!scene.getStylesheets().contains(cssPath)) {
                    scene.getStylesheets().add(cssPath);
                }
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