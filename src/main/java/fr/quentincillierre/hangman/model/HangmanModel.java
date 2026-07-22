package fr.quentincillierre.hangman.model;

import java.util.HashSet;
import java.util.Set;

public class HangmanModel {
    private final String wordToGuess;
    private final Set<Character> guessedLetters;
    private int currentWrongs;
    private static final int MAX_WRONGS = 10;

    public HangmanModel(String word) {
        this.wordToGuess = word.toUpperCase();
        this.guessedLetters = new HashSet<>();
        this.currentWrongs = 0;
    }

    public String getWordToGuess() { return wordToGuess; }
    public int getCurrentWrongs() { return currentWrongs; }
    public Set<Character> getGuessedLetter() { return new HashSet<>(guessedLetters); }

    public void tryLetter(Character letter) {
        char upper = Character.toUpperCase(letter);
        if (guessedLetters.contains(upper)) return;
        guessedLetters.add(upper);
        if (!wordToGuess.contains(String.valueOf(upper))) {
            currentWrongs++;
        }
    }

    public String getHiddenWord() {
        StringBuilder hidden = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            hidden.append(guessedLetters.contains(c) ? c : '_').append(' ');
        }
        return hidden.toString().trim();
    }

    public boolean isWin() {
        for (char c : wordToGuess.toCharArray()) {
            if (!guessedLetters.contains(c)) return false;
        }
        return true;
    }

    public boolean isLose() {
        return currentWrongs >= MAX_WRONGS;
    }
}