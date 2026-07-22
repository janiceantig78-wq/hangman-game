package fr.quentincillierre.hangman.controller;

import fr.quentincillierre.hangman.application.MainApp;
import fr.quentincillierre.hangman.model.WordRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class MenuController {

    @FXML private ComboBox<String> categoryBox;
    @FXML private ComboBox<String> difficultyBox;

    public static String selectedCategory = "Programming";
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
    }

    @FXML
    private void handleStartGame() {
        if (categoryBox != null) selectedCategory = categoryBox.getValue();
        if (difficultyBox != null) selectedDifficulty = difficultyBox.getValue();
        // Uses your active MainApp helper method
        MainApp.switchScene("game-view.fxml");
    }

    @FXML
    private void handleExitGame() {
        Platform.exit();
        System.exit(0);
    }
}
