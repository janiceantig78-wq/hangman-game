package fr.quentincillierre.hangman.controller;

import fr.quentincillierre.hangman.model.HangmanModel;
import fr.quentincillierre.hangman.model.WordRepository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import java.io.InputStream;

public class GameController {
    @FXML private Label wordLabel;
    @FXML private Label wrongLabel;
    @FXML private Label timerLabel;
    @FXML private Label pointsLabel; 
    @FXML private ImageView hangmanView;
    @FXML private GridPane keyboardGrid;
    @FXML private Button resetButton;
    @FXML private Button hintButton; 

    private HangmanModel game;
    private Timeline timer;
    private int timeLeft = 60;

    @FXML
    public void initialize() {
        startGameSession();
    }

    private void startGameSession() {
        WordRepository repo = new WordRepository();
        String word = repo.getRandomWord(MenuController.selectedCategory, MenuController.selectedDifficulty);
        game = new HangmanModel(word, MenuController.selectedDifficulty);
        
        updateUI();
        buildKeyboard();
        startTimer();
    }

    @FXML
    public void resetGame() {
        if (timer != null) timer.stop();
        timeLeft = 60;
        keyboardGrid.getChildren().clear();
        startGameSession();
    }

    @FXML
    private void handleTriggerHint() {
        if (game.isWin() || game.isLose() || game.getPlayerPoints() < 30) return;
        
        char hintLetter = game.getRevealHintLetter();
        if (hintLetter != ' ') {
            game.deductPoints(30);
            game.tryLetter(hintLetter); 
            updateUI();
        }
    }

    private void updateUI() {
        if (wordLabel != null) wordLabel.setText(game.getHiddenWord());
        
        if (wrongLabel != null) {
            wrongLabel.setText("Category: " + MenuController.selectedCategory + 
                               " | Mistakes: " + game.getCurrentWrongs() + " / " + game.getMaxWrongs());
        }

        if (pointsLabel != null) {
            pointsLabel.setText("Points: " + game.getPlayerPoints());
        }
        
        int step = game.getCurrentWrongs();
        if (game.getMaxWrongs() == 5) {
            step = game.getCurrentWrongs() * 2; 
        }
        step = Math.min(step, 10);
        
        try {
            InputStream imgStream = getClass().getResourceAsStream("/pictures/" + step + "-hangman.png");
            if (imgStream != null) {
                hangmanView.setImage(new Image(imgStream));
            }
        } catch (Exception e) {
            System.err.println("Error rendering assets: " + e.getMessage());
        }

        if (game.isWin()) {
            if (timer != null) timer.stop();
            wordLabel.setText("YOU WIN! Word: " + game.getWordToGuess());
            disableAllButtons();
        } else if (game.isLose()) {
            if (timer != null) timer.stop();
            wordLabel.setText("GAME OVER! Word: " + game.getWordToGuess());
            disableAllButtons();
        }
    }

    private void disableAllButtons() {
        for (javafx.scene.Node node : keyboardGrid.getChildren()) {
            if (node instanceof Button) ((Button) node).setDisable(true);
        }
    }

    private void buildKeyboard() {
        keyboardGrid.getChildren().clear();
        
        // Center the keyboard grid layout horizontally and vertically
        keyboardGrid.setAlignment(javafx.geometry.Pos.CENTER);
        keyboardGrid.setHgap(12.0); 
        keyboardGrid.setVgap(12.0);

        String[] rows = {"ABCDEFGHIJKLM", "NOPQRSTUVWXYZ"};
        int rowNum = 0;
        for (String row : rows) {
            int colNum = 0;
            for (char c : row.toCharArray()) {
                Button btn = new Button(String.valueOf(c));
                btn.getStyleClass().add("keyboard-button");
                
                // Enlarge buttons for better gameplay interaction
                btn.setPrefSize(60, 55); 
                
                // Set bold text styling and larger font size
                btn.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;");
                
                btn.setOnAction(e -> handleGuess(c, btn));
                keyboardGrid.add(btn, colNum, rowNum);
                colNum++;
            }
            rowNum++;
        }
    }

    private void handleGuess(char c, Button btn) {
        if (game.isWin() || game.isLose()) return;
        btn.setDisable(true);
        game.tryLetter(c);
        updateUI();
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            if (timerLabel != null) timerLabel.setText("Time: " + timeLeft + "s");
            if (timeLeft <= 0) {
                timer.stop();
                while (!game.isLose()) game.tryLetter('X');
                updateUI();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
}
