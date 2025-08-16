package org.tejaslamba2006.timedactions;

import com.github.sirblobman.api.configuration.ConfigurationManager;
import com.github.sirblobman.api.language.LanguageManager;
import com.github.sirblobman.api.language.replacer.Replacer;
import com.github.sirblobman.api.plugin.ConfigurablePlugin;
import com.github.sirblobman.api.shaded.adventure.text.minimessage.MiniMessage;

import lombok.Getter;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.tejaslamba2006.timedactions.command.timedactions.TimedActionsCommand;
import org.tejaslamba2006.timedactions.manager.ScheduleManager;
import org.tejaslamba2006.timedactions.manager.PlaceholderManager;

@Getter
public final class TimedActionsPlugin extends ConfigurablePlugin {
    
    private static final String CONFIG_FILE = "config.yml";
    
    private static TimedActionsPlugin plugin;
    private ScheduleManager scheduleManager;
    private PlaceholderManager placeholderManager;
    
    public static Component mmString(String string) {
        String text = MiniMessage.miniMessage().serialize(MiniMessage.miniMessage().deserialize(string));
        return net.kyori.adventure.text.minimessage.MiniMessage.miniMessage().deserialize(text);
    }
    
    public static Component mm(CommandSender audience, String key) {
        String text = MiniMessage.miniMessage().serialize(plugin.getLanguageManager().getMessageWithPrefix(audience, key));
        return net.kyori.adventure.text.minimessage.MiniMessage.miniMessage().deserialize(text);
    }
    
    public static Component mm(CommandSender audience, String key, Replacer... replacer) {
        String text = MiniMessage.miniMessage().serialize(plugin.getLanguageManager().getMessageWithPrefix(audience, key, replacer));
        return net.kyori.adventure.text.minimessage.MiniMessage.miniMessage().deserialize(text);
    }
    
    private YamlConfiguration mainConfig;
    
    public static TimedActionsPlugin getInstance() {
        return plugin;
    }
    
    @SuppressWarnings("java:S6548") // Singleton pattern is required for Bukkit plugins
    private synchronized void setInstance() {
        if (plugin == null) {
            plugin = this;
        }
    }
    
    @Override
    public void onLoad() {
        this.loadConfig();
    }

    @Override
    public void onEnable() {
        setInstance();
        
        // Initialize managers
        this.placeholderManager = new PlaceholderManager(this);
        this.scheduleManager = new ScheduleManager(this);
        
        this.loadLanguages();
        this.registerCommands();
        
        // Start all schedules
        this.scheduleManager.startAllSchedules();
        
        getLogger().info("TimedActions plugin has been enabled.");
        getLogger().info("Loaded " + scheduleManager.getScheduleCount() + " schedule(s).");
    }

    public void reload() {
        // Stop all running schedules
        if (scheduleManager != null) {
            scheduleManager.stopAllSchedules();
        }
        
        this.loadConfig();
        this.loadLanguages();
        this.registerCommands();
        
        // Reinitialize managers
        this.scheduleManager = new ScheduleManager(this);
        
        // Restart all schedules
        this.scheduleManager.startAllSchedules();
        
        getLogger().info("TimedActions plugin has been reloaded.");
        getLogger().info("Loaded " + scheduleManager.getScheduleCount() + " schedule(s).");
    }

    @Override
    public void onDisable() {
        if (scheduleManager != null) {
            scheduleManager.stopAllSchedules();
        }
        getLogger().info("TimedActions plugin has been disabled.");
    }

    private void loadConfig() {
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.saveDefault(CONFIG_FILE);
        configurationManager.reload(CONFIG_FILE);
        this.mainConfig = configurationManager.get(CONFIG_FILE);
        getLogger().info("Configuration loaded");
    }

    private void loadLanguages() {
        LanguageManager languageManager = getLanguageManager();
        languageManager.saveDefaultLanguageFiles();
        languageManager.reloadLanguages();
        languageManager.onPluginEnable();
        getLogger().info("Languages loaded");
    }

    private void registerCommands() {
        new TimedActionsCommand(this).register();
        getLogger().info("Commands registered");
    }
    
    /**
     * Check if PlaceholderAPI is available and enabled in config
     */
    public boolean isPlaceholderAPIEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") && 
               mainConfig.getBoolean("settings.placeholder-api", true);
    }
    
    /**
     * Get the broadcast prefix from config
     */
    public String getBroadcastPrefix() {
        return mainConfig.getString("settings.broadcast-prefix", "&8[&6TimedActions&8]&r ");
    }
    
    /**
     * Check if global console logging is enabled
     */
    public boolean isGlobalConsoleLogEnabled() {
        return mainConfig.getBoolean("settings.global-console-log", true);
    }
}