package com.spectrasonic.SimpleTimer.commands;

import com.spectrasonic.SimpleTimer.manager.TimerManager;
import com.spectrasonic.SimpleTimer.utils.ColorUtil;
import com.spectrasonic.SimpleTimer.utils.TimeUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimerCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;
    private final TimerManager timerManager;

    public TimerCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.timerManager = new TimerManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /timer <time> <title> <color>");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "version":
                sender.sendMessage(ChatColor.GREEN + "SimpleTimer v1.0 by Spectrasonic");
                return true;

            case "pause":
                if (timerManager.isRunning()) {
                    timerManager.pauseTimer();
                    sender.sendMessage(ChatColor.YELLOW + "Timer paused.");
                } else {
                    sender.sendMessage(ChatColor.RED + "No active timer to pause.");
                }
                return true;

            case "resume":
                if (timerManager.isPaused()) {
                    timerManager.resumeTimer();
                    sender.sendMessage(ChatColor.GREEN + "Timer resumed.");
                } else {
                    sender.sendMessage(ChatColor.RED + "No paused timer to resume.");
                }
                return true;

            case "cancel":
                if (timerManager.isRunning() || timerManager.isPaused()) {
                    timerManager.cancelTimer();
                    sender.sendMessage(ChatColor.RED + "Timer canceled and removed.");
                } else {
                    sender.sendMessage(ChatColor.RED + "No active or paused timer to cancel.");
                }
                return true;

            default:
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /timer <time> <title> <color>");
                    return false;
                }

                long time = TimeUtil.parseTime(args[0]);
                if (time <= 0) {
                    sender.sendMessage(ChatColor.RED + "Invalid time format. Use [number][h/m/s]");
                    return false;
                }

                String title = args[1];
                String colorName = args[2].toUpperCase();

                if (!ColorUtil.isValidColor(colorName)) {
                    sender.sendMessage(ChatColor.RED + "Invalid color. Available colors: " + ColorUtil.getValidColors());
                    return false;
                }

                timerManager.startTimer(time, title, ColorUtil.getColor(colorName));
                sender.sendMessage(ChatColor.GREEN + "Timer started for " + args[0] + " with title: " + title + " and color: " + colorName);
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("version", "pause", "resume", "cancel", "10s", "1m", "1h");
        } else if (args.length == 3) {
            return ColorUtil.getValidColorsList();
        }
        return new ArrayList<>();
    }
}