package in.oormi.naad.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a complete tabla composition containing multiple loops.
 * Based on the C++ CComposition class specification.
 */
public class Composition {
    
    // Constants
    public static final int LOOP_NONE = 0xFFFD;
    
    // Instance fields
    private String compositionName = "New Composition";
    private String composerName = "";
    private String notes = "";
    private LocalDateTime created;
    private LocalDateTime modified;
    private List<Loop> loops = new ArrayList<>();
    private int[] loopSequence;
    private Map<String, Object> metadata = new HashMap<>();
    private int loopCount = 0;
    
    /**
     * Default constructor
     */
    public Composition() {
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
    }
    
    /**
     * Adds a loop at specified grid position
     */
    public boolean addLoop(int col, int row) {
        Loop loop = new Loop();
        loops.add(loop);
        loop.setLoopName("New Loop " + (loopCount + 1));
        loop.setCol(col);
        loop.setRow(row);
        loopCount++;
        updateModified();
        return true;
    }
    
    /**
     * Adds a pre-existing loop to the composition
     */
    public boolean addLoop(Loop loop) {
        if (loop == null) return false;
        loops.add(loop);
        loopCount++;
        updateModified();
        return true;
    }
    
    /**
     * Deletes a loop at specified index
     */
    public boolean deleteLoop(int nloop) {
        if (nloop == LOOP_NONE || nloop < 0 || nloop >= loops.size()) {
            return false;
        }
        
        loops.remove(nloop);
        loopCount--;
        updateModified();
        return true;
    }
    
    /**
     * Gets loop index at specified grid position
     */
    public int getLoop(int col, int row) {
        for (int x = 0; x < loopCount; x++) {
            Loop loop = loops.get(x);
            if (loop.getCol() == col && loop.getRow() == row) {
                return x;
            }
        }
        return LOOP_NONE;
    }
    
    /**
     * Gets loop object at specified index
     */
    public Loop getLoopAt(int index) {
        if (index >= 0 && index < loops.size()) {
            return loops.get(index);
        }
        return null;
    }
    
    /**
     * Sets bol selection in a specific loop
     */
    public void setBolSelection(int nloop, int col, int row, boolean sel) {
        if (nloop == LOOP_NONE || nloop < 0 || nloop >= loops.size()) return;
        
        Loop loop = loops.get(nloop);
        int nbol = loop.getBol(col, row);
        if (nbol != Bol.BOL_NONE) {
            Bol bol = loop.getBolAt(nbol);
            if (bol != null) {
                bol.setSelected(sel);
            }
        }
    }
    
    /**
     * Sets loop selection at grid position
     */
    public void setLoopSelection(int col, int row, boolean sel) {
        int nloop = getLoop(col, row);
        if (nloop != LOOP_NONE) {
            loops.get(nloop).setSelected(sel);
        }
    }
    
    /**
     * Deselects all loops and bols
     */
    public void deSelectAll() {
        for (Loop loop : loops) {
            loop.setSelected(false);
            loop.deSelectAll();
        }
    }
    
    /**
     * Clears all loops from composition
     */
    public void clear() {
        int count = loopCount;
        for (int x = 0; x < count; x++) {
            deleteLoop(0);
        }
    }
    
    /**
     * Auto-composes a tabla composition using predefined patterns
     */
    public void autoCompose() {
        // Seed random number generator
        Random rand = new Random(System.currentTimeMillis());
        
        // Set composition info
        composerName = "Naad AI";
        compositionName = "AutoComp";
        notes += "\n\nAutomatically composed by Naad AI on " + LocalDateTime.now();
        
        // Parameters for auto-composition
        int maxmatra = 16;
        int minmatra = 3;
        int maxloops = 10;
        int minloops = 7;
        int maxbols = 16;
        int minbols = 4;
        int maxrepeats = 3;
        int minrepeats = 1;
        int maxmatrabols = 4;
        int minmatrabols = 1;
        int maxboltypes = Bol.MAXBOLVARS * Bol.MAXBOLS - 1;
        
        // Choose a maatra (using fixed value like C++ version)
        int rmatra = 6;
        
        // Create loops
        int rloop = getRandValue(rand, minloops, maxloops);
        for (int x = 0; x < rloop; x++) {
            addLoop(0, x);
        }
        
        // Configure specific loops (from C++ autoCompose)
        if (loops.size() > 0) {
            // Peshkar
            Loop peshkar = loops.get(0);
            peshkar.setLoopName("Peshkar");
            peshkar.setRepeatCount(1);
            peshkar.setMaatraBol(4);
            peshkar.setLoopBpm(90);
        }
        
        if (loops.size() > 1) {
            // Kaida
            Loop kaida = loops.get(1);
            kaida.setLoopName("Kaida");
            kaida.setRepeatCount(4);
        }
        
        if (loops.size() > 2) {
            // Rela
            Loop rela = loops.get(2);
            rela.setLoopName("Rela");
            rela.setRepeatCount(4);
        }
        
        if (loops.size() > 3) {
            // Prakar
            Loop prakar = loops.get(3);
            prakar.setLoopName("Prakar");
            prakar.setRepeatCount(4);
        }
        
        if (loops.size() > 4) {
            // Gat
            Loop gat = loops.get(4);
            gat.setLoopName("Gat");
            gat.setRepeatCount(4);
        }
        
        if (loops.size() > 5) {
            // Chakradhar
            Loop chakradhar = loops.get(5);
            chakradhar.setLoopName("Chakradhar");
            chakradhar.setRepeatCount(3);
        }
        
        if (loops.size() > 6) {
            // Tod
            Loop tod = loops.get(6);
            tod.setLoopName("Tod");
            tod.setRepeatCount(1);
        }
        
        // Add some sample tukdas to peshkar (simplified version)
        if (loops.size() > 0) {
            addSampleTukdas(loops.get(0), rand);
        }
        
        updateModified();
    }
    
