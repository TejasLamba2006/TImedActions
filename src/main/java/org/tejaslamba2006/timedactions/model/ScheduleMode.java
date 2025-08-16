package org.tejaslamba2006.timedactions.model;

/**
 * Enum for schedule modes
 */
public enum ScheduleMode {
    MINECRAFT_DAYS("minecraft_days"),
    REAL_TIME("real_time");
    
    private final String configValue;
    
    ScheduleMode(String configValue) {
        this.configValue = configValue;
    }
    
    public String getConfigValue() {
        return configValue;
    }
    
    public static ScheduleMode fromString(String value) {
        for (ScheduleMode mode : values()) {
            if (mode.configValue.equalsIgnoreCase(value)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown schedule mode: " + value);
    }
}