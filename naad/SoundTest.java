
package com.naad;

import com.naad.sound.SoundManager;

public class SoundTest {
    public static void main(String[] args) throws Exception {
        String[] bols = { "Dha01.wav", "Dha02.wav", "Dha03.wav", "Dha04.wav", "Dha05.wav", "Dha06.wav", "Dha07.wav",
                "Dha08.wav", "Dha09.wav", "Dha10.wav", "Dhin01.wav", "Dhin02.wav", "Dhin03.wav", "Dhin04.wav",
                "Dhin05.wav",
                "Dhin06.wav", "Dhin07.wav", "Dhin08.wav", "Dhin09.wav", "Dhin10.wav", "Dhit01.wav", "Dhit02.wav",
                "Dhit03.wav", "Dhit04.wav", "Dhit05.wav", "Dhit06.wav", "Dhit07.wav", "Dhit08.wav", "Dhit09.wav",
                "Dhit10.wav",
                "Dhun01.wav", "Dhun02.wav", "Dhun03.wav", "Dhun04.wav", "Dhun05.wav", "Dhun06.wav", "Dhun07.wav",
                "Dhun08.wav", "Dhun09.wav", "Dhun10.wav",
                "Ga01.wav", "Ga02.wav", "Ga03.wav", "Ga04.wav", "Ga05.wav", "Ga06.wav", "Ga07.wav", "Ga08.wav",
                "Ga09.wav", "Ga10.wav", "Ga11.wav",
                "Ge01.wav", "Ge02.wav", "Ge03.wav", "Ge04.wav", "Ge05.wav", "Ge06.wav", "Ge07.wav", "Ge08.wav",
                "Ge09.wav", "Ge10.wav", "Ge11.wav", "Ge12.wav", "Ge13.wav", "Ge14.wav",
                "Ke01.wav", "Ke02.wav", "Ke03.wav", "Ke04.wav", "Ke05.wav", "Ke06.wav", "Ke07.wav", "Ke08.wav",
                "Ke09.wav", "Ke10.wav", "Ke11.wav",
                "Na01.wav", "Na02.wav", "Na03.wav", "Na04.wav", "Na05.wav", "Na06.wav", "Na07.wav", "Na08.wav",
                "Na09.wav", "Na10.wav", "Na11.wav",
                "Ta01.wav", "Ta02.wav", "Ta03.wav", "Ta04.wav", "Ta05.wav", "Ta06.wav", "Ta07.wav", "Ta08.wav",
                "Ta09.wav", "Ta10.wav",
                "Ti01.wav", "Ti02.wav", "Ti03.wav", "Ti04.wav", "Ti05.wav", "Ti06.wav", "Ti07.wav", "Ti08.wav",
                "Ti09.wav", "Ti10.wav",
                "Tin01.wav", "Tin02.wav", "Tin03.wav", "Tin04.wav", "Tin05.wav", "Tin06.wav", "Tin07.wav", "Tin08.wav",
                "Tin09.wav", "Tin10.wav",
                "Tun01.wav", "Tun02.wav", "Tun03.wav", "Tun04.wav", "Tun05.wav", "Tun06.wav", "Tun07.wav", "Tun08.wav",
                "Tun09.wav", "Tun10.wav" };
        for (String bol : bols) {
            System.out.println("Playing: " + bol);
            SoundManager.play(bol);
            Thread.sleep(2000);
        }
    }
}

/*
 * package com.naad;
 * 
 * import com.naad.sound.SoundManager;
 * import com.naad.sound.SequencePlayer;
 * 
 * public class SoundTest {
 * public static void main(String[] args) throws Exception {
 * // Single bols test
 * String[] bols = { "dha.wav", "din.wav", "na.wav", "tin.wav" };
 * for (String bol : bols) {
 * System.out.println("Playing: " + bol);
 * SoundManager.play(bol);
 * Thread.sleep(1000);
 * }
 * 
 * // Sequence test
 * System.out.println("Playing Pattern...");
 * SequencePlayer sp = new SequencePlayer(800); // 800ms per bol
 * sp.addPattern("dha din din dha dha tin tin ta");
 * sp.loop(2); // repeat 2 times
 * }
 * }
 */