    /**
     * Adds sample tukdas (bol patterns) to a loop
     */
    private void addSampleTukdas(Loop loop, Random rand) {
        // Frequent tukdas for peshkar (from C++ code)
        int[][] tpeshkar = {
            {20, 118, 110, 11},        // ti ra ke ta
            {105, 100, 80, 50},        // ge ge dhun dha
            {Bol.BOL_SILENCE, 60, Bol.BOL_SILENCE, 0},  // dhin na
            {110, 20, 11, Bol.BOL_SILENCE},             // ke ti ta
            {60, Bol.BOL_SILENCE, 0, 0},                // dhin na na
            {60, 40, 0, 0}             // dhin tun na na
        };
        
        // Add a few patterns
        int row = 0, col = 0;
        for (int y = 0; y < Math.min(4, tpeshkar.length); y++) {
            for (int x = 0; x < 4; x++) {
                if (col < Loop.MC && row < Loop.MR) {
                    loop.setBolAt(col, row, tpeshkar[y][x], Bol.BOL_NORMAL);
                    col++;
                    if (col >= 8) {
                        col = 0;
                        row++;
                    }
                }
            }
        }
    }
    
    /**
     * Gets a random value between min and max
     */
    private int getRandValue(Random rand, int min, int max) {
        if (min == 0) return rand.nextInt(max);
        int r = 0;
        while (r < min) r = rand.nextInt(max);
        return r;
    }
    
    /**
     * Sets the loop sequence for playback
     */
    public void setLoopSequence(int[] sequence) {
        // Validate sequence indices
        if (sequence != null) {
            for (int index : sequence) {
                if (index < 0 || index >= loopCount) {
                    throw new IllegalArgumentException("Invalid loop index in sequence: " + index);
                }
            }
        }
        this.loopSequence = sequence != null ? sequence.clone() : null;
        updateModified();
    }
    
    /**
     * Gets the total duration of the composition in milliseconds
     */
    @JsonIgnore
    public double getTotalDurationMs() {
        double totalDuration = 0.0;
        
        if (loopSequence != null) {
            // Use sequence order
            for (int loopIndex : loopSequence) {
                if (loopIndex >= 0 && loopIndex < loops.size()) {
                    Loop loop = loops.get(loopIndex);
                    totalDuration += loop.getDurationMs() * loop.getRepeatCount();
                }
            }
        } else {
            // Use all loops in order
            for (Loop loop : loops) {
                totalDuration += loop.getDurationMs() * loop.getRepeatCount();
            }
        }
        
        return totalDuration;
    }
    
    /**
     * Validates the composition structure
     */
    @JsonIgnore
    public boolean isValid() {
        if (compositionName == null || compositionName.trim().isEmpty()) {
            return false;
        }
        
        for (Loop loop : loops) {
            if (!loop.isValid()) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Updates the modification timestamp
     */
    private void updateModified() {
        this.modified = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getCompositionName() { return compositionName; }
    public void setCompositionName(String compositionName) { 
        this.compositionName = compositionName;
        updateModified();
    }
    
    public String getComposerName() { return composerName; }
    public void setComposerName(String composerName) { 
        this.composerName = composerName;
        updateModified();
    }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { 
        this.notes = notes;
        updateModified();
    }
    
    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }
    
    public LocalDateTime getModified() { return modified; }
    public void setModified(LocalDateTime modified) { this.modified = modified; }
    
    public List<Loop> getLoops() { return new ArrayList<>(loops); }
    public void setLoops(List<Loop> loops) { 
        this.loops = new ArrayList<>(loops);
        this.loopCount = this.loops.size();
        updateModified();
    }
    
    public int[] getLoopSequence() { 
        return loopSequence != null ? loopSequence.clone() : null; 
    }
    
    public Map<String, Object> getMetadata() { return new HashMap<>(metadata); }
    public void setMetadata(Map<String, Object> metadata) { 
        this.metadata = new HashMap<>(metadata);
        updateModified();
    }
    
    public int getLoopCount() { return loopCount; }
    
    @Override
    public String toString() {
        return "Composition{" +
                "compositionName='" + compositionName + '\'' +
                ", composerName='" + composerName + '\'' +
                ", loopCount=" + loopCount +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}