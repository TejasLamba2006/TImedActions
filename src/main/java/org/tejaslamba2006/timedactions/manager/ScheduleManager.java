package org.tejaslamba2006.timedactions.manager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.tejaslamba2006.timedactions.TimedActionsPlugin;
import org.tejaslamba2006.timedactions.model.Schedule;
import org.tejaslamba2006.timedactions.model.ScheduleMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manages all scheduled actions
 */
public class ScheduleManager {
    
    private final TimedActionsPlugin plugin;
    private final Map<String, Schedule> schedules = new HashMap<>();
    private final Map<String, BukkitTask> runningTasks = new HashMap<>();
    
    public ScheduleManager(TimedActionsPlugin plugin) {
        this.plugin = plugin;
        loadSchedules();
    }
    
    /**
     * Load schedules from config
     */
    public void loadSchedules() {
        schedules.clear();
        ConfigurationSection schedulesSection = plugin.getMainConfig().getConfigurationSection("schedules");
        
        if (schedulesSection == null) {
            plugin.getLogger().warning("No schedules section found in config!");
            return;
        }
        
        for (String scheduleName : schedulesSection.getKeys(false)) {
            try {
                ConfigurationSection scheduleSection = schedulesSection.getConfigurationSection(scheduleName);
                if (scheduleSection == null) continue;
                
                Schedule schedule = new Schedule();
                schedule.setName(scheduleName);
                schedule.setMode(ScheduleMode.fromString(scheduleSection.getString("mode", "real_time")));
                schedule.setInterval(scheduleSection.getString("interval", "1h"));
                schedule.setCommands(scheduleSection.getStringList("commands"));
                schedule.setBroadcast(scheduleSection.getString("broadcast"));
                schedule.setConsoleLog(scheduleSection.getBoolean("console-log", false));
                
                schedules.put(scheduleName, schedule);
                plugin.getLogger().info(String.format("Loaded schedule: %s (%s)", scheduleName, schedule.getDisplayInterval()));
                
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to load schedule '" + scheduleName + "': " + e.getMessage());
            }
        }
    }
    
    /**
     * Start all schedules
     */
    public void startAllSchedules() {
        for (Schedule schedule : schedules.values()) {
            startSchedule(schedule);
        }
    }
    
    /**
     * Stop all schedules
     */
    public void stopAllSchedules() {
        for (BukkitTask task : runningTasks.values()) {
            if (task != null && !task.isCancelled()) {
                task.cancel();
            }
        }
        runningTasks.clear();
    }
    
    /**
     * Start a specific schedule
     */
    public void startSchedule(Schedule schedule) {
        // Stop existing task if running
        stopSchedule(schedule.getName());
        
        try {
            long intervalTicks = schedule.getIntervalInTicks();
            
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    executeSchedule(schedule);
                }
            }.runTaskTimer(plugin, 0L, intervalTicks);
            
            runningTasks.put(schedule.getName(), task);
            plugin.getLogger().info("Started schedule: " + schedule.getName() + " (every " + schedule.getDisplayInterval() + ")");
            
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to start schedule '" + schedule.getName() + "': " + e.getMessage());
        }
    }
    
    /**
     * Stop a specific schedule
     */
    public void stopSchedule(String scheduleName) {
        BukkitTask task = runningTasks.remove(scheduleName);
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }
    
    /**
     * Execute a schedule (run its commands and broadcast)
     */
    public void executeSchedule(Schedule schedule) {
        try {
            // Log to console if enabled
            if (schedule.isConsoleLog() || plugin.isGlobalConsoleLogEnabled()) {
                plugin.getLogger().info("Executing schedule: " + schedule.getName());
            }
            
            // Execute commands
            executeCommands(schedule);
            
            // Send broadcast if configured
            if (schedule.getBroadcast() != null && !schedule.getBroadcast().trim().isEmpty()) {
                broadcastMessage(schedule.getBroadcast());
            }
            
        } catch (Exception e) {
            plugin.getLogger().severe("Error executing schedule '" + schedule.getName() + "': " + e.getMessage());
        }
    }
    
    /**
     * Execute commands for a schedule
     */
    private void executeCommands(Schedule schedule) {
        List<String> commands = schedule.getCommands();
        if (commands == null || commands.isEmpty()) {
            return;
        }
        
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            executeCommandsForOfflineMode(commands);
        } else {
            executeCommandsForPlayers(commands);
        }
    }
    
    /**
     * Execute commands when players are online
     */
    private void executeCommandsForPlayers(List<String> commands) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (String command : commands) {
                String processedCommand = plugin.getPlaceholderManager().processCommandForPlayer(command, player);
                executeCommand(processedCommand);
            }
        }
    }
    
    /**
     * Execute commands when no players are online
     */
    private void executeCommandsForOfflineMode(List<String> commands) {
        for (String command : commands) {
            // Only execute if command doesn't contain player placeholders
            if (!command.contains("%player_name%") && !command.contains("%")) {
                executeCommand(command);
            }
        }
    }
    
    /**
     * Execute a single command safely
     */
    private void executeCommand(String command) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            try {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            } catch (Exception e) {
                plugin.getLogger().warning(String.format("Failed to execute command '%s': %s", command, e.getMessage()));
            }
        });
    }
    
    /**
     * Broadcast a message to all players
     */
    private void broadcastMessage(String message) {
        String prefix = plugin.getBroadcastPrefix();
        String fullMessage = translateColorCodes(prefix + message);
        
        // Process placeholders for each player individually
        for (Player player : Bukkit.getOnlinePlayers()) {
            String processedMessage = plugin.getPlaceholderManager().processPlaceholders(fullMessage, player);
            player.sendMessage(processedMessage);
        }
    }
    
    /**
     * Translate color codes using & symbol
     */
    @SuppressWarnings("deprecation")
    private String translateColorCodes(String message) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }
    
    /**
     * Get a schedule by name
     */
    public Schedule getSchedule(String name) {
        return schedules.get(name);
    }
    
    /**
     * Get all schedule names
     */
    public Set<String> getScheduleNames() {
        return schedules.keySet();
    }
    
    /**
     * Get number of loaded schedules
     */
    public int getScheduleCount() {
        return schedules.size();
    }
    
    /**
     * Check if a schedule is running
     */
    public boolean isScheduleRunning(String name) {
        BukkitTask task = runningTasks.get(name);
        return task != null && !task.isCancelled();
    }
    
    /**
     * Manually trigger a schedule
     */
    public boolean triggerSchedule(String name) {
        Schedule schedule = schedules.get(name);
        if (schedule == null) {
            return false;
        }
        
        // Execute on main thread
        Bukkit.getScheduler().runTask(plugin, () -> executeSchedule(schedule));
        return true;
    }
}