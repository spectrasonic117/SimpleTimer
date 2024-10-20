package com.spectrasonic.SimpleTimer.managers;

import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class TimerManager {

    private final Plugin plugin;
    private final Map<String, BossBar> timers = new HashMap<>();
    private BukkitRunnable timerTask;
    private boolean isPaused = false;

    public TimerManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void startTimer(Player player, String label, long timeInSeconds, BarColor color) {
        if (timers.containsKey(label)) {
            player.sendMessage(ChatColor.RED + "A timer with this label already exists.");
            return;
        }

        BossBar bossBar = plugin.getServer().createBossBar(label + ": " + formatTime(timeInSeconds), color, BarStyle.SOLID);
        bossBar.setVisible(true);
        bossBar.addPlayer(player);

        timers.put(label, bossBar);

        timerTask = new BukkitRunnable() {
            long remainingTime = timeInSeconds;

            @Override
            public void run() {
                if (!isPaused) {
                    remainingTime--;
                    bossBar.setTitle(label + ": " + formatTime(remainingTime));
                    bossBar.setProgress((double) remainingTime / timeInSeconds);

                    if (remainingTime <= 0) {
                        cancelTimer(player, label);
                        bossBar.setTitle(ChatColor.RED + "Timer Finished");
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                        bossBar.setColor(BarColor.RED);
                    }
                }
            }
        };
        timerTask.runTaskTimer(plugin, 0L, 20L); // 1 second intervals
    }

    public void pauseTimer(Player player, String label) {
        isPaused = true;
        player.sendMessage(ChatColor.YELLOW + "Timer paused.");
    }

    public void cancelTimer(Player player, String label) {
        BossBar bossBar = timers.get(label);
        if (bossBar != null) {
            bossBar.removeAll();
            timers.remove(label);
            player.sendMessage(ChatColor.GREEN + "Timer cancelled.");
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private String formatTime(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
