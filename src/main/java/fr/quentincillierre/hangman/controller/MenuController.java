package fr.quentincillierre.hangman.controller;

import fr.quentincillierre.hangman.application.MainApp;
import fr.quentincillierre.hangman.model.ScoreManager;
import fr.quentincillierre.hangman.model.WordRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.util.List;
import java.util.stream.Collectors;

public class MenuController {

    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private ComboBox<String> difficultyBox;
    @FXML
    private Label highScoresLabel;

    public static String selectedCategory = "Words A - G";
    public static String selectedDifficulty = "Medium";

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
}