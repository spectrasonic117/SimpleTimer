package com.spectrasonic.SimpleTimer;

import com.spectrasonic.SimpleTimer.commands.TimerCommand;
import com.spectrasonic.SimpleTimer.commands.TimerTabCompleter;
import com.spectrasonic.SimpleTimer.managers.TimerManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    private TimerManager timerManager;

    @Override
    public void onEnable() {
        timerManager = new TimerManager(this);

        // Register the command and its tab completer
        Objects.requireNonNull(getCommand("timer")).setExecutor(new TimerCommand(this, timerManager));
        Objects.requireNonNull(getCommand("timer")).setTabCompleter(new TimerTabCompleter());

        getLogger().info("SimpleTimer has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleTimer has been disabled!");
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }
}
