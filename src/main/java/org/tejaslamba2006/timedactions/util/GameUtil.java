package org.tejaslamba2006.timedactions.util;

import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * Utility class for game-related functions
 */
public final class GameUtil {
    
    private GameUtil() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Get the current Minecraft day for the main world
     * @return The current day number (starting from day 0)
     */
    public static long getCurrentMinecraftDay() {
        World world = Bukkit.getWorlds().get(0); // Main world
        return world.getFullTime() / 24000L; // 24000 ticks = 1 day
    }
    
    /**
     * Get the current Minecraft time of day
     * @return The current time in ticks (0-23999)
     */
    public static long getCurrentMinecraftTime() {
        World world = Bukkit.getWorlds().get(0); // Main world
        return world.getTime();
    }
    
    /**
     * Check if it's currently day time in Minecraft
     * @return true if it's day (0-12000 ticks), false if night
     */
    public static boolean isDay() {
        long time = getCurrentMinecraftTime();
        return time >= 0 && time < 12000;
    }
    
    /**
     * Get a formatted time string for display
     * @return Human-readable time string
     */
    public static String getFormattedMinecraftTime() {
        long time = getCurrentMinecraftTime();
        long hours = ((time + 6000) / 1000) % 24; // Minecraft day starts at 6 AM
        long minutes = ((time + 6000) % 1000) * 60 / 1000;
        
        return String.format("%02d:%02d", hours, minutes);
    }
}