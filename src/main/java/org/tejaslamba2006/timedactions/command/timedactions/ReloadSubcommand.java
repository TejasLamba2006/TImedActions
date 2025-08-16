package org.tejaslamba2006.timedactions.command.timedactions;

import com.github.sirblobman.api.command.Command;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.tejaslamba2006.timedactions.TimedActionsPlugin;

import java.util.Collections;
import java.util.List;

public class ReloadSubcommand extends Command {
    private final TimedActionsPlugin plugin;
    
    public ReloadSubcommand(@NonNull TimedActionsPlugin plugin) {
        super(plugin, "reload");
        this.plugin = plugin;
        setPermissionName("timedactions.admin");
    }

    @Override
    protected @NonNull List<String> onTabComplete(@NonNull CommandSender sender, String @NonNull [] args) {
        return Collections.emptyList();
    }

    @Override
    protected boolean execute(@NonNull CommandSender commandSender, String @NonNull [] strings) {
        commandSender.sendMessage("§6[TimedActions] §eReloading configuration...");
        
        try {
            plugin.reload();
            
            commandSender.sendMessage("§6[TimedActions] §aConfiguration reloaded successfully!");
            commandSender.sendMessage("§6[TimedActions] §aLoaded " + plugin.getScheduleManager().getScheduleCount() + " schedule(s).");
            
        } catch (Exception e) {
            commandSender.sendMessage("§6[TimedActions] §cError reloading configuration: " + e.getMessage());
            plugin.getLogger().severe("Error reloading configuration: " + e.getMessage());
        }
        
        return true;
    }
}
