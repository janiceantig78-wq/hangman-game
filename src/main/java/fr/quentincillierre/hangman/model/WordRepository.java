package fr.quentincillierre.hangman.model;

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
        categories.put("Programming", Arrays.asList(
            "JAVA", "JAVAFX", "PYTHON", "COMPILER", "VARIABLE", 
            "RECURSION", "INTERFACE", "REPOS", "DEVELOPER", "ALGORITHM"
        ));

        categories.put("Animals", Arrays.asList(
            "CAT", "DOG", "ELEPHANT", "GIRAFFE", "KANGAROO", 
            "LEOPARD", "DOLPHIN", "CHIMPANZEE", "PENGUIN", "TIGER"
        ));

        categories.put("Countries", Arrays.asList(
            "FRANCE", "PHILIPPINES", "CANADA", "JAPAN", "GERMANY", 
            "AUSTRALIA", "BRAZIL", "SINGAPORE", "INDONESIA", "MEXICO"
        ));
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
