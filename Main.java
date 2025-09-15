import naad.io.IOHelper;
import naad.util.Util;
import naad.music.Loop;
import naad.music.Composition;
import naad.music.Sequence;

public class Main {
    public static void main(String[] args) {
        // Single sound
        IOHelper.playSound("resources/dha.wav");

        // Loop example
        Loop.playLoop("resources/dha.wav", 3, 500);

        // Composition example
        String[] comp = { "resources/dha.wav", "resources/din.wav", "resources/ta.wav" };
        Composition.playComposition(comp, 400);

        // Sequence example
        String[] seq = { "resources/dha.wav", "resources/din.wav", "resources/ta.wav" };
        int[] times = { 300, 600, 300 };
        Sequence.playSequence(seq, times);

        Util.debug("✅ All sounds finished!");
    }
}
