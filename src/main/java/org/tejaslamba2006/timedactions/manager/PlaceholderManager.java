package org.tejaslamba2006.timedactions.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.tejaslamba2006.timedactions.TimedActionsPlugin;

import java.lang.reflect.Method;

/**
 * Manages PlaceholderAPI integration
 */
public class PlaceholderManager {
    
    private final TimedActionsPlugin plugin;
    private final boolean placeholderAPIAvailable;
    private Method setPlaceholdersMethod;
    
    public PlaceholderManager(TimedActionsPlugin plugin) {
        this.plugin = plugin;
        this.placeholderAPIAvailable = initializePlaceholderAPI();
        
        if (placeholderAPIAvailable) {
            plugin.getLogger().info("PlaceholderAPI detected and enabled");
        } else {
            plugin.getLogger().info("PlaceholderAPI not found - placeholder support disabled");
        }
    }
    
    private boolean initializePlaceholderAPI() {
        try {
            if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                return false;
            }
            
            Class<?> placeholderAPI = Class.forName("me.clip.placeholderapi.PlaceholderAPI");
            setPlaceholdersMethod = placeholderAPI.getMethod("setPlaceholders", org.bukkit.OfflinePlayer.class, String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Process placeholders in a string for a specific player
     * @param text The text to process
     * @param player The player to process placeholders for (can be null)
     * @return The processed text
     */
    public String processPlaceholders(String text, Player player) {
        if (!placeholderAPIAvailable || !plugin.isPlaceholderAPIEnabled()) {
            return text;
        }
        
        try {
            return (String) setPlaceholdersMethod.invoke(null, player, text);
        } catch (Exception e) {
            plugin.getLogger().warning("Error processing placeholders: " + e.getMessage());
            return text;
        }
    }
    
    /**
     * Process placeholders in a command for all online players
     * @param command The command to process
     * @return The processed command for each player
     */
    public String processCommandForPlayer(String command, Player player) {
        String processed = command;
        
        // Always replace %player_name% even without PlaceholderAPI
        if (player != null) {
            processed = processed.replace("%player_name%", player.getName());
        }
        
        // Process other placeholders if PlaceholderAPI is available
        if (placeholderAPIAvailable && plugin.isPlaceholderAPIEnabled()) {
            processed = processPlaceholders(processed, player);
        }
        
        return processed;
    }
    
    /**
     * Check if PlaceholderAPI is available
     */
    public boolean isAvailable() {
        return placeholderAPIAvailable;
    }
}