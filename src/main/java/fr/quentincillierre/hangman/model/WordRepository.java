package fr.quentincillierre.hangman.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WordRepository {
    private final Map<String, List<String>> categories = new HashMap<>();
    private final Random random = new Random();

    public WordRepository() {
        // Initialize the dictionary buckets matching your huge words.txt file
        categories.put("Words A - G", new ArrayList<>());
        categories.put("Words H - O", new ArrayList<>());
        categories.put("Words P - Z", new ArrayList<>());
        loadWordsFromFile();
    }

    private void loadWordsFromFile() {
        try (InputStream is = getClass().getResourceAsStream("/words.txt")) {
            if (is == null) {
                System.err.println("Error: words.txt file not found in resources!");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String word = line.trim().toUpperCase();
                    if (!word.isEmpty() && word.matches("[A-Z]+")) {
                        char firstChar = word.charAt(0);
                        
                        // Automatically sort all 178,691 words into logical letter groups
                        if (firstChar >= 'A' && firstChar <= 'G') {
                            categories.get("Words A - G").add(word);
                        } else if (firstChar >= 'H' && firstChar <= 'O') {
                            categories.get("Words H - O").add(word);
                        } else if (firstChar >= 'P' && firstChar <= 'Z') {
                            categories.get("Words P - Z").add(word);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to read words list dictionary: " + e.getMessage());
        }
    }

    public List<String> getAvailableCategories() {
        return Arrays.asList("Words A - G", "Words H - O", "Words P - Z");
    }

    public String getRandomWord(String category, String difficulty) {
        List<String> wordPool = categories.getOrDefault(category, categories.get("Words A - G"));
        List<String> filteredWords = new ArrayList<>();

        for (String word : wordPool) {
            int len = word.length();
            // Sub-filter your alphabet group by difficulty lengths
            if (difficulty.equalsIgnoreCase("Easy") && len <= 5) {
                filteredWords.add(word);
            } else if (difficulty.equalsIgnoreCase("Medium") && len >= 6 && len <= 8) {
                filteredWords.add(word);
            } else if (difficulty.equalsIgnoreCase("Hard") && len >= 9) {
                filteredWords.add(word);
            }
        }

        if (filteredWords.isEmpty()) {
            if (wordPool.isEmpty()) return "HANGMAN"; // Safeguard emergency fallback
            return wordPool.get(random.nextInt(wordPool.size()));
        }

        return filteredWords.get(random.nextInt(filteredWords.size()));
    }
}
