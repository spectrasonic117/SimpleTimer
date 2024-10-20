package com.spectrasonic.SimpleTimer;

import com.spectrasonic.SimpleTimer.commands.TimerCommand;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    // Variables
    private static final String divider = "------------------------------";
    private static final String prefix = ChatColor.AQUA + "[SimpleTimer]" + ChatColor.RESET + " ";

    public final String pluginVersion = getDescription().getVersion();
    public final String pluginName = getDescription().getName();
    public final String pluginAuthor = getDescription().getAuthors().toString();

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("timer")).setExecutor(new TimerCommand(this));
        Objects.requireNonNull(getCommand("timer")).setTabCompleter(new TimerCommand(this));

        // Enable
        getServer().getConsoleSender().sendMessage(divider);
        getServer().getConsoleSender().sendMessage(prefix + ChatColor.WHITE + pluginName + ChatColor.GREEN + " Plugin Enabled!");
        getServer().getConsoleSender().sendMessage(prefix + ChatColor.LIGHT_PURPLE + "Version: " + ChatColor.AQUA + pluginVersion);
        getServer().getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "Developed by " + ChatColor.RED + pluginAuthor);
        getServer().getConsoleSender().sendMessage(divider);
    }

    @Override
    public void onDisable() {
        // Disable
        getServer().getConsoleSender().sendMessage(divider);
        getServer().getConsoleSender().sendMessage(prefix + ChatColor.RED + pluginName + "plugin Disabled!");
        getServer().getConsoleSender().sendMessage(divider);

    }


    public String getPluginVersion() {
        return pluginVersion;
    }

    public String getPluginAuthor() {
        return pluginAuthor;
    }

    public String getPluginName() {
        return pluginName;
    }

}
