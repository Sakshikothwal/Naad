package in.oormi.naad.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a tabla syllable (Bol) with its properties and state.
 * Based on the C++ CBol class specification.
 */
public class Bol {
    
    // Constants from C++ header
    public static final int BOL_NONE = 0xFFFF;
    public static final int BOL_SILENCE = 0xFFFE;
    
    // Status constants
    public static final int BOL_NORMAL = 0;
    public static final int BOL_SAM = 1;
    public static final int BOL_TALI = 2;
    public static final int BOL_KHALI = 3;
    
    // Maximum values
    public static final int MAXBOLS = 12;
    public static final int MAXBOLVARS = 10;
    public static final int MAXSTATUS = 4;
    
    // Bol names array (from C++ BOLS definition)
    private static final String[] BOLS = {
        "Na", "Ta", "Ti", "Tin", "Tun", "Dha", "Dhin", "Dhit", "Dhun", "Ga", "Ge", "Ke"
    };
    
    // Status names array (from C++ BOLSTATUS definition)
    private static final String[] BOLSTATUS = {"", "~S~", "~T~", "~K~"};
    
    // Bol variations array (from C++ BOLVARS definition)
    private static final String[] BOLVARS = {
        // Na variations (000-009)
        "Na01", "Na02", "Na03", "Na04", "Na05", "Na06", "Na07", "Na08", "Na09", "Na10",
        // Ta variations (010-019)
        "Ta01", "Ta02", "Ta03", "Ta04", "Ta05", "Ta06", "Ta07", "Ta08", "Ta09", "Ta10",
        // Ti variations (020-029)
        "Ti01", "Ti02", "Ti03", "Ti04", "Ti05", "Ti06", "Ti07", "Ti08", "Ti09", "Ti10",
        // Tin variations (030-039)
        "Tin01", "Tin02", "Tin03", "Tin04", "Tin05", "Tin06", "Tin07", "Tin08", "Tin09", "Tin10",
        // Tun variations (040-049)
        "Tun01", "Tun02", "Tun03", "Tun04", "Tun05", "Tun06", "Tun07", "Tun08", "Tun09", "Tun10",
        // Dha variations (050-059)
        "Dha01", "Dha02", "Dha03", "Dha04", "Dha05", "Dha06", "Dha07", "Dha08", "Dha09", "Dha10",
        // Dhin variations (060-069)
        "Dhin01", "Dhin02", "Dhin03", "Dhin04", "Dhin05", "Dhin06", "Dhin07", "Dhin08", "Dhin09", "Dhin10",
        // Dhit variations (070-079)
        "Dhit01", "Dhit02", "Dhit03", "Dhit04", "Dhit05", "Dhit06", "Dhit07", "Dhit08", "Dhit09", "Dhit10",
        // Dhun variations (080-089)
        "Dhun01", "Dhun02", "Dhun03", "Dhun04", "Dhun05", "Dhun06", "Dhun07", "Dhun08", "Dhun09", "Dhun10",
        // Ga variations (090-099)
        "Ga01", "Ga02", "Ga03", "Ga04", "Ga05", "Ga06", "Ga07", "Ga08", "Ga09", "Ga10",
        // Ge variations (100-109)
        "Ge01", "Ge02", "Ge03", "Ge04", "Ge05", "Ge06", "Ge07", "Ge08", "Ge09", "Ge10",
        // Ke variations (110-119)
        "Ke01", "Ke02", "Ke03", "Ke04", "Ke05", "Ke06", "Ke07", "Ke08", "Ke09", "Ke10"
    };
    
    // Instance fields (mapped from C++ members)
    private String bolName = "";
    private String bolName2 = "";
    private String bolStatusName = "";
    private int bolId = BOL_NONE;
    private int bolId2 = BOL_NONE;
    private int status = BOL_NORMAL;
    private int species = 0;
    private int row = 0;
    private int col = 0;
    private boolean selected = false;
    
