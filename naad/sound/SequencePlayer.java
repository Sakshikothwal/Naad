package com.naad.sound;

import java.util.ArrayList;
import java.util.List;

public class SequencePlayer {
    private final List<String> sequence;
    private final int tempo; // milliseconds per bol

    public SequencePlayer(int tempo) {
        this.sequence = new ArrayList<>();
        this.tempo = tempo;
    }

    public void addBol(String bol) {
        this.sequence.add(bol + ".wav");
    }

    public void addPattern(String pattern) {
        String[] bols = pattern.split("\\s+");
        for (String bol : bols) {
            this.sequence.add(bol + ".wav");
        }
    }

    public void play() {
        for (String bol : sequence) {
            System.out.println("Playing: " + bol);
            SoundManager.play(bol);
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void loop(int times) {
        for (int i = 0; i < times; i++) {
            play();
        }
    }
}
