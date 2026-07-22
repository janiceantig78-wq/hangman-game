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

public class GameController {
    @FXML private Label wordLabel;
    @FXML private Label wrongLabel;
    @FXML private Label timerLabel;
    @FXML private ImageView hangmanView;
    @FXML private GridPane keyboardGrid;
    @FXML private Button reseButton;

    private HangmanModel game;
    private Timeline timer;
    private int timeLeft = 60;

    @FXML
    public void initialize() {
        WordRepository repo = new WordRepository();
        game = new HangmanModel(repo.getRandomWord());
        updateUI();
        buildKeyboard();
        startTimer();
    }

    @FXML
    public void resetGame() {
        if (timer != null) {
            timer.stop();
        }
        WordRepository repo = new WordRepository();
        game = new HangmanModel(repo.getRandomWord());
        timeLeft = 60; // reset timer
        startTimer();
        updateUI();
        enableAllButtons();
    }

    private void enableAllButtons() {
        for (javafx.scene.Node node : keyboardGrid.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setDisable(false);
            }
        }
    }

    private void updateUI() {
        wordLabel.setText(game.getHiddenWord());
        wrongLabel.setText("Wrong: " + game.getCurrentWrongs() + " / 10");
        int step = Math.min(game.getCurrentWrongs(), 10);
        Image img = new Image(getClass().getClassLoader().getResource("pictures/" + step + "-hangman.png").toExternalForm());
        hangmanView.setImage(img);

        if (game.isWin()) {
            timer.stop();
            wordLabel.setText("YOU WIN! Word: " + game.getWordToGuess());
            disableAllButtons();
        } else if (game.isLose()) {
            timer.stop();
            wordLabel.setText("GAME OVER! Word: " + game.getWordToGuess());
            disableAllButtons();
        }
    }

    private void disableAllButtons() {
        for (javafx.scene.Node node : keyboardGrid.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setDisable(true);
            }
        }
    }

    private void buildKeyboard() {
        String[] rows = {"ABCDEFGHIJKLM", "NOPQRSTUVWXYZ"};
        int rowNum = 0;
        for (String row : rows) {
            int colNum = 0;
            for (char c : row.toCharArray()) {
                Button btn = new Button(String.valueOf(c));
                btn.getStyleClass().add("keyboard-button");
                btn.setPrefSize(45, 45);
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
            timerLabel.setText("Time: " + timeLeft + "s");
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