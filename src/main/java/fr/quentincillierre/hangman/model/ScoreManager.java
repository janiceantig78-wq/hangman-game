package fr.quentincillierre.hangman.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {
    private static final String SCORE_FILE = "highscores.txt";

    /**
     * Saves a run score permanently to disk and keeps only the top 5 highest records.
     */
    public static void saveHighScore(int score) {
        List<Integer> scores = loadHighScores();
        scores.add(score);
        
        // Sort descending (highest scores first)
        scores.sort(Collections.reverseOrder());
        
        // Keep only top 5 scores
        if (scores.size() > 5) {
            scores = scores.subList(0, 5);
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
            for (int s : scores) {
                writer.write(String.valueOf(s));
                writer.newLine();
            }
        } catch (Exception e) {
            System.err.println("Could not write leaderboard score file: " + e.getMessage());
        }
    }

    /**
     * Reads recorded high scores from the local file storage.
     */
    public static List<Integer> loadHighScores() {
        List<Integer> scores = new ArrayList<>();
        File file = new File(SCORE_FILE);
        
        if (!file.exists()) return scores;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    scores.add(Integer.parseInt(trimmed));
                }
            }
        } catch (Exception e) {
            System.err.println("Could not read leaderboard scores: " + e.getMessage());
        }
        return scores;
    }
}
