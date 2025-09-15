package com.naad.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundLoopPlayer {

    public static void startLoop() {
        File soundDir = new File("src/main/resources/sounds");
        File[] soundFiles = soundDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));

        if (soundFiles == null || soundFiles.length == 0) {
            System.out.println("No sound files found in " + soundDir.getAbsolutePath());
            return;
        }

        System.out.println("Found " + soundFiles.length + " sound files. Starting playback (each twice)...");

        for (File soundFile : soundFiles) {
            for (int i = 0; i < 2; i++) {  // repeat each sound twice
                playSound(soundFile);
            }
        }

        System.out.println("Finished playing all sounds twice each.");
    }

    private static void playSound(File soundFile) {
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000); // Wait for the sound to finish
            clip.close();
            System.out.println("Played: " + soundFile.getName());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
