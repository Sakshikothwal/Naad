package in.oormi.naad;

import in.oormi.naad.core.*;
import in.oormi.naad.io.*;
import in.oormi.naad.util.*;

/**
 * Main application class for Naad Tabla Composer.
 * Implements application lifecycle management and integration points.
 */
public class NaadApplication {
    
    private SettingsManager settingsManager;
    private CompositionFileHandler compositionFileHandler;
    private LoopFileHandler loopFileHandler;
    private Composition currentComposition;
    
    public NaadApplication() {
        initialize();
    }
    
    private void initialize() {
        System.out.println("=== Naad Tabla Composer - Java Backend ===");
        System.out.println("Initializing application components...");
        
        // Initialize managers
        settingsManager = new SettingsManager();
        compositionFileHandler = new CompositionFileHandler();
        loopFileHandler = new LoopFileHandler();
        
        System.out.println("Settings Manager initialized");
        System.out.println("File handlers initialized");
        
        // Create a default composition
        createNewComposition();
        
        System.out.println("Application initialized successfully!");
    }
    
    public void createNewComposition() {
        currentComposition = new Composition();
        currentComposition.setCompositionName("New Composition");
        currentComposition.setComposerName("Unknown");
        System.out.println("New composition created: " + currentComposition.getCompositionName());
    }
    
    public void demonstrateCore() {
        System.out.println("\n=== Core Backend Demonstration ===");
        
        // Demonstrate Bol creation
        System.out.println("\n1. Creating Bols (Tabla syllables):");
        Bol dha = new Bol();
        dha.setBolType(50, 0, 0); // Dha01
        System.out.println("Created Bol: " + dha.getBolName() + " (ID: " + dha.getBolId() + ")");
        
        Bol na = new Bol();
        na.setBolType(0, 1, 0); // Na01
        System.out.println("Created Bol: " + na.getBolName() + " (ID: " + na.getBolId() + ")");
        
        // Demonstrate Loop creation
        System.out.println("\n2. Creating Loop:");
        Loop loop = new Loop();
        loop.setLoopName("Demo Loop");
        loop.setLoopBpm(120);
        loop.setMaatraBol(4);
        loop.addBol(dha);
        loop.addBol(na);
        System.out.println("Created Loop: " + loop.getLoopName() + 
                          " (BPM: " + loop.getLoopBpm() + 
                          ", Bols: " + loop.getBolCount() + ")");
        
        // Demonstrate Composition
        System.out.println("\n3. Adding Loop to Composition:");
        currentComposition.addLoop(loop);
        currentComposition.setCompositionName("Demo Composition");
        currentComposition.setComposerName("Naad Backend Demo");
        System.out.println("Composition: " + currentComposition.getCompositionName() + 
                          " by " + currentComposition.getComposerName() +
                          " (Loops: " + currentComposition.getLoopCount() + ")");
        
        // Demonstrate Sequence
        System.out.println("\n4. Creating Playback Sequence:");
        Sequence sequence = new Sequence();
        sequence.createBolSequence(loop);
        System.out.println("Sequence created with " + sequence.getBolSeqCount() + 
                          " bols, repeat count: " + sequence.getRepeatSeqCount());
        
        // Demonstrate File I/O
        System.out.println("\n5. File I/O Operations:");
        String filePath = "demo-composition";
        boolean saved = compositionFileHandler.saveComposition(currentComposition, filePath);
        System.out.println("Composition saved: " + saved);
        
        if (saved) {
            Composition loaded = compositionFileHandler.loadComposition(filePath + ".naad");
            System.out.println("Composition loaded: " + (loaded != null ? loaded.getCompositionName() : "Failed"));
        }
        
        // Demonstrate Auto-Composition
        System.out.println("\n6. Auto-Composition Feature:");
        Composition autoComp = new Composition();
        autoComp.autoCompose();
        System.out.println("Auto-composed: " + autoComp.getCompositionName() + 
                          " by " + autoComp.getComposerName() + 
                          " (Loops: " + autoComp.getLoopCount() + ")");
    }
    
    public void showStatus() {
        System.out.println("\n=== Application Status ===");
        System.out.println("Current Composition: " + 
                          (currentComposition != null ? currentComposition.getCompositionName() : "None"));
        System.out.println("Audio Sample Path: " + 
                          settingsManager.getStringSetting("audio.samplePath", "./samples"));
        System.out.println("Default BPM: " + 
                          settingsManager.getIntSetting("audio.defaultBpm", 180));
        System.out.println("Auto-backup enabled: " + 
                          settingsManager.getBooleanSetting("composition.autoBackup", true));
    }
    
    
    public static void main(String[] args) {
        com.naad.sound.SoundLoopPlayer.startLoop();
        try {
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
            e.printStackTrace();
        }
    }

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