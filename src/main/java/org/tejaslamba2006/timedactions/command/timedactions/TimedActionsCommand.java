package org.tejaslamba2006.timedactions.command.timedactions;

import com.github.sirblobman.api.command.Command;
import com.github.sirblobman.api.language.replacer.StringReplacer;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.tejaslamba2006.timedactions.TimedActionsPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TimedActionsCommand extends Command {
    public TimedActionsCommand(@NonNull TimedActionsPlugin plugin) {
        super(plugin, "timedactions");
        setPermissionName("timedactions.admin");
        addSubCommand(new ReloadSubcommand(plugin));
        addSubCommand(new TriggerSubcommand(plugin));
        addSubCommand(new ListSubcommand(plugin));
    }
    
    @Override
    protected @NonNull List<String> onTabComplete(@NonNull CommandSender sender, String @NonNull [] args) {
        return Collections.emptyList();
    }

    @Override
    protected boolean execute(@NonNull CommandSender commandSender, String @NonNull [] strings) {
        Objects.requireNonNull(getLanguageManager()).sendMessage(commandSender, "help-title", new StringReplacer("%command%", "/timedactions"));
        Objects.requireNonNull(getLanguageManager()).sendMessage(commandSender, "commands.help");
        return true;
    }
}
