import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NaadTablaPlayer {
    
    private static final String[] TABLA_SOUNDS = {
        "Dhun04.wav", "Dhun05.wav", "Dhun06.wav", "Dhun07.wav", 
        "Dhun08.wav", "Dhun09.wav", "Dhun10.wav"
    };
    
    public static void main(String[] args) {
        System.out.println("🥁 === NAAD TABLA COMPOSER === 🥁");
        System.out.println("Initializing tabla sound system...");
        
        NaadTablaPlayer player = new NaadTablaPlayer();
        
        try {
            // Test 1: Play individual sounds
            System.out.println("\n🎵 Playing individual tabla sounds:");
            player.playIndividualSounds();
            
            // Test 2: Play a simple rhythm pattern
            System.out.println("\n🎶 Playing rhythm pattern:");
            player.playRhythmPattern();
            
            // Test 3: Play a composition
            System.out.println("\n🎼 Playing tabla composition:");
            player.playComposition();
            
            System.out.println("\n✅ Naad Tabla Player completed successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void playIndividualSounds() throws Exception {
        for (String sound : TABLA_SOUNDS) {
            System.out.println("🔊 Playing: " + sound);
            playSound(sound);
            Thread.sleep(800); // Pause between sounds
        }
    }
    
    public void playRhythmPattern() throws Exception {
        // Create a simple tabla rhythm: Dha-Din-Din-Dha
        String[] pattern = {"Dhun04.wav", "Dhun05.wav", "Dhun05.wav", "Dhun04.wav"};
        String[] bolNames = {"Dha", "Din", "Din", "Dha"};
        
        System.out.println("🥁 Rhythm Pattern: Dha-Din-Din-Dha");
        
        for (int i = 0; i < pattern.length; i++) {
            System.out.print(bolNames[i] + " ");
            playSound(pattern[i]);
            Thread.sleep(600);
        }
        System.out.println();
    }
    
    public void playComposition() throws Exception {
        // Create a more complex composition
        System.out.println("🎼 Tabla Composition: Traditional Teental Pattern");
        
        // Teental pattern (16 beats)
        String[] composition = {
            "Dhun04.wav", "Dhun05.wav", "Dhun06.wav", "Dhun04.wav", // Dha Dhin Dhin Dha
            "Dhun04.wav", "Dhun05.wav", "Dhun06.wav", "Dhun04.wav", // Dha Dhin Dhin Dha  
            "Dhun07.wav", "Dhun08.wav", "Dhun09.wav", "Dhun07.wav", // Na Tin Tin Ta
            "Dhun08.wav", "Dhun05.wav", "Dhun06.wav", "Dhun04.wav"  // Tin Dhin Dhin Dha
        };
        
        String[] bolPattern = {
            "Dha", "Dhin", "Dhin", "Dha",
            "Dha", "Dhin", "Dhin", "Dha", 
            "Na", "Tin", "Tin", "Ta",
            "Tin", "Dhin", "Dhin", "Dha"
        };
        
        for (int i = 0; i < composition.length; i++) {
            System.out.print(bolPattern[i] + " ");
            playSound(composition[i]);
            Thread.sleep(500);
            
            // Add emphasis on beats 1, 5, 9, 13 (sam)
            if (i % 4 == 0) {
                System.out.print("| ");
            }
        }
        System.out.println("\n🎯 Composition complete!");
    }
    
    private void playSound(String filename) {
        try {
            // Try multiple possible paths for the sound files
            File soundFile = findSoundFile(filename);
            
            if (soundFile == null || !soundFile.exists()) {
                System.err.println("⚠️  Sound file not found: " + filename);
                return;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            
            // Wait for the sound to finish
            while (clip.isRunning()) {
                Thread.sleep(10);
            }
            
            clip.close();
            audioStream.close();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            System.err.println("❌ Error playing sound " + filename + ": " + e.getMessage());
        }
    }
    
    private File findSoundFile(String filename) {
        // Try different possible locations
        String[] possiblePaths = {
            filename,                           // Current directory
            "./" + filename,                    // Explicit current directory
            "sounds/" + filename,               // sounds subdirectory
            "src/main/resources/sounds/" + filename, // Maven structure
            "resources/" + filename,            // resources directory
            "../" + filename                    // Parent directory
        };
        
        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {
                System.out.println("✅ Found sound file at: " + path);
                return file;
            }
        }
        
        return null;
    }
}