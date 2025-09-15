package in.oormi.naad.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import in.oormi.naad.core.Composition;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles loading and saving of Composition objects to/from JSON files.
 * Implements file I/O operations as specified in the C1 intern tasks.
 */
public class CompositionFileHandler {
    
    private final ObjectMapper objectMapper;
    private static final String FILE_EXTENSION = ".naad";
    private static final String BACKUP_EXTENSION = ".bak";
    
    public CompositionFileHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    /**
     * Saves a composition to a JSON file
     */
    public boolean saveComposition(Composition composition, String filePath) {
        if (composition == null || filePath == null) {
            return false;
        }
        
        try {
            // Ensure file has correct extension
            String finalPath = ensureCorrectExtension(filePath);
            
            // Create backup if file exists
            createBackup(finalPath);
            
            // Create parent directories if they don't exist
            Path path = Paths.get(finalPath);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            
            // Write composition to file
            objectMapper.writeValue(new File(finalPath), composition);
            
            System.out.println("Composition saved to: " + finalPath);
            return true;
            
        } catch (IOException e) {
            System.err.println("Error saving composition: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Loads a composition from a JSON file
     */
    public Composition loadComposition(String filePath) {
        if (filePath == null) {
            return null;
        }
        
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("Composition file not found: " + filePath);
                return null;
            }
            
            Composition composition = objectMapper.readValue(file, Composition.class);
            System.out.println("Composition loaded from: " + filePath);
            return composition;
            
        } catch (IOException e) {
            System.err.println("Error loading composition: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Exports composition to a different format (JSON with different structure)
     */
    public boolean exportComposition(Composition composition, String filePath, String format) {
        if (composition == null || filePath == null) {
            return false;
        }
        
        try {
            switch (format.toLowerCase()) {
                case "json":
                    return saveComposition(composition, filePath);
                    
                case "compact":
                    // Compact JSON format
                    ObjectMapper compactMapper = new ObjectMapper();
                    compactMapper.registerModule(new JavaTimeModule());
                    compactMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    compactMapper.writeValue(new File(filePath), composition);
                    return true;
                    
                default:
                    System.err.println("Unsupported export format: " + format);
                    return false;
            }
            
        } catch (IOException e) {
            System.err.println("Error exporting composition: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates if a file is a valid composition file
     */
    public boolean isValidCompositionFile(String filePath) {
        try {
            Composition composition = loadComposition(filePath);
            return composition != null && composition.isValid();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Gets file information for a composition file
     */
    public CompositionFileInfo getFileInfo(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            
            CompositionFileInfo info = new CompositionFileInfo();
            info.filePath = filePath;
            info.fileName = file.getName();
            info.fileSize = file.length();
            info.lastModified = file.lastModified();
            info.isReadable = file.canRead();
            info.isWritable = file.canWrite();
            
            // Try to get composition name
            try {
                Composition comp = loadComposition(filePath);
                if (comp != null) {
                    info.compositionName = comp.getCompositionName();
                    info.composerName = comp.getComposerName();
                    info.loopCount = comp.getLoopCount();
                    info.isValid = comp.isValid();
                }
            } catch (Exception e) {
                info.isValid = false;
            }
            
            return info;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Creates a backup of an existing file
     */
    private void createBackup(String filePath) {
        try {
            File originalFile = new File(filePath);
            if (originalFile.exists()) {
                File backupFile = new File(filePath + BACKUP_EXTENSION);
                Files.copy(originalFile.toPath(), backupFile.toPath(), 
                          java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not create backup: " + e.getMessage());
        }
    }
    
    /**
     * Ensures the file path has the correct extension
     */
    private String ensureCorrectExtension(String filePath) {
        if (!filePath.toLowerCase().endsWith(FILE_EXTENSION)) {
            return filePath + FILE_EXTENSION;
        }
        return filePath;
    }
    
    /**
     * Inner class to hold file information
     */
    public static class CompositionFileInfo {
        public String filePath;
        public String fileName;
        public long fileSize;
        public long lastModified;
        public boolean isReadable;
        public boolean isWritable;
        public boolean isValid;
        public String compositionName;
        public String composerName;
        public int loopCount;
        
        @Override
        public String toString() {
            return "CompositionFileInfo{" +
                    "fileName='" + fileName + '\'' +
                    ", compositionName='" + compositionName + '\'' +
                    ", composerName='" + composerName + '\'' +
                    ", loopCount=" + loopCount +
                    ", fileSize=" + fileSize +
                    ", isValid=" + isValid +
                    '}';
        }
    }
}