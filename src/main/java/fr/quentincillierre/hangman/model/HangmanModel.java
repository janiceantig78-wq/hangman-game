package fr.quentincillierre.hangman.model;

import java.util.HashSet;
import java.util.Set;

public class HangmanModel {
    private final String wordToGuess;
    private final Set<Character> guessedLetters;
    private int currentWrongs;
    private int maxWrongs;
    private int playerPoints;

    // Constructor: Accepts word and difficulty configuration
    public HangmanModel(String wordToGuess, String difficulty) {
        this.wordToGuess = wordToGuess.toUpperCase();
        this.guessedLetters = new HashSet<>();
        this.currentWrongs = 0;
        this.playerPoints = 100; // Baseline points for hint spending

        // Dynamic difficulty setting rules
        if (difficulty.equalsIgnoreCase("Easy")) {
            this.maxWrongs = 10;
        } else if (difficulty.equalsIgnoreCase("Medium")) {
            this.maxWrongs = 10;
        } else {
            this.maxWrongs = 5; // Hard mode threshold
        }
    }

    public String getWordToGuess() {
        return this.wordToGuess;
    }

    public int getCurrentWrongs() {
        return this.currentWrongs;
    }

    public int getMaxWrongs() {
        return this.maxWrongs;
    }

    public int getPlayerPoints() {
        return this.playerPoints;
    }

    public void deductPoints(int amount) {
        this.playerPoints = Math.max(0, this.playerPoints - amount);
    }

    public void awardPoints(int amount) {
        this.playerPoints += amount;
    }

    public void tryLetter(char letter) {
        char upperLetter = Character.toUpperCase(letter);
        if (guessedLetters.contains(upperLetter)) return;

        guessedLetters.add(upperLetter);
        if (!wordToGuess.contains(String.valueOf(upperLetter))) {
            currentWrongs++;
        }
    }

    public String getHiddenWord() {
        StringBuilder sb = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            if (guessedLetters.contains(c)) {
                sb.append(c).append(" ");
            } else {
                sb.append("_ ");
            }
        }
        return sb.toString().trim();
    }

    public boolean isWin() {
        for (char c : wordToGuess.toCharArray()) {
            if (!guessedLetters.contains(c)) return false;
        }
        return true;
    }

    public boolean isLose() {
        return currentWrongs >= maxWrongs;
    }

    public char getRevealHintLetter() {
        for (char letter : wordToGuess.toCharArray()) {
            if (!guessedLetters.contains(letter)) {
                return letter;
            }
        }
        return ' ';
    }
}
