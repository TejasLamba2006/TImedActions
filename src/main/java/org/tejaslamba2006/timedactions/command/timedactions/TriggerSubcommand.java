package org.tejaslamba2006.timedactions.command.timedactions;

import com.github.sirblobman.api.command.Command;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.tejaslamba2006.timedactions.TimedActionsPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TriggerSubcommand extends Command {
    private final TimedActionsPlugin plugin;
    
    public TriggerSubcommand(@NonNull TimedActionsPlugin plugin) {
        super(plugin, "trigger");
        this.plugin = plugin;
        setPermissionName("timedactions.admin");
    }

    @Override
    protected @NonNull List<String> onTabComplete(@NonNull CommandSender sender, String @NonNull [] args) {
        if (args.length == 1) {
            List<String> scheduleNames = new ArrayList<>(plugin.getScheduleManager().getScheduleNames());
            String input = args[0].toLowerCase();
            return scheduleNames.stream()
                    .filter(name -> name.toLowerCase().startsWith(input))
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    protected boolean execute(@NonNull CommandSender commandSender, String @NonNull [] args) {
        if (args.length != 1) {
            commandSender.sendMessage("§6[TimedActions] §cUsage: /timedactions trigger <scheduleName>");
            commandSender.sendMessage("§6[TimedActions] §cAvailable schedules: " + 
                String.join(", ", plugin.getScheduleManager().getScheduleNames()));
            return true;
        }
        
        String scheduleName = args[0];
        
        if (!plugin.getScheduleManager().getScheduleNames().contains(scheduleName)) {
            commandSender.sendMessage("§6[TimedActions] §cSchedule '" + scheduleName + "' not found!");
            commandSender.sendMessage("§6[TimedActions] §cAvailable schedules: " + 
                String.join(", ", plugin.getScheduleManager().getScheduleNames()));
            return true;
        }
        
        try {
            boolean success = plugin.getScheduleManager().triggerSchedule(scheduleName);
            if (success) {
                commandSender.sendMessage("§6[TimedActions] §aTriggered schedule '" + scheduleName + "' successfully!");
            } else {
                commandSender.sendMessage("§6[TimedActions] §cFailed to trigger schedule '" + scheduleName + "'!");
            }
        } catch (Exception e) {
            commandSender.sendMessage("§6[TimedActions] §cError triggering schedule: " + e.getMessage());
            plugin.getLogger().severe("Error triggering schedule '" + scheduleName + "': " + e.getMessage());
        }
        
        return true;
    }
}