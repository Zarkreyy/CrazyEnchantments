package com.ryderbelserion.crazyenchantments.enchants.types;

import com.ryderbelserion.crazyenchantments.enchants.interfaces.CustomEnchantment;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.keys.tags.EnchantmentTagKeys;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.tag.TagEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DisorderEnchant implements CustomEnchantment {

    public static final Key disorder_key = Key.key("crazyenchantments:disorder_curse");

    private final Set<TagKey<Enchantment>> enchantTagKeys = new HashSet<>();
    private final EnchantmentRegistryEntry.EnchantmentCost minimumCost;
    private final EnchantmentRegistryEntry.EnchantmentCost maximumCost;
    private final Set<TagEntry<ItemType>> supportedItemTags;
    private final int anvilCost, maxLevel, weight, disorderChancePerLevel;
    private final boolean isEnabled;

    public DisorderEnchant(
            final boolean isEnabled,
            final int anvilCost,
            final int maxLevel,
            final int weight,
            final EnchantmentRegistryEntry.EnchantmentCost minimumCost,
            final EnchantmentRegistryEntry.EnchantmentCost maximumCost,
            final boolean isInEnchantmentTable,
            final Set<TagEntry<ItemType>> supportedItemTags,
            final ConfigurationSection section
    ) {
        this.supportedItemTags = supportedItemTags;

        if (isInEnchantmentTable) {
            this.enchantTagKeys.add(EnchantmentTagKeys.IN_ENCHANTING_TABLE);
        }

        this.minimumCost = minimumCost;
        this.maximumCost = maximumCost;

        this.isEnabled = isEnabled;
        this.anvilCost = anvilCost;
        this.maxLevel = maxLevel;
        this.weight = weight;

        // enchant unique values

        this.disorderChancePerLevel = section.getInt("", 1);
    }

    @Override
    public Key getKey() {
        return disorder_key;
    }

    @Override
    public Component getDescription() {
        return Component.translatable("crazyenchantments.enchant.disorder_curse", "Curse of Disorder");
    }

    @Override
    public int getAnvilCost() {
        return this.anvilCost;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public EnchantmentRegistryEntry.EnchantmentCost getMinimumCost() {
        return this.minimumCost;
    }

    @Override
    public EnchantmentRegistryEntry.EnchantmentCost getMaximumCost() {
        return this.maximumCost;
    }

    @Override
    public Iterable<EquipmentSlotGroup> getActiveSlots() {
        return Set.of(EquipmentSlotGroup.HAND);
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public Set<TagEntry<ItemType>> getSupportedItems() {
        return this.supportedItemTags;
    }

    @Override
    public Set<TagKey<Enchantment>> getEnchantTagKeys() {
        return Collections.unmodifiableSet(this.enchantTagKeys);
    }
}