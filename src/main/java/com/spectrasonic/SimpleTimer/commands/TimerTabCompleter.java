package com.spectrasonic.SimpleTimer.commands;

import org.bukkit.boss.BarColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimerTabCompleter implements TabCompleter {

    private final List<String> COMMANDS = Arrays.asList("start", "pause", "cancel", "version");
    private final List<String> COLORS = new ArrayList<>();

    public TimerTabCompleter() {
        for (BarColor color : BarColor.values()) {
            COLORS.add(color.name().toLowerCase());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return filterResults(args[0], COMMANDS);
        } else if (args[0].equalsIgnoreCase("start") && args.length == 4) {
            return filterResults(args[3], COLORS); // Autocomplete for color
        }
        return new ArrayList<>();
    }

    private List<String> filterResults(String input, List<String> options) {
        List<String> results = new ArrayList<>();
        for (String option : options) {
            if (option.startsWith(input.toLowerCase())) {
                results.add(option);
            }
        }
        return results;
    }
}
