package fr.quentincillierre.hangman.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordRepository {
    private final List<String> words = new ArrayList<>();
    private final Random random = new Random();

    public WordRepository() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("words.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) words.add(line);
            }
        } catch (Exception e) {
            words.add("DUNGEON");
            words.add("SWORD");
            words.add("SHIELD");
            words.add("GHOST");
            words.add("TREASURE");
            words.add("DRAGON");
        }
    }

    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
}