package com.ryderbelserion.crazyenchantments.utils;

import org.bukkit.configuration.ConfigurationSection;
import java.util.List;

public class Methods {

    public static List<String> getStringList(final ConfigurationSection section, final String key, final List<String> defaultValue) {
        final List<String> list = section.contains(key) ? section.getStringList(key) : null;

        if (list == null) {
            section.set(key, defaultValue);

            return defaultValue;
        }

        return list;
    }

    public static boolean getBoolean(final ConfigurationSection section, final String key, final boolean defaultValue) {
        final boolean value = section.contains(key) && section.getBoolean(key);

        if (!value) {
            section.set(key, defaultValue);

            return defaultValue;
        }

        return true;
    }

    public static int getInt(final ConfigurationSection section, final String key, final int defaultValue) {
        final int value = section.contains(key) ? section.getInt(key) : -1;

        if (value == -1) {
            section.set(key, defaultValue);

            return defaultValue;
        }

        return value;
    }
}