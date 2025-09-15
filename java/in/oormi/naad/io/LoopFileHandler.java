package in.oormi.naad.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import in.oormi.naad.core.Loop;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles loading and saving of individual Loop objects to/from JSON files.
 */
public class LoopFileHandler {
    
    private final ObjectMapper objectMapper;
    private static final String FILE_EXTENSION = ".loop";
    
    public LoopFileHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    /**
     * Saves a loop to a JSON file
     */
    public boolean saveLoop(Loop loop, String filePath) {
        if (loop == null || filePath == null) {
            return false;
        }
        
        try {
            String finalPath = ensureCorrectExtension(filePath);
            Path path = Paths.get(finalPath);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            
            objectMapper.writeValue(new File(finalPath), loop);
            System.out.println("Loop saved to: " + finalPath);
            return true;
            
        } catch (IOException e) {
            System.err.println("Error saving loop: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Loads a loop from a JSON file
     */
    public Loop loadLoop(String filePath) {
        if (filePath == null) {
            return null;
        }
        
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("Loop file not found: " + filePath);
                return null;
            }
            
            Loop loop = objectMapper.readValue(file, Loop.class);
            System.out.println("Loop loaded from: " + filePath);
            return loop;
            
        } catch (IOException e) {
            System.err.println("Error loading loop: " + e.getMessage());
            return null;
        }
    }
    
    private String ensureCorrectExtension(String filePath) {
        if (!filePath.toLowerCase().endsWith(FILE_EXTENSION)) {
            return filePath + FILE_EXTENSION;
        }
        return filePath;
    }
}