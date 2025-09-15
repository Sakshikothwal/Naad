package naad.music;

import naad.io.IOHelper;
import naad.util.Util;

public class Loop {
    // Play a sound n times with a gap
    public static void playLoop(String filePath, int count, int gapMs) {
        for (int i = 0; i < count; i++) {
            IOHelper.playSound(filePath);
            Util.sleep(gapMs);
        }
    }
}
