import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("🥁 === NAAD TABLA COMPOSER === 🥁");
        System.out.println("Starting tabla sound demonstration...");
        
        try {
            // Play available sound files from your repository
            String[] availableSounds = {
                "Dhun04.wav", "Dhun05.wav", "Dhun06.wav", 
                "Dhun07.wav", "Dhun08.wav", "Dhun09.wav", "Dhun10.wav"
            };
            
            // Single sound test
            System.out.println("\n🎵 Testing individual sounds:");
            for (String sound : availableSounds) {
                System.out.println("Playing: " + sound);
                playSound(sound);
                Thread.sleep(1000);
            }
            
            // Loop example - play a pattern 3 times
            System.out.println("\n🔄 Playing loop pattern:");
            playLoop(availableSounds[0], 3, 800);
            
            // Composition example - play multiple sounds in sequence
            System.out.println("\n🎼 Playing composition:");
            String[] composition = {availableSounds[0], availableSounds[1], availableSounds[2]};
            playComposition(composition, 600);
            
            // Sequence with different timings
            System.out.println("\n🎶 Playing sequence with varied timing:");
            String[] sequence = {availableSounds[0], availableSounds[1], availableSounds[2]};
            int[] timings = {400, 800, 400};
            playSequence(sequence, timings);
            
            System.out.println("\n✅ All sounds finished!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Play a single sound file
    public static void playSound(String filename) {
        try {
            File soundFile = findSoundFile(filename);
            if (soundFile == null || !soundFile.exists()) {
                System.err.println("Sound file not found: " + filename);
                return;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            
            // Wait for sound to complete
            while (clip.isRunning()) {
                Thread.sleep(10);
            }
            
            clip.close();
            audioStream.close();
            
        } catch (Exception e) {
            System.err.println("Error playing " + filename + ": " + e.getMessage());
        }
    }
    
    // Play a sound in a loop
    public static void playLoop(String filename, int repeatCount, int delayMs) {
        System.out.println("🔄 Looping " + filename + " " + repeatCount + " times");
        for (int i = 0; i < repeatCount; i++) {
            System.out.println("  Loop " + (i + 1) + "/" + repeatCount);
            playSound(filename);
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    // Play multiple sounds in sequence
    public static void playComposition(String[] sounds, int delayMs) {
        System.out.println("🎼 Playing composition with " + sounds.length + " sounds");
        for (int i = 0; i < sounds.length; i++) {
            System.out.println("  Playing " + (i + 1) + "/" + sounds.length + ": " + sounds[i]);
            playSound(sounds[i]);
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    // Play sounds with individual timing
    public static void playSequence(String[] sounds, int[] timings) {
        System.out.println("🎶 Playing sequence with custom timings");
        for (int i = 0; i < sounds.length && i < timings.length; i++) {
            System.out.println("  " + sounds[i] + " (delay: " + timings[i] + "ms)");
            playSound(sounds[i]);
            try {
                Thread.sleep(timings[i]);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    // Smart file finder - checks multiple possible locations
    private static File findSoundFile(String filename) {
        String[] possiblePaths = {
            filename,                           // Current directory
            "./" + filename,                    // Explicit current directory
            "sounds/" + filename,               // sounds subdirectory
            "resources/" + filename,            // resources directory
            "src/main/resources/" + filename,   // Maven resources
            "../" + filename                    // Parent directory
        };
        
        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {
                return file;
            }
        }
        
        return null;
    }
}