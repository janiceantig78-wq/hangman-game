package fr.quentincillierre.hangman.controller;

import fr.quentincillierre.hangman.application.MainApp;
import fr.quentincillierre.hangman.model.ScoreManager;
import fr.quentincillierre.hangman.model.WordRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.stream.Collectors;

public class MenuController {

    @FXML private ComboBox<String> categoryBox;
    @FXML private ComboBox<String> difficultyBox;
    @FXML private Label highScoresLabel; 
    @FXML private AnchorPane menuRootPane; 
    @FXML private VBox scoreCardBox;       

    public static String selectedCategory = "Words A - G";
    public static String selectedDifficulty = "Medium";
    
    private static boolean isDarkModeActive = false;

    @FXML
    public void initialize() {
        WordRepository repo = new WordRepository();
        
        if (categoryBox != null) {
            categoryBox.setItems(FXCollections.observableArrayList(repo.getAvailableCategories()));
            categoryBox.setValue(selectedCategory);
        }
        
        if (difficultyBox != null) {
            difficultyBox.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
            difficultyBox.setValue(selectedDifficulty);
        }

        if (highScoresLabel != null) {
            List<Integer> historicalScores = ScoreManager.loadHighScores();
            if (!historicalScores.isEmpty()) {
                String scoreRow = historicalScores.stream()
                        .map(score -> score + " pts")
                        .collect(Collectors.joining("  |  "));
                highScoresLabel.setText(scoreRow);
            } else {
                highScoresLabel.setText("No scores recorded yet");
            }
        }
        
        applyThemeState();
    }

    @FXML
    private void handleStartGame() {
        if (categoryBox != null && categoryBox.getValue() != null) {
            selectedCategory = categoryBox.getValue();
        } else {
            selectedCategory = "Words A - G";
        }

        if (difficultyBox != null && difficultyBox.getValue() != null) {
            selectedDifficulty = difficultyBox.getValue();
        } else {
            selectedDifficulty = "Medium";
        }
        
        MainApp.switchScene("/game-view.fxml");
    }

    @FXML
    private void handleExitGame() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void handleToggleTheme(ActionEvent event) {
        isDarkModeActive = !isDarkModeActive;
        applyThemeState();
    }

    private void applyThemeState() {
        if (menuRootPane == null) return;
        
        if (isDarkModeActive) {
            menuRootPane.setStyle("-fx-background-color: #2b2b2b;");
            if (scoreCardBox != null) {
                scoreCardBox.setStyle("-fx-background-color: #3c3f41; -fx-padding: 20; -fx-background-radius: 10; -fx-border-color: #2a9d8f; -fx-border-width: 2; -fx-border-radius: 10;");
            }
            if (highScoresLabel != null) {
                highScoresLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-font-weight: bold;");
            }
        } else {
            menuRootPane.setStyle("-fx-background-color: #f0f2f5;");
            if (scoreCardBox != null) {
                scoreCardBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20; -fx-background-radius: 10; -fx-border-color: #2a9d8f; -fx-border-width: 2; -fx-border-radius: 10;");
            }
            if (highScoresLabel != null) {
                highScoresLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 14px; -fx-font-weight: bold;");
            }
        }
    }
}
