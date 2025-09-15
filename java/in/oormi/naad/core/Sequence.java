package in.oormi.naad.core;

/**
 * Manages the playback sequence of bols from a loop.
 * Based on the C++ CSequence class specification.
 */
public class Sequence {
    
    // Arrays to store bol sequence data
    private int[] bolIdSeq;
    private int[] bolId2Seq;
    private int[] bolVarSeq;
    private int[] bolStatusSeq;
    
    // Sequence properties
    private int bolSeqCount = 0;
    private int repeatSeqCount = 1;
    private int repeatCountDown = 1;
    
    /**
     * Default constructor
     */
    public Sequence() {
        // Initialize with null arrays
    }
    
    /**
     * Creates a bol sequence from a loop
     */
    public boolean createBolSequence(Loop loop) {
        if (loop.getBolCount() == 0) {
            bolSeqCount = 0;
            return true;
        }
        
        // Clean up existing arrays
        deleteBolSequence();
        
        // Allocate memory for sequence arrays
        try {
            bolIdSeq = new int[loop.getBolCount()];
            bolId2Seq = new int[loop.getBolCount()];
            bolVarSeq = new int[loop.getBolCount()];
            bolStatusSeq = new int[loop.getBolCount()];
        } catch (OutOfMemoryError e) {
            return false;
        }
        
        // Prepare bol sequence by scanning grid positions
        int nseq = 0;
        
        // Scan grid positions (row by row, then column by column)
        for (int by = 0; by < Loop.MR; by++) {
            for (int bx = 0; bx < Loop.MC; bx++) {
                int nbol = loop.getBol(bx, by);
                
                if (nbol != Bol.BOL_NONE) {
                    Bol bol = loop.getBolAt(nbol);
                    if (bol != null) {
                        bolIdSeq[nseq] = bol.getBolId();
                        bolId2Seq[nseq] = bol.getBolId2();
                        bolVarSeq[nseq] = bol.getBolId(); // Variation uses same ID initially
                        bolStatusSeq[nseq] = bol.getStatus();
                        nseq++;
                    }
                }
            }
        }
        
        bolSeqCount = nseq;
        repeatCountDown = repeatSeqCount = loop.getRepeatCount();
        return true;
    }
    
    /**
     * Creates a bol sequence directly from a list of bols
     */
    public boolean createBolSequence(java.util.List<Bol> bols, int repeatCount) {
        if (bols == null || bols.isEmpty()) {
            bolSeqCount = 0;
            return true;
        }
        
        // Clean up existing arrays
        deleteBolSequence();
        
        // Allocate memory for sequence arrays
        try {
            bolIdSeq = new int[bols.size()];
            bolId2Seq = new int[bols.size()];
            bolVarSeq = new int[bols.size()];
            bolStatusSeq = new int[bols.size()];
        } catch (OutOfMemoryError e) {
            return false;
        }
        
        // Fill sequence arrays
        for (int i = 0; i < bols.size(); i++) {
            Bol bol = bols.get(i);
            bolIdSeq[i] = bol.getBolId();
            bolId2Seq[i] = bol.getBolId2();
            bolVarSeq[i] = bol.getBolId();
            bolStatusSeq[i] = bol.getStatus();
        }
        
        bolSeqCount = bols.size();
        repeatCountDown = repeatSeqCount = repeatCount;
        return true;
    }
    
    /**
     * Cleans up allocated sequence arrays
     */
    public void deleteBolSequence() {
        bolIdSeq = null;
        bolId2Seq = null;
        bolVarSeq = null;
        bolStatusSeq = null;
        bolSeqCount = 0;
    }
    
    /**
     * Gets the bol ID at specified sequence position
     */
    public int getBolIdAt(int index) {
        if (bolIdSeq != null && index >= 0 && index < bolSeqCount) {
            return bolIdSeq[index];
        }
        return Bol.BOL_NONE;
    }
    
    /**
     * Gets the secondary bol ID at specified sequence position
     */
    public int getBolId2At(int index) {
        if (bolId2Seq != null && index >= 0 && index < bolSeqCount) {
            return bolId2Seq[index];
        }
        return Bol.BOL_NONE;
    }
    
    /**
     * Gets the bol variation at specified sequence position
     */
    public int getBolVarAt(int index) {
        if (bolVarSeq != null && index >= 0 && index < bolSeqCount) {
            return bolVarSeq[index];
        }
        return Bol.BOL_NONE;
    }
    
    /**
     * Gets the bol status at specified sequence position
     */
    public int getBolStatusAt(int index) {
        if (bolStatusSeq != null && index >= 0 && index < bolSeqCount) {
            return bolStatusSeq[index];
        }
        return Bol.BOL_NORMAL;
    }
    
    /**
     * Sets variation for a specific position
     */
    public boolean setBolVariationAt(int index, int variationId) {
        if (bolVarSeq != null && index >= 0 && index < bolSeqCount) {
            bolVarSeq[index] = variationId;
            return true;
        }
        return false;
    }
    
    /**
     * Decrements the repeat countdown
     */
    public boolean decrementRepeat() {
        if (repeatCountDown > 0) {
            repeatCountDown--;
            return true;
        }
        return false;
    }
    
    /**
     * Resets the repeat countdown
     */
    public void resetRepeat() {
        repeatCountDown = repeatSeqCount;
    }
    
    /**
     * Checks if sequence has more repeats to play
     */
    public boolean hasMoreRepeats() {
        return repeatCountDown > 0;
    }
    
    /**
     * Checks if sequence is valid and ready for playback
     */
    public boolean isValid() {
        return bolSeqCount > 0 && bolIdSeq != null;
    }
    
    /**
     * Gets a copy of the bol ID sequence
     */
    public int[] getBolIdSequence() {
        if (bolIdSeq == null) return new int[0];
        int[] copy = new int[bolSeqCount];
        System.arraycopy(bolIdSeq, 0, copy, 0, bolSeqCount);
        return copy;
    }
    
    /**
     * Gets a copy of the bol status sequence
     */
    public int[] getBolStatusSequence() {
        if (bolStatusSeq == null) return new int[0];
        int[] copy = new int[bolSeqCount];
        System.arraycopy(bolStatusSeq, 0, copy, 0, bolSeqCount);
        return copy;
    }
    
    // Getters and Setters
    public int getBolSeqCount() { return bolSeqCount; }
    
    public int getRepeatSeqCount() { return repeatSeqCount; }
    public void setRepeatSeqCount(int repeatSeqCount) { 
        this.repeatSeqCount = Math.max(1, repeatSeqCount);
        this.repeatCountDown = this.repeatSeqCount;
    }
    
    public int getRepeatCountDown() { return repeatCountDown; }
    
    @Override
    public String toString() {
        return "Sequence{" +
                "bolSeqCount=" + bolSeqCount +
                ", repeatSeqCount=" + repeatSeqCount +
                ", repeatCountDown=" + repeatCountDown +
                '}';
    }
}