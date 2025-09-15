package naad.music;

import naad.io.IOHelper;
import naad.util.Util;

public class Sequence {
    // Play sound with custom timings
    public static void playSequence(String[] sounds, int[] timings) {
        if (sounds.length != timings.length) {
            System.out.println("❌ Sounds and timings must match!");
            return;
        }

        for (int i = 0; i < sounds.length; i++) {
            IOHelper.playSound(sounds[i]);
            Util.sleep(timings[i]);
        }
    }
}
