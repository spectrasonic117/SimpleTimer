package com.spectrasonic.SimpleTimer.manager;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerManager {

    private final JavaPlugin plugin;
    private BossBar bossBar;
    private BukkitRunnable timerTask;
    private long timeLeft;
    private long initialTime;
    private boolean paused = false;

    public TimerManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startTimer(long timeInSeconds, String title, BarColor color) {
        if (bossBar != null) {
            bossBar.removeAll();
        }

        bossBar = Bukkit.createBossBar(title, color, BarStyle.SOLID);
        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);

        initialTime = timeInSeconds;
        timeLeft = timeInSeconds;
        paused = false;

        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (paused) return;

                if (timeLeft <= 0) {
                    bossBar.setTitle("Time's up!");
                    bossBar.setProgress(0);
                    playEndSound(); // Play the sound when the timer ends

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            bossBar.removeAll();
                            bossBar = null;
                        }
                    }.runTaskLater(plugin, 100); // 5 seconds later

                    cancel();
                    return;
                }

                bossBar.setTitle(title + " (" + timeLeft + " seconds left)");
                bossBar.setProgress((double) timeLeft / initialTime);
                timeLeft--;
            }
        };
        timerTask.runTaskTimer(plugin, 0, 20);
    }

    public void pauseTimer() {
        if (timerTask != null) {
            paused = true;
            bossBar.setTitle("Timer Paused");
        }
    }

    public void resumeTimer() {
        if (paused) {
            paused = false;
        }
    }

    public void cancelTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            bossBar.removeAll();
            bossBar = null;
            timerTask = null;
        }
    }

    public boolean isRunning() {
        return bossBar != null && timerTask != null && !paused;
    }

    public boolean isPaused() {
        return paused && timerTask != null;
    }

    private void playEndSound() {
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f));
    }
}