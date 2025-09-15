
package com.naad.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static final Map<String, Clip> cache = new HashMap<>();

    public static void play(String soundFile) {
        try {
            Clip clip = cache.get(soundFile);
            if (clip == null) {
                URL url = SoundManager.class.getResource("/sounds/" + soundFile);
                if (url == null) {
                    System.err.println("Sound not found: " + soundFile);
                    return;
                }
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                clip.open(audioIn);
                cache.put(soundFile, clip);
            }
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
