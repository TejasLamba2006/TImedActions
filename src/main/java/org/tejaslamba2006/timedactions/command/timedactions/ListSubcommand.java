package org.tejaslamba2006.timedactions.command.timedactions;

import com.github.sirblobman.api.command.Command;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.tejaslamba2006.timedactions.TimedActionsPlugin;
import org.tejaslamba2006.timedactions.model.Schedule;

import java.util.Collections;
import java.util.List;

public class ListSubcommand extends Command {
    private final TimedActionsPlugin plugin;
    
    public ListSubcommand(@NonNull TimedActionsPlugin plugin) {
        super(plugin, "list");
        this.plugin = plugin;
        setPermissionName("timedactions.admin");
    }

    @Override
    protected @NonNull List<String> onTabComplete(@NonNull CommandSender sender, String @NonNull [] args) {
        return Collections.emptyList();
    }

    @Override
    protected boolean execute(@NonNull CommandSender commandSender, String @NonNull [] args) {
        int scheduleCount = plugin.getScheduleManager().getScheduleCount();
        
        if (scheduleCount == 0) {
            commandSender.sendMessage("§6[TimedActions] §cNo schedules configured!");
            return true;
        }
        
        displaySchedulesList(commandSender, scheduleCount);
        return true;
    }
    
    private void displaySchedulesList(CommandSender sender, int scheduleCount) {
        sender.sendMessage("§6[TimedActions] §eActive Schedules (" + scheduleCount + "):");
        sender.sendMessage("§8§m----------------------------------");
        
        for (String scheduleName : plugin.getScheduleManager().getScheduleNames()) {
            displayScheduleInfo(sender, scheduleName);
        }
        
        sender.sendMessage("§8§m----------------------------------");
    }
    
    private void displayScheduleInfo(CommandSender sender, String scheduleName) {
        Schedule schedule = plugin.getScheduleManager().getSchedule(scheduleName);
        if (schedule == null) return;
        
        boolean isRunning = plugin.getScheduleManager().isScheduleRunning(scheduleName);
        String status = isRunning ? "§aRunning" : "§cStopped";
        
        sender.sendMessage(String.format("§f• §e%s §7- %s §7(§f%s§7) §7[%s§7]", 
            scheduleName, 
            schedule.getMode().getConfigValue(),
            schedule.getDisplayInterval(),
            status));
        
        displayScheduleDetails(sender, schedule);
    }
    
    private void displayScheduleDetails(CommandSender sender, Schedule schedule) {
        int commandCount = schedule.getCommands() != null ? schedule.getCommands().size() : 0;
        sender.sendMessage(String.format("  §7Commands: §f%d  §7Broadcast: §f%s  §7Console Log: §f%s",
            commandCount,
            schedule.getBroadcast() != null ? "Yes" : "No",
            schedule.isConsoleLog() ? "Yes" : "No"));
    }
}