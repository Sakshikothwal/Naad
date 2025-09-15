package naad.music;

import naad.io.IOHelper;
import naad.util.Util;

public class Composition {
    // Play multiple sounds in a row
    public static void playComposition(String[] sounds, int gapMs) {
        for (String sound : sounds) {
            IOHelper.playSound(sound);
            Util.sleep(gapMs);
        }
    }
}
