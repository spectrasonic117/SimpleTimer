package com.spectrasonic.SimpleTimer.utils;

import org.bukkit.boss.BarColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColorUtil {

    public static BarColor getColor(String colorName) {
        return BarColor.valueOf(colorName);
    }

    public static boolean isValidColor(String colorName) {
        try {
            BarColor.valueOf(colorName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static String getValidColors() {
        return Arrays.stream(BarColor.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    public static List<String> getValidColorsList() {
        return Arrays.stream(BarColor.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
