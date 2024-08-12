package com.badbones69.crazyenchantments.paper.api.enums.pdc;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public enum DataKeys {

    dust("Crazy_Dust"),
    white_scroll_protection("White_Scroll_Protection"),
    enchantments("CrazyEnchants"),
    stored_enchantments("Stored_Enchantments"),
    protection_crystal("is_Protections_Crystal"),
    protected_item("isProtected"),
    scrambler("isScrambler"),
    lost_book("Lost_Book_Type"),
    no_firework_damage("no_firework_damage"),
    scroll("Crazy_Scroll"),
    experience("Experience"),
    limit_reducer("Limit_Reducer"),
    slot_crystal("Slot_Crystal"),
    gkit_type("gkit"),
    random_number("random_number");

    private final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    private final String NamespacedKey;

    DataKeys(final String NamespacedKey) {
        this.NamespacedKey = NamespacedKey;
    }

    public final NamespacedKey getNamespacedKey() {
        return new NamespacedKey(this.plugin, this.NamespacedKey);
    }
}