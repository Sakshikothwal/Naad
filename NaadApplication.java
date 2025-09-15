package in.oormi.naad;

import in.oormi.naad.core.*;
import in.oormi.naad.io.*;
import in.oormi.naad.util.*;

public class NaadApplication {
    
    private SettingsManager settingsManager;
    private CompositionFileHandler compositionFileHandler;
    private LoopFileHandler loopFileHandler;
    private Composition currentComposition;
    
    public NaadApplication() {
        initialize();
    }
    
    // ... [all your existing methods stay the same] ...
    
    public static void main(String[] args) {
        try {
            // Initialize the application
            NaadApplication app = new NaadApplication();
            
            // Demonstrate core functionality
            app.demonstrateCore();
            app.showStatus();
            
            // Sound loop functionality
            com.naad.sound.SoundLoopPlayer.startLoop();
            
            java.io.File soundFolder = new java.io.File("src/main/resources/sounds");
            java.io.File[] files = soundFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));

            if (files != null) {
                for (java.io.File file : files) {
                    System.out.println("Playing: " + file.getName());
                    javax.sound.sampled.AudioInputStream audioStream = javax.sound.sampled.AudioSystem.getAudioInputStream(file);
                    javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength() / 1000);
                    clip.close();
                }
            } else {
                System.out.println("No sound files found in sounds folder.");
            }
        } catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Getters for integration with other teams
    public Composition getCurrentComposition() { return currentComposition; }
    public void setCurrentComposition(Composition composition) { this.currentComposition = composition; }
    public SettingsManager getSettingsManager() { return settingsManager; }
    public CompositionFileHandler getCompositionFileHandler() { return compositionFileHandler; }
    public LoopFileHandler getLoopFileHandler() { return loopFileHandler; }
}