package com.spectrasonic.SimpleTimer.commands;

import com.spectrasonic.SimpleTimer.managers.TimerManager;
import com.spectrasonic.SimpleTimer.utils.TimeParser;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TimerCommand implements CommandExecutor {

    private final Plugin plugin;
    private final TimerManager timerManager;

    public TimerCommand(Plugin plugin, TimerManager timerManager) {
        this.plugin = plugin;
        this.timerManager = timerManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /timer <start/pause/cancel/version>");
            return false;
        }

        if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(ChatColor.GREEN + "SimpleTimer version: " + plugin.getDescription().getVersion());
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }

        Player player = (Player) sender;
        String timerLabel = args[1];

        switch (args[0].toLowerCase()) {
            case "start":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /timer start <label> <time> <color(optional)>");
                    return false;
                }
                long timeInSeconds = TimeParser.parseTime(args[2]); // Using TimeParser utility
                BarColor color = args.length > 3 ? parseColor(args[3]) : BarColor.WHITE;
                timerManager.startTimer(player, timerLabel, timeInSeconds, color);
                break;

            case "pause":
                timerManager.pauseTimer(player, timerLabel);
                break;

            case "cancel":
                timerManager.cancelTimer(player, timerLabel);
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Unknown command. Use /timer <start/pause/cancel/version>.");
                break;
        }
        return true;
    }

    private BarColor parseColor(String color) {
        try {
            return BarColor.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            return BarColor.WHITE; // Default color
        }
    }
}
