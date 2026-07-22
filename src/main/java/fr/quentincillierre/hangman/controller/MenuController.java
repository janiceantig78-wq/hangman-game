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

      
    public static String selectedCategory = "Words A - G";
    public static String selectedDifficulty = "Medium";

    @FXML
    public void initialize() {
        WordRepository repo = new WordRepository();
        
        // Populate and guard category options safely
        if (categoryBox != null) {
            categoryBox.setItems(FXCollections.observableArrayList(repo.getAvailableCategories()));
            categoryBox.setValue(selectedCategory);
        }
        
        // Populate and guard difficulty selection choices safely
        if (difficultyBox != null) {
            difficultyBox.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
            difficultyBox.setValue(selectedDifficulty);
        }
    }

    @FXML
    private void handleStartGame() {
        // Fallback default assignments if fields are null to prevent application freezes
        if (categoryBox != null && categoryBox.getValue() != null) {
            selectedCategory = categoryBox.getValue();
        } else {
            selectedCategory = "Programming";
        }

        if (difficultyBox != null && difficultyBox.getValue() != null) {
            selectedDifficulty = difficultyBox.getValue();
        } else {
            selectedDifficulty = "Medium";
        }
        
        // Transitions scene routing execution cleanly to the interactive play board
        MainApp.switchScene("/game-view.fxml");
    }

    @FXML
    private void handleExitGame() {
        Platform.exit();
        System.exit(0);
    }
}
