package fr.quentincillierre.hangman.application;

import javafx.scene.media.AudioClip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundEngine {

    private static final Map<String, AudioClip> SOUND_CACHE = new HashMap<>();
    private static boolean isMuted = false;

    /**
     * Preloads an audio file into RAM so it plays instantly when triggered.
     */
    public static void preload(String soundFileName) {
        if (SOUND_CACHE.containsKey(soundFileName)) return;

        try {
            URL resource = SoundEngine.class.getResource("/audio/" + soundFileName);
            if (resource != null) {
                AudioClip clip = new AudioClip(resource.toExternalForm());
                SOUND_CACHE.put(soundFileName, clip);
            } else {
                System.err.println("[SoundEngine] File not found: /audio/" + soundFileName);
            }
        } catch (Exception e) {
            System.err.println("[SoundEngine] Error loading " + soundFileName + ": " + e.getMessage());
        }
    }

    /**
     * Plays a sound effect instantly.
     */
    public static void playEffect(String soundFileName) {
        if (isMuted) return;

        // If sound wasn't preloaded, try loading it on the fly
        if (!SOUND_CACHE.containsKey(soundFileName)) {
            preload(soundFileName);
        }

        AudioClip clip = SOUND_CACHE.get(soundFileName);
        if (clip != null) {
            clip.play();
        }
    }

    public static void setMuted(boolean muted) {
        isMuted = muted;
    }

    public static boolean isMuted() {
        return isMuted;
    }
}