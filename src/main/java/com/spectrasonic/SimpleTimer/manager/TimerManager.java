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
    private String timerTitle;

    public TimerManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startTimer(long timeInSeconds, String title, BarColor color) {
        // Cancelar cualquier timer existente
        cancelTimer();

        // Guardar detalles del timer
        this.timerTitle = title;

        // Crear nuevo BossBar
        bossBar = Bukkit.createBossBar(title, color, BarStyle.SOLID);
        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);

        initialTime = timeInSeconds;
        timeLeft = timeInSeconds;
        paused = false;

        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                // Verificar que el bossBar no sea null antes de usarlo
                if (bossBar == null) {
                    cancel();
                    return;
                }

                if (paused) return;

                if (timeLeft <= 0) {
                    endTimer();
                    return;
                }

                String formattedTime = formatTime(timeLeft);
                bossBar.setTitle(timerTitle + " (" + formattedTime + ")");
                bossBar.setProgress((double) timeLeft / initialTime);
                timeLeft--;
            }
        };
        timerTask.runTaskTimer(plugin, 0, 20);
    }

    private void endTimer() {
        if (bossBar != null) {
            bossBar.setTitle("Time's up!");
            bossBar.setProgress(0);
            playEndSound();

            // Eliminar el BossBar después de un breve retraso
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (bossBar != null) {
                        bossBar.removeAll();
                        bossBar = null;
                    }
                }
            }.runTaskLater(plugin, 100); // 5 segundos
        }

        // Cancelar la tarea del timer
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    public void pauseTimer() {
        if (timerTask != null && bossBar != null) {
            paused = true;
            bossBar.setTitle(timerTitle + " (Paused)");
        }
    }

    public void resumeTimer() {
        if (paused && bossBar != null) {
            paused = false;
            bossBar.setTitle(timerTitle);
        }
    }

    public void cancelTimer() {
        // Detener la tarea del timer
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        // Eliminar el BossBar
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar = null;
        }

        // Restablecer variables
        timeLeft = 0;
        paused = false;
    }

    public boolean isRunning() {
        return bossBar != null && timerTask != null && !paused;
    }

    public boolean isPaused() {
        return paused && bossBar != null;
    }

    private void playEndSound() {
        Bukkit.getOnlinePlayers().forEach(player ->
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f)
        );
    }

    private String formatTime(long timeInSeconds) {
        long hours = timeInSeconds / 3600;
        long minutes = (timeInSeconds % 3600) / 60;
        long seconds = timeInSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}