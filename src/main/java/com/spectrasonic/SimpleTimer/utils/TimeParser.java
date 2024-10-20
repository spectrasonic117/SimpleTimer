package com.spectrasonic.SimpleTimer.utils;

public class TimeParser {

    public static long parseTime(String time) throws IllegalArgumentException {
        String[] units = time.split(":");
        if (units.length != 3) {
            throw new IllegalArgumentException("Invalid time format. Use hh:mm:ss");
        }
        long hours = Long.parseLong(units[0]) * 3600;
        long minutes = Long.parseLong(units[1]) * 60;
        long seconds = Long.parseLong(units[2]);
        return hours + minutes + seconds;
    }
}