    /**
     * Default constructor - initializes Bol with default values
     */
    public Bol() {
        // Default initialization (matches C++ constructor)
    }
    
    /**
     * Sets the main bol type with position information
     */
    public boolean setBolType(int id, int col, int row) {
        if (id > BOL_NONE) return false;
        
        this.bolId = id;
        this.row = row;
        this.col = col;
        this.species = id / MAXBOLVARS;
        
        updateBolName(id);
        return true;
    }
    
    /**
     * Sets the main bol type without position
     */
    public boolean setBolType(int id) {
        if (id > BOL_NONE) return false;
        
        this.bolId = id;
        this.species = id / MAXBOLVARS;
        
        updateBolName(id);
        return true;
    }
    
    /**
     * Sets the additional/secondary bol type
     */
    public boolean setBolAdditional(int id, int col, int row) {
        if (id > BOL_NONE) return false;
        
        this.bolId2 = id;
        this.species = id / MAXBOLVARS;
        
        updateBolName2(id);
        return true;
    }
    
    /**
     * Sets the bol status (Sam, Tali, Khali, etc.)
     */
    public boolean setBolStatus(int status) {
        this.status = status;
        if (status < MAXSTATUS) {
            this.bolStatusName = BOLSTATUS[status];
            return true;
        }
        return false;
    }
    
    /**
     * Clones another bol's properties
     */
    public void clone(Bol srcBol) {
        setBolType(srcBol.bolId, srcBol.col, srcBol.row);
        setBolStatus(srcBol.status);
    }
    
    /**
     * Updates the main bol name based on ID
     */
    private void updateBolName(int id) {
        if (id == BOL_NONE) {
            this.bolName = "";
        } else if (id == BOL_SILENCE) {
            this.bolName = "~ o ~";
        } else if (id >= 0 && id < BOLVARS.length) {
            this.bolName = BOLVARS[id];
        }
    }
    
    /**
     * Updates the secondary bol name based on ID
     */
    private void updateBolName2(int id) {
        if (id == BOL_NONE) {
            this.bolName2 = "";
        } else if (id == BOL_SILENCE) {
            this.bolName2 = "~ o ~";
        } else if (id >= 0 && id < BOLVARS.length) {
            this.bolName2 = BOLVARS[id];
        }
    }
    
    /**
     * Get the audio file path for this bol variation
     */
    @JsonIgnore
    public String getAudioFilePath(String basePath) {
        if (bolId == BOL_NONE || bolId == BOL_SILENCE) {
            return null;
        }
        
        // Extract base bol name (e.g., "Na" from "Na01")
        if (bolId >= 0 && bolId < BOLVARS.length) {
            String bolVar = BOLVARS[bolId];
            String baseBol = bolVar.substring(0, bolVar.length() - 2); // Remove "01" suffix
            return basePath + "/" + baseBol + "/" + bolVar + ".wav";
        }
        
        return null;
    }
    
    // Getters and Setters
    public String getBolName() { return bolName; }
    public void setBolName(String bolName) { this.bolName = bolName; }
    
    public String getBolName2() { return bolName2; }
    public void setBolName2(String bolName2) { this.bolName2 = bolName2; }
    
    public String getBolStatusName() { return bolStatusName; }
    public void setBolStatusName(String bolStatusName) { this.bolStatusName = bolStatusName; }
    
    public int getBolId() { return bolId; }
    public void setBolId(int bolId) { this.bolId = bolId; }
    
    public int getBolId2() { return bolId2; }
    public void setBolId2(int bolId2) { this.bolId2 = bolId2; }
    
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    
    public int getSpecies() { return species; }
    public void setSpecies(int species) { this.species = species; }
    
    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }
    
    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
    
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
    
    @Override
    public String toString() {
        return "Bol{" +
                "bolName='" + bolName + '\'' +
                ", bolId=" + bolId +
                ", status=" + status +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}