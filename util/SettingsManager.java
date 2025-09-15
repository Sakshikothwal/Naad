package in.oormi.naad.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages application settings and user preferences.
 */
public class SettingsManager {
    
    private static final String SETTINGS_FILE = "naad-settings.json";
    private final ObjectMapper objectMapper;
    private Map<String, Object> settings;
    
    public SettingsManager() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.settings = new HashMap<>();
        loadSettings();
        setDefaultSettings();
    }
    
    private void setDefaultSettings() {
        putIfAbsent("audio.defaultBpm", 180);
        putIfAbsent("audio.defaultMaatraBol", 4);
        putIfAbsent("ui.theme", "default");
        putIfAbsent("file.defaultSaveLocation", "./compositions");
        putIfAbsent("audio.samplePath", "./samples");
        putIfAbsent("playback.autoplay", false);
        putIfAbsent("composition.autoBackup", true);
    }
    
    private void putIfAbsent(String key, Object value) {
        if (!settings.containsKey(key)) {
            settings.put(key, value);
        }
    }
    
    public void loadSettings() {
        try {
            File file = new File(SETTINGS_FILE);
            if (file.exists()) {
                settings = objectMapper.readValue(file, Map.class);
            }
        } catch (IOException e) {
            System.err.println("Error loading settings: " + e.getMessage());
            settings = new HashMap<>();
        }
    }
    
    public void saveSettings() {
        try {
            objectMapper.writeValue(new File(SETTINGS_FILE), settings);
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }
    
    public Object getSetting(String key) {
        return settings.get(key);
    }
    
    public void setSetting(String key, Object value) {
        settings.put(key, value);
        saveSettings();
    }
    
    public int getIntSetting(String key, int defaultValue) {
        Object value = settings.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return defaultValue;
    }
    
    public String getStringSetting(String key, String defaultValue) {
        Object value = settings.get(key);
        return value instanceof String ? (String) value : defaultValue;
    }
    
    public boolean getBooleanSetting(String key, boolean defaultValue) {
        Object value = settings.get(key);
        return value instanceof Boolean ? (Boolean) value : defaultValue;
    }
}