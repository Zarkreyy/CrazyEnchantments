package com.badbones69.crazyenchantments.paper.controllers.settings;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.badbones69.crazyenchantments.paper.Methods;
import com.badbones69.crazyenchantments.paper.Starter;
import com.badbones69.crazyenchantments.paper.api.FileManager.Files;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.DataKeys;
import com.badbones69.crazyenchantments.paper.api.utils.ColorUtils;
import io.papermc.paper.persistence.PersistentDataContainerView;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ProtectionCrystalSettings {

    @NotNull
    private final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    @NotNull
    private final Starter starter = this.plugin.getStarter();

    @NotNull
    private final Methods methods = this.starter.getMethods();

    @NotNull
    private final String protectionString = ColorUtils.color(Files.CONFIG.getFile().getString("Settings.ProtectionCrystal.Protected"));

    private final HashMap<UUID, List<ItemStack>> crystalItems = new HashMap<>();

    private ItemBuilder crystal;

    public void loadProtectionCrystal() {
        FileConfiguration config = Files.CONFIG.getFile();

        this.crystal = new ItemBuilder()
                .withType(config.getString("Settings.ProtectionCrystal.Item", "emerald").toLowerCase()) // this is lowercased, because internally. the itembuilder uses mojang mapped ids.
                .setDisplayName(config.getString("Settings.ProtectionCrystal.Name", "Error getting name."))
                .setDisplayLore(config.getStringList("Settings.ProtectionCrystal.Lore"))
                .setGlowing(config.getBoolean("Settings.ProtectionCrystal.Glowing", false));
    }

    public ItemStack getCrystals() {
        return getCrystals(1);
    }

    public ItemStack getCrystals(int amount) { //todo() debug this, it adds the pdc on getStack build through a consumer, so editMeta is only called once.
        return this.crystal.setAmount(amount).getStack(itemMeta -> itemMeta.getPersistentDataContainer().set(DataKeys.protection_crystal.getNamespacedKey(), PersistentDataType.BOOLEAN, true));
    }

    /**
     * Add a player to the map to protect items.
     * @param player - The player object.
     * @param items - The items in the player's inventory.
     */
    public void addPlayer(Player player, List<ItemStack> items) {
        this.crystalItems.put(player.getUniqueId(), items);
    }

    /**
     * Remove the player from the map.
     * @param player - The player object.
     */
    public void removePlayer(Player player) {
        this.crystalItems.remove(player.getUniqueId());
    }

    /**
     * Check if the map contains the player.
     * @param player - The player object.
     */
    public boolean containsPlayer(Player player) {
        return this.crystalItems.containsKey(player.getUniqueId());
    }

    /**
     * Get the player from the map.
     * @param player - The player object.
     * @return Get the player's items stored.
     */
    public List<ItemStack> getPlayer(Player player) {
        return this.crystalItems.get(player.getUniqueId());
    }

    /**
     * @return The hash map.
     */
    public HashMap<UUID, List<ItemStack>> getCrystalItems() {
        return this.crystalItems;
    }

    /**
     * Check if the player has permissions & if the option is enabled.
     * @param player - The player to check.
     */
    public boolean isProtectionSuccessful(Player player) {
        if (player.hasPermission("crazyenchantments.bypass.protectioncrystal")) return true;

        FileConfiguration config = Files.CONFIG.getFile();

        if (config.getBoolean("Settings.ProtectionCrystal.Chance.Toggle", false)) return this.methods.randomPicker(config.getInt("Settings.ProtectionCrystal.Chance.Success-Chance", 100), 100);

        return true;
    }

    /**
     * Check if the item is protected or not.
     * @param data- The pdc to check.
     * @return True if yes otherwise false.
     */
    public static boolean isProtected(PersistentDataContainerView data) { //todo() debug and run spark profiler, it should no longer call item meta, but we must check
        return data != null && data.has(DataKeys.protected_item.getNamespacedKey());
    }

    /**
     * Check if the item is a protection crystal.
     * @param item - The item to check.
     * @return True if the item is a protection crystal.
     */
    public boolean isProtectionCrystal(ItemStack item) { //todo() debug and run spark profiler, it should no longer call item meta, but we must check
        return item.getPersistentDataContainer().has(DataKeys.protection_crystal.getNamespacedKey());
    }

    /**
     * Remove protection from the item.
     * @param item - The item to remove protection from.
     * @return The new item.
     */
    public ItemStack removeProtection(ItemStack item) { //todo() debug and run spark profiler, it should no longer call item meta, but we must check
        if (item.getPersistentDataContainer().has(DataKeys.protected_item.getNamespacedKey())) {
            if (!(item.lore() == null)) {
                List<Component> lore = item.lore();

                assert lore != null; //todo() maybe deconstruct with the ItemBuilder?
                lore.removeIf(loreComponent -> ColorUtils.toPlainText(loreComponent)
                        .contains(ColorUtils.stripStringColour(this.protectionString)));

                item.editMeta(itemMeta -> {
                    itemMeta.getPersistentDataContainer().remove(DataKeys.protected_item.getNamespacedKey());

                    itemMeta.lore(lore);
                });
            }
        }

        return item;
    }

    /**
     * Add protection to an item.
     * @param item - The item to add protection to.
     * @return The new item.
     */
    public ItemStack addProtection(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = item.lore() != null ? item.lore() : new ArrayList<>();

        meta.getPersistentDataContainer().set(DataKeys.protected_item.getNamespacedKey(), PersistentDataType.BOOLEAN, true);

        assert lore != null;
        lore.add(ColorUtils.legacyTranslateColourCodes(this.protectionString));

        meta.lore(lore);
        item.setItemMeta(meta);

        return item;
    }
}