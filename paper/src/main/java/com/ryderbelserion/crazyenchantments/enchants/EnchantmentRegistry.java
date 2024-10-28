package com.ryderbelserion.crazyenchantments.enchants;

import com.ryderbelserion.crazyenchantments.enchants.interfaces.CustomEnchantment;
import com.ryderbelserion.crazyenchantments.enchants.types.DisorderEnchantment;
import com.ryderbelserion.crazyenchantments.enchants.types.ViperEnchantment;
import com.ryderbelserion.vital.paper.api.files.CustomFile;
import com.ryderbelserion.vital.paper.api.files.FileManager;
import net.kyori.adventure.key.Key;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import java.util.HashMap;
import java.util.Map;

public class EnchantmentRegistry {

    private final Map<Key, CustomEnchantment> enchantments = new HashMap<>();

    private final FileManager fileManager;

    public EnchantmentRegistry(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void addEnchantment(final Key key, final CustomEnchantment enchantment) {
        this.enchantments.put(key, enchantment);
    }

    public void removeEnchantment(final Key key) {
        this.enchantments.remove(key);
    }

    public final CustomEnchantment getEnchantment(final Key key) {
        return this.enchantments.get(key);
    }

    public final boolean hasEnchantment(final Key key) {
        return this.enchantments.containsKey(key);
    }

    public void populateEnchantments() {
        final CustomFile file = this.fileManager.getFile("enchants.yml");

        if (file == null) return;

        final YamlConfiguration configuration = file.getConfiguration();

        if (configuration == null) return;

        final ConfigurationSection section = configuration.getConfigurationSection("enchantments");

        if (section == null) return;

        for (final String value : section.getKeys(false)) {
            final ConfigurationSection enchantmentSection = section.getConfigurationSection(value);

            if (enchantmentSection == null) continue;

            CustomEnchantment enchantment = null;

            Key key = null;

            switch (value) {
                case "viper" -> {
                    enchantment = new ViperEnchantment(enchantmentSection);

                    key = ViperEnchantment.viper_key;
                }
            }

            if (enchantment == null) continue;

            addEnchantment(key, enchantment);
        }
    }

    public void populateCurses() {
        final CustomFile file = this.fileManager.getFile("curses.yml");

        if (file == null) return;

        final YamlConfiguration configuration = file.getConfiguration();

        if (configuration == null) return;

        final ConfigurationSection section = configuration.getConfigurationSection("enchantments");

        if (section == null) return;

        for (final String value : section.getKeys(false)) {
            final ConfigurationSection enchantmentSection = section.getConfigurationSection(value);

            if (enchantmentSection == null) continue;

            CustomEnchantment enchantment = null;

            Key key = null;

            switch (value) {
                case "disorder" -> {
                    enchantment = new DisorderEnchantment(enchantmentSection);

                    key = DisorderEnchantment.disorder_key;
                }
            }

            if (enchantment == null) continue;

            addEnchantment(key, enchantment);
        }
    }
}