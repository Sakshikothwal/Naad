package in.oormi.naad.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rhythmic loop containing a sequence of bols.
 * Based on the C++ CLoop class specification.
 */
public class Loop {
    
    // Constants
    public static final int DEFAULT_BPM = 180;
    public static final int MC = 24; // max cols
    public static final int MR = 24; // max rows
    
    // Instance fields (mapped from C++ members)
    private String loopName = "New Loop";
    private String note = "";
    private int maatraBol = 1;
    private int repeatCount = 1;
    private int bolCount = 0;
    private int loopBpm = DEFAULT_BPM;
    private int row = 0;
    private int col = 0;
    private int accompaniment = 0;
    private boolean selected = false;
    private List<Bol> bols = new ArrayList<>();
    
    // Private field
    private int matraCount = 0;
    
    /**
     * Default constructor - initializes Loop with default values
     */
    public Loop() {
        // Default initialization (matches C++ constructor)
    }
    
    /**
     * Adds a new bol to the loop
     */
    public boolean addBol() {
        Bol bol = new Bol();
        bols.add(bol);
        bolCount++;
        updateMatraCount();
        return true;
    }
    
    /**
     * Adds a specific bol to the loop
     */
    public boolean addBol(Bol bol) {
        if (bol == null) return false;
        bols.add(bol);
        bolCount++;
        updateMatraCount();
        return true;
    }
    
    /**
     * Gets the bol at specified grid position
     */
    public int getBol(int col, int row) {
        for (int x = 0; x < bolCount; x++) {
            Bol bol = bols.get(x);
            if (bol.getCol() == col && bol.getRow() == row) {
                return x;
            }
        }
        return Bol.BOL_NONE;
    }
    
    /**
     * Gets the bol object at specified index
     */
    public Bol getBolAt(int index) {
        if (index >= 0 && index < bols.size()) {
            return bols.get(index);
        }
        return null;
    }
    
    /**
     * Deletes a bol at specified index
     */
    public boolean deleteBol(int nbol) {
        if (nbol >= 0 && nbol < bols.size()) {
            bols.remove(nbol);
            bolCount--;
            updateMatraCount();
            return true;
        }
        return false;
    }
    
    /**
     * Updates the matra count based on current bol count
     */
    private void updateMatraCount() {
        this.matraCount = bolCount / maatraBol;
    }
    
    /**
     * Gets the matra count as a formatted string
     */
    @JsonIgnore
    public String getMatraCount() {
        int frac = bolCount % maatraBol;
        matraCount = bolCount / maatraBol;
        
        if (frac > 0) {
            return String.format("%d-%d/%d Maatras", matraCount, frac, maatraBol);
        } else {
            return String.format("%d Maatras", matraCount);
        }
    }
    
    /**
     * Gets the numeric matra count
     */
    @JsonIgnore
    public int getMatraCountNumeric() {
        return matraCount;
    }
    
    /**
     * Deselects all bols in this loop
     */
    public void deSelectAll() {
        for (Bol bol : bols) {
            bol.setSelected(false);
        }
    }
    
    /**
     * Clones another loop's properties and bols
     */
    public void clone(Loop srcLoop) {
        this.maatraBol = srcLoop.maatraBol;
        this.repeatCount = srcLoop.repeatCount;
        this.accompaniment = srcLoop.accompaniment;
        this.loopBpm = srcLoop.loopBpm;
        this.loopName = srcLoop.loopName;
        this.note = srcLoop.note;
        
        // Clear existing bols and clone from source
        this.bols.clear();
        this.bolCount = 0;
        
        for (Bol srcBol : srcLoop.bols) {
            Bol newBol = new Bol();
            newBol.clone(srcBol);
            addBol(newBol);
        }
    }
    
    /**
     * Sets a bol at a specific grid position
     */
    public boolean setBolAt(int col, int row, int bolId, int bolStatus) {
        // Find existing bol at position
        int existingBolIndex = getBol(col, row);
        
        if (existingBolIndex != Bol.BOL_NONE) {
            // Update existing bol
            Bol bol = bols.get(existingBolIndex);
            bol.setBolType(bolId, col, row);
            bol.setBolStatus(bolStatus);
            return true;
        } else {
            // Create new bol at position
            Bol newBol = new Bol();
            newBol.setBolType(bolId, col, row);
            newBol.setBolStatus(bolStatus);
            return addBol(newBol);
        }
    }
    
    /**
     * Removes bol at specific grid position
     */
    public boolean removeBolAt(int col, int row) {
        int bolIndex = getBol(col, row);
        if (bolIndex != Bol.BOL_NONE) {
            return deleteBol(bolIndex);
        }
        return false;
    }
    
    /**
     * Gets the total duration of this loop in milliseconds
     */
    @JsonIgnore
    public double getDurationMs() {
        // Calculate based on BPM and number of beats
        double beatsPerMinute = loopBpm;
        double beatsPerSecond = beatsPerMinute / 60.0;
        double totalBeats = bolCount;
        return (totalBeats / beatsPerSecond) * 1000.0;
    }
    
    /**
     * Gets the time per beat in milliseconds
     */
    @JsonIgnore
    public double getBeatDurationMs() {
        double beatsPerMinute = loopBpm;
        double beatsPerSecond = beatsPerMinute / 60.0;
        return 1000.0 / beatsPerSecond;
    }
    
    /**
     * Validates the loop structure
     */
    @JsonIgnore
    public boolean isValid() {
        return bolCount >= 0 && loopBpm > 0 && maatraBol > 0 && repeatCount > 0;
    }
    
    // Getters and Setters
    public String getLoopName() { return loopName; }
    public void setLoopName(String loopName) { this.loopName = loopName; }
    
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    
    public int getMaatraBol() { return maatraBol; }
    public void setMaatraBol(int maatraBol) { 
        this.maatraBol = maatraBol;
        updateMatraCount();
    }
    
    public int getRepeatCount() { return repeatCount; }
    public void setRepeatCount(int repeatCount) { this.repeatCount = Math.max(1, repeatCount); }
    
    public int getBolCount() { return bolCount; }
    
    public int getLoopBpm() { return loopBpm; }
    public void setLoopBpm(int loopBpm) { this.loopBpm = Math.max(1, loopBpm); }
    
    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }
    
    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
    
    public int getAccompaniment() { return accompaniment; }
    public void setAccompaniment(int accompaniment) { this.accompaniment = accompaniment; }
    
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
    
    public List<Bol> getBols() { return new ArrayList<>(bols); }
    public void setBols(List<Bol> bols) { 
        this.bols = new ArrayList<>(bols);
        this.bolCount = this.bols.size();
        updateMatraCount();
    }
    
    @Override
    public String toString() {
        return "Loop{" +
                "loopName='" + loopName + '\'' +
                ", bolCount=" + bolCount +
                ", maatraBol=" + maatraBol +
                ", loopBpm=" + loopBpm +
                ", repeatCount=" + repeatCount +
                '}';
    }
}