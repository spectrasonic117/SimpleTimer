package com.spectrasonic.SimpleTimer;

import com.spectrasonic.SimpleTimer.commands.TimerCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("timer")).setExecutor(new TimerCommand(this));
        Objects.requireNonNull(getCommand("timer")).setTabCompleter(new TimerCommand(this));

        getLogger().info("SimpleTimer Plugin Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleTimer Plugin Disabled");
    }
}
