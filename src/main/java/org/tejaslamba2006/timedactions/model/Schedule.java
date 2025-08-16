package org.tejaslamba2006.timedactions.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Represents a scheduled action configuration
 */
@Data
@EqualsAndHashCode(of = "name")
public class Schedule {
    
    private String name;
    private ScheduleMode mode;
    private String interval;
    private List<String> commands;
    private String broadcast;
    private boolean consoleLog;
    
    /**
     * Parse interval string to ticks for real_time mode
     * Supports: s (seconds), m (minutes), h (hours), d (days)
     */
    public long getIntervalInTicks() {
        if (mode == ScheduleMode.MINECRAFT_DAYS) {
            return Long.parseLong(interval) * 24000L; // 24000 ticks = 1 minecraft day
        }
        
        // Parse real_time intervals
        String timeStr = interval.toLowerCase();
        long multiplier = 1L;
        
        if (timeStr.endsWith("s")) {
            multiplier = 20L; // 20 ticks per second
            timeStr = timeStr.substring(0, timeStr.length() - 1);
        } else if (timeStr.endsWith("m")) {
            multiplier = 20L * 60L; // 1200 ticks per minute
            timeStr = timeStr.substring(0, timeStr.length() - 1);
        } else if (timeStr.endsWith("h")) {
            multiplier = 20L * 60L * 60L; // 72000 ticks per hour
            timeStr = timeStr.substring(0, timeStr.length() - 1);
        } else if (timeStr.endsWith("d")) {
            multiplier = 20L * 60L * 60L * 24L; // 1728000 ticks per day
            timeStr = timeStr.substring(0, timeStr.length() - 1);
        }
        
        try {
            return Long.parseLong(timeStr) * multiplier;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid interval format: " + interval);
        }
    }
    
    /**
     * Get interval for display purposes
     */
    public String getDisplayInterval() {
        if (mode == ScheduleMode.MINECRAFT_DAYS) {
            return interval + " minecraft day(s)";
        }
        return interval.replaceAll("(\\d+)([smhd])", "$1 $2")
                .replace("s", "second(s)")
                .replace("m", "minute(s)")
                .replace("h", "hour(s)")
                .replace("d", "day(s)");
    }
}