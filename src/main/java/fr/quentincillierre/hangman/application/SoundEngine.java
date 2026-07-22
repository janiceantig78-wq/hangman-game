package fr.quentincillierre.hangman.application;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class SoundEngine {
    /**
     * Plays a short audio clip file located locally in the resources/audio folder.
     */
    public static void playEffect(String soundFileName) {
        try {
            URL resource = SoundEngine.class.getResource("/audio/" + soundFileName);
            if (resource != null) {
                AudioClip clip = new AudioClip(resource.toExternalForm());
                clip.play();
            } else {
                System.err.println("Audio asset file not found: " + soundFileName);
            }
        } catch (Exception e) {
            System.err.println("Audio playback bypassed: " + e.getMessage());
        }
    }
}
