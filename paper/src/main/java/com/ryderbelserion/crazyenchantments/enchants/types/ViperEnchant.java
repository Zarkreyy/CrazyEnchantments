package com.ryderbelserion.crazyenchantments.enchants.types;

import com.ryderbelserion.crazyenchantments.enchants.interfaces.CustomEnchantment;
import com.ryderbelserion.vital.utils.Methods;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.tag.TagEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;
import java.util.HashSet;
import java.util.Set;

public class ViperEnchant implements CustomEnchantment {

    public static final Key viper_key = Key.key("crazyenchantments:viper");

    private final ConfigurationSection section;

    public ViperEnchant(final ConfigurationSection section) {
        this.section = section;
    }

    @Override
    public Key getKey() {
        return viper_key;
    }

    @Override
    public Component getDescription() {
        return Methods.parse(this.section.getString("display.name", "<red>Viper"));
    }

    @Override
    public int getAnvilCost() {
        return this.section.getInt("anvil.cost", 1);
    }

    @Override
    public int getMaxLevel() {
        return this.section.getInt("max.level", 1);
    }

    @Override
    public int getWeight() {
        return this.section.getInt("weight", 1);
    }

    @Override
    public EnchantmentRegistryEntry.EnchantmentCost getMinimumCost() {
        return EnchantmentRegistryEntry.EnchantmentCost.of(10, 1);
    }

    @Override
    public EnchantmentRegistryEntry.EnchantmentCost getMaximumCost() {
        return EnchantmentRegistryEntry.EnchantmentCost.of(65, 1);
    }

    @Override
    public Iterable<EquipmentSlotGroup> getActiveSlots() {
        return Set.of(EquipmentSlotGroup.MAINHAND, EquipmentSlotGroup.OFFHAND);
    }

    @Override
    public boolean canGetFromEnchantingTable() {
        return this.section.getBoolean("enchantment-table.enabled", false);
    }

    @Override
    public boolean isEnabled() {
        return this.section.getBoolean("enabled", false);
    }

    @Override
    public Set<TagEntry<ItemType>> getSupportedItems() {
        final Set<TagEntry<ItemType>> items = new HashSet<>();

        this.section.getStringList("supported-items").forEach(text -> items.add(TagEntry.tagEntry(ItemTypeTagKeys.create(Key.key(text)))));

        return items;
    }

    @Override
    public Set<TagEntry<ItemType>> getPrimaryItems() {
        final Set<TagEntry<ItemType>> items = new HashSet<>();

        this.section.getStringList("enchantment-table.primary-items").forEach(text -> items.add(TagEntry.tagEntry(ItemTypeTagKeys.create(Key.key(text)))));

        return items;
    }
}