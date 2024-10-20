package com.spectrasonic.SimpleTimer.utils;

public class TimeUtil {

    public static long parseTime(String timeString) {
        try {
            long time;
            char unit = timeString.charAt(timeString.length() - 1);
            long value = Long.parseLong(timeString.substring(0, timeString.length() - 1));

            switch (unit) {
                case 'h': time = value * 3600; break;
                case 'm': time = value * 60; break;
                case 's': time = value; break;
                default: return -1;
            }
            return time;
        } catch (Exception e) {
            return -1;
        }
    }
}
