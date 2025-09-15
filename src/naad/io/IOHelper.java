package naad.io;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class IOHelper {
    // Load and play a WAV file
    public static void playSound(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("⚠️ File not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Wait until sound completes
            while (clip.isRunning()) {
                Thread.sleep(10);
            }

            clip.close();
            audioStream.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            System.out.println("❌ Error playing sound: " + e.getMessage());
        }
    }
}
