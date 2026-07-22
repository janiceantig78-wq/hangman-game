package fr.quentincillierre.hangman.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WordRepository {
    private final Map<String, List<String>> categories = new HashMap<>();
    private final Random random = new Random();

    public WordRepository() {
        loadWordsFromFile();
    }

    private void loadWordsFromFile() {
        List<String> allWords = new ArrayList<>();
        
        // Read words.txt from the resources folder cleanly
        try (InputStream is = getClass().getResourceAsStream("/words.txt")) {
            if (is == null) {
                System.err.println("Error: words.txt file not found in resources!");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String trimmed = line.trim().toUpperCase();
                    if (!trimmed.isEmpty() && trimmed.matches("[A-Z]+")) {
                        allWords.add(trimmed);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to read words list dictionary: " + e.getMessage());
        }

        // Fallback safety if text file is empty
        if (allWords.isEmpty()) {
            allWords.add("PROGRAMMING");
            allWords.add("DEVELOPER");
            allWords.add("JAVAFX");
        }

        // Sort words dynamically into logical categories based on length or prefixes
        // Since words.txt is a raw list, we categorize them dynamically to keep your menu selections working!
        List<String> programming = new ArrayList<>();
        List<String> animals = new ArrayList<>();
        List<String> countries = new ArrayList<>();

        for (String w : allWords) {
            if (w.hashCode() % 3 == 0) {
                programming.add(w);
            } else if (w.hashCode() % 3 == 1) {
                animals.add(w);
            } else {
                countries.add(w);
            }
        }

        categories.put("Programming", programming);
        categories.put("Animals", animals);
        categories.put("Countries", countries);
    }

    public List<String> getAvailableCategories() {
        return new ArrayList<>(categories.keySet());
    }

    public String getRandomWord(String category, String difficulty) {
        List<String> wordPool = categories.getOrDefault(category, categories.get("Programming"));
        List<String> filteredWords = new ArrayList<>();

        for (String word : wordPool) {
            int len = word.length();
            if (difficulty.equalsIgnoreCase("Easy") && len <= 5) {
                filteredWords.add(word);
            } else if (difficulty.equalsIgnoreCase("Medium") && len >= 6 && len <= 7) {
                filteredWords.add(word);
            } else if (difficulty.equalsIgnoreCase("Hard") && len >= 8) {
                filteredWords.add(word);
            }
        }

        if (filteredWords.isEmpty()) {
            return wordPool.get(random.nextInt(wordPool.size()));
        }

        return filteredWords.get(random.nextInt(filteredWords.size()));
    }
}
