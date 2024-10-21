package com.ryderbelserion.crazyenchantments.enchants.interfaces;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.tag.TagEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;
import java.util.Set;

public interface CustomEnchantment {

    Key getKey();

    Component getDescription();

    int getAnvilCost();

    int getMaxLevel();

    int getWeight();

    EnchantmentRegistryEntry.EnchantmentCost getMinimumCost();

    EnchantmentRegistryEntry.EnchantmentCost getMaximumCost();

    Iterable<EquipmentSlotGroup> getActiveSlots();

    boolean canGetFromEnchantingTable();

    boolean isEnabled();

    Set<TagEntry<ItemType>> getSupportedItems();

    //TagKey<ItemType> getTagForSupportedItems();

    Set<TagEntry<ItemType>> getPrimaryItems();

    default TagEntry<Enchantment> getTagEntry() {
        return TagEntry.valueEntry(TypedKey.create(RegistryKey.ENCHANTMENT, getKey()));
    }
}