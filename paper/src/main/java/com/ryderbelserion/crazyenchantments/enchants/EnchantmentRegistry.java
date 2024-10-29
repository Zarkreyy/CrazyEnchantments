package com.ryderbelserion.crazyenchantments.enchants;

import com.ryderbelserion.crazyenchantments.enchants.interfaces.CustomEnchantment;
import com.ryderbelserion.crazyenchantments.enchants.types.DisorderEnchant;
import com.ryderbelserion.crazyenchantments.enchants.types.ViperEnchant;
import com.ryderbelserion.crazyenchantments.utils.Methods;
import com.ryderbelserion.vital.paper.api.files.CustomFile;
import com.ryderbelserion.vital.paper.api.files.FileManager;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.tag.TagEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnchantmentRegistry {

    private final Map<Key, CustomEnchantment> enchantments = new HashMap<>();

    private final FileManager fileManager;
    private final ComponentLogger logger;

    public EnchantmentRegistry(final FileManager fileManager, final ComponentLogger logger) {
        this.fileManager = fileManager;

        this.logger = logger;
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

    public final Map<Key, CustomEnchantment> getEnchantments() {
        return this.enchantments;
    }

    public void populateEnchantments() {
        final CustomFile file = this.fileManager.getFile("enchants.yml");

        if (file == null) return;

        final YamlConfiguration configuration = file.getConfiguration();

        if (configuration == null) return;

        final ConfigurationSection section = configuration.getConfigurationSection("enchantments");

        if (section == null) return;

        for (final String value : section.getKeys(false)) {
            final ConfigurationSection enchant = section.getConfigurationSection(value);

            if (enchant == null || !enchant.getBoolean("enabled", true)) continue;

            CustomEnchantment enchantment = null;

            Key key = null;

            switch (value) {
                case "viper" -> {
                    enchantment = new ViperEnchant(
                            Methods.getBoolean(enchant, "enabled", true),
                            Methods.getInt(enchant, "anvil-cost", 1),
                            Methods.getInt(enchant, "max-level", 1),
                            Methods.getInt(enchant, "weight", 10),
                            EnchantmentRegistryEntry.EnchantmentCost
                                    .of(Methods.getInt(enchant, "minimum-cost.base", 20), Methods.getInt(enchant, "minimum-cost.extra-per-level", 1)),
                            EnchantmentRegistryEntry.EnchantmentCost
                                    .of(Methods.getInt(enchant, "maximum-cost.base", 60), Methods.getInt(enchant, "maximum-cost.extra-per-level", 1)),
                            Methods.getBoolean(enchant, "enchantment-table", false),
                            getTagsFromList(Methods.getStringList(
                                    enchant,
                                    "supported-items",
                                    List.of(
                                            "#minecraft:enchantable/armor"
                                    )
                            )),
                            enchant
                    );

                    key = ViperEnchant.viper_key;
                }
            }

            if (enchantment == null) continue;

            addEnchantment(key, enchantment);
        }

        file.save();
    }

    public void populateCurses() {
        final CustomFile file = this.fileManager.getFile("curses.yml");

        if (file == null) return;

        final YamlConfiguration configuration = file.getConfiguration();

        if (configuration == null) return;

        final ConfigurationSection section = configuration.getConfigurationSection("enchantments");

        if (section == null) return;

        for (final String value : section.getKeys(false)) {
            final ConfigurationSection enchant = section.getConfigurationSection(value);

            if (enchant == null || !enchant.getBoolean("enabled", true)) continue;

            CustomEnchantment enchantment = null;

            Key key = null;

            switch (value) {
                case "disorder" -> {
                    enchantment = new DisorderEnchant(
                            Methods.getBoolean(enchant, "enabled", true),
                            Methods.getInt(enchant, "anvil-cost", 1),
                            Methods.getInt(enchant, "max-level", 1),
                            Methods.getInt(enchant, "weight", 10),
                            EnchantmentRegistryEntry.EnchantmentCost
                                    .of(Methods.getInt(enchant, "minimum-cost.base", 20), Methods.getInt(enchant, "minimum-cost.extra-per-level", 1)),
                            EnchantmentRegistryEntry.EnchantmentCost
                                    .of(Methods.getInt(enchant, "maximum-cost.base", 60), Methods.getInt(enchant, "maximum-cost.extra-per-level", 1)),
                            Methods.getBoolean(enchant, "enchantment-table", false),
                            getTagsFromList(Methods.getStringList(
                                    enchant,
                                    "supported-items",
                                    List.of(
                                            "#minecraft:enchantable/armor"
                                    )
                            )),
                            enchant
                    );

                    key = DisorderEnchant.disorder_key;
                }
            }

            if (enchantment == null) continue;

            addEnchantment(key, enchantment);
        }

        file.save();
    }

    private Set<TagEntry<ItemType>> getTagsFromList(List<String> tags) {
        final Set<TagEntry<ItemType>> supportedItemTags = new HashSet<>();

        for (String itemTag : tags) {
            if (itemTag == null) continue;

            if (itemTag.startsWith("#")) {
                itemTag = itemTag.substring(1);
            } else {
                this.logger.warn("Only item tags are supported for now, item tags need to begin with #");

                continue;
            }

            try {
                Key key = Key.key(itemTag);

                TagKey<ItemType> tagKey = ItemTypeTagKeys.create(key);

                TagEntry<ItemType> tagEntry = TagEntry.tagEntry(tagKey);

                supportedItemTags.add(tagEntry);
            } catch (IllegalArgumentException e) {
                this.logger.warn("Failed to create tag entry for {}", itemTag);
            }
        }

        return supportedItemTags;
    }
}