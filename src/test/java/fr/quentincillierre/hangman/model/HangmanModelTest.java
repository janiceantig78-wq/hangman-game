package fr.quentincillierre.hangman.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HangmanModelTest {

    private HangmanModel model;

    @BeforeEach
    void setUp() {
        // Fixed: Passed the required difficulty parameter to match your new constructor
        model = new HangmanModel("java", "Medium");
    }

    @Test
    void testConstructor() {
        assertEquals("JAVA", model.getWordToGuess().toUpperCase());
        assertEquals(0, model.getCurrentWrongs());
        assertEquals(7, model.getMaxWrongs()); // Medium difficulty allows 7 mistakes
    }

    @Test
    void testTryLetterWithCorrectLetter() {
        model.tryLetter('j');
        assertEquals(0, model.getCurrentWrongs());
        assertEquals("J _ _ _", model.getHiddenWord());
    }

    @Test
    void testTryLetterWithIncorrectLetter() {
        model.tryLetter('x');
        assertEquals(1, model.getCurrentWrongs());
        assertEquals("_ _ _ _", model.getHiddenWord());
    }

    @Test
    void testTryLetterWithDuplicate() {
        model.tryLetter('j');
        model.tryLetter('j');
        assertEquals(0, model.getCurrentWrongs());
    }
}
