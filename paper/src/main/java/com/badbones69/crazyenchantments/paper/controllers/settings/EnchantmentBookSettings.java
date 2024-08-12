package com.badbones69.crazyenchantments.paper.controllers.settings;

import com.badbones69.crazyenchantments.paper.api.FileManager.Files;
import com.badbones69.crazyenchantments.paper.api.economy.Currency;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.DataKeys;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.Enchant;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.EnchantedBook;
import com.badbones69.crazyenchantments.paper.api.objects.CEBook;
import com.badbones69.crazyenchantments.paper.api.objects.CEnchantment;
import com.badbones69.crazyenchantments.paper.api.objects.Category;
import com.badbones69.crazyenchantments.paper.api.objects.LostBook;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.badbones69.crazyenchantments.paper.api.utils.ColorUtils;
import com.badbones69.crazyenchantments.paper.api.utils.EnchantUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentBookSettings {

    private ItemBuilder enchantmentBook;

    private final List<Category> categories = Lists.newArrayList();

    private final List<CEnchantment> registeredEnchantments = Lists.newArrayList();

    private final Gson gson = new Gson();

    /**
     *
     * @return True if unsafe enchantments are enabled.
     */
    public boolean useUnsafeEnchantments() {
        FileConfiguration config = Files.CONFIG.getFile();

        return config.getBoolean("Settings.EnchantmentOptions.UnSafe-Enchantments");
    }

    @Deprecated(since = "1.20.6")
    public boolean hasEnchantment(ItemMeta meta, CEnchantment enchantment) {
        getEnchantments(meta);
        PersistentDataContainer data = meta.getPersistentDataContainer();

        if (!data.has(DataKeys.enchantments.getNamespacedKey())) return false;

        String itemData = data.get(DataKeys.enchantments.getNamespacedKey(), PersistentDataType.STRING);
        if (itemData == null) return false;

        return this.gson.fromJson(itemData, Enchant.class).hasEnchantment(enchantment.getName());
    }

    /**
     * This method converts an ItemStack into a CEBook.
     * @param book The ItemStack you are converting.
     * @return If the book is a CEBook it will return the CEBook object and if not it will return null.
     */
    @Nullable
    public CEBook getCEBook(ItemStack book) {
        if (!book.hasItemMeta()) return null;
        ItemMeta meta = book.getItemMeta();
        if (!meta.getPersistentDataContainer().has(DataKeys.stored_enchantments.getNamespacedKey())) return null;

        EnchantedBook data = this.gson.fromJson(meta.getPersistentDataContainer().get(DataKeys.stored_enchantments.getNamespacedKey(), PersistentDataType.STRING), EnchantedBook.class);
       
        CEnchantment enchantment = null;
        for (CEnchantment enchant : getRegisteredEnchantments()) {
            if (enchant.getName().equalsIgnoreCase(data.getName())) {
                enchantment = enchant;
                break;
            }
        }

        return new CEBook(enchantment, data.getLevel(), book.getAmount())
                .setSuccessRate(data.getSuccessChance())
                .setDestroyRate(data.getDestroyChance());
    }

    /**
     * Get a new book that has been scrambled.
     * @param book The old book.
     * @return A new scrambled book.
     */
    @Nullable
    public ItemStack getNewScrambledBook(ItemStack book) {
        if (!book.hasItemMeta()) return null;

        EnchantedBook data = this.gson.fromJson(book.getItemMeta().getPersistentDataContainer().get(DataKeys.stored_enchantments.getNamespacedKey(), PersistentDataType.STRING), EnchantedBook.class);

        CEnchantment enchantment = null;
        int bookLevel = 0;

        for (CEnchantment enchantment1 : getRegisteredEnchantments()) {
            if (!enchantment1.getName().equalsIgnoreCase(data.getName())) continue;
            enchantment = enchantment1;
            bookLevel = data.getLevel();
        }

        if (enchantment == null) return null;

        return new CEBook(enchantment, bookLevel, EnchantUtils.getHighestEnchantmentCategory(enchantment)).buildBook();
    }

    /**
     * Check if an itemstack is an enchantment book.
     * @param book The item you are checking.
     * @return True if it is and false if not.
     */
    public boolean isEnchantmentBook(ItemStack book) {

        if (book == null || book.getItemMeta() == null) return false;
        if (!book.getItemMeta().getPersistentDataContainer().has(DataKeys.stored_enchantments.getNamespacedKey())) return false;

        String dataString = book.getItemMeta().getPersistentDataContainer().get(DataKeys.stored_enchantments.getNamespacedKey(), PersistentDataType.STRING);
        EnchantedBook data = this.gson.fromJson(dataString, EnchantedBook.class);

        for (CEnchantment enchantment : getRegisteredEnchantments()) {
            if (enchantment.getName().equalsIgnoreCase(data.getName())) return true;
        }

        return false;
    }

    /**
     *
     * @return A list of all active enchantments.
     */
    @NotNull
    public List<CEnchantment> getRegisteredEnchantments() {
        return this.registeredEnchantments;
    }

    /**
     *
     * @return itemBuilder for an enchanted book.
     */
    public ItemBuilder getNormalBook() {
        return new ItemBuilder(this.enchantmentBook);
    }

    /**
     * @return the itemstack of the enchantment book.
     */
    @NotNull
    public ItemStack getEnchantmentBookItem() {
        return new ItemBuilder(this.enchantmentBook).build();
    }

    /**
     *
     * @param enchantmentBook The book data to use for the itemBuilder.
     */
    public void setEnchantmentBook(@NotNull ItemBuilder enchantmentBook) {
        this.enchantmentBook = enchantmentBook;
    }

    /**
     * Note: If the enchantment is not active it will not be added to the Map.
     * @param item Item you want to get the enchantments from.
     * @return A Map of all enchantments and their levels on the item.
     */
    @NotNull
    public Map<CEnchantment, Integer> getEnchantments(@Nullable ItemStack item) {
        if (item == null || item.getItemMeta() == null) return Collections.emptyMap();

        return getEnchantments(item.getItemMeta());
    }

    @NotNull
    public Map<CEnchantment, Integer> getEnchantments(@Nullable ItemMeta meta) {
        if (meta == null) return Collections.emptyMap();

        return getEnchantments(meta.getPersistentDataContainer());
    }

    @NotNull
    public Map<CEnchantment, Integer> getEnchantments(@NotNull PersistentDataContainer container) {
        Map<CEnchantment, Integer> enchantments = new HashMap<>();

        String data = container.get(DataKeys.enchantments.getNamespacedKey(), PersistentDataType.STRING);

        if (data == null) return Collections.emptyMap();

        Enchant enchants = this.gson.fromJson(data, Enchant.class);

        if (enchants.isEmpty()) return Collections.emptyMap();

        for (CEnchantment enchantment : getRegisteredEnchantments()) {
            if (!enchantment.isActivated()) continue;

            if (enchants.hasEnchantment(enchantment.getName())) enchantments.put(enchantment, enchants.getLevel(enchantment.getName()));
        }

        return enchantments;
    }

    /**
     * Note: If the enchantment is not active it will not be added to the list.
     * @param item Item you want to get the enchantments from.
     * @return A list of enchantments the item has.
     */
    @Deprecated(since = "1.20", forRemoval = true)
    public List<CEnchantment> getEnchantmentsOnItem(ItemStack item) {
        return new ArrayList<>(getEnchantments(item).keySet());
    }

    /**
     *
     * @param item to check.
     * @return Amount of enchantments on the item.
     */
    public int getEnchantmentAmount(@NotNull ItemStack item, boolean includeVanillaEnchantments) {
        int amount = getEnchantments(item).size();

        if (includeVanillaEnchantments) {
            if (item.hasItemMeta()) {
                if (item.getItemMeta().hasEnchants()) amount += item.getItemMeta().getEnchants().size();
            }
        }

        return amount;
    }

    /**
     * Get all the categories that can be used.
     * @return List of all the categories.
     */
    @NotNull
    public List<Category> getCategories() {
        return this.categories;
    }

    /**
     * Loads in all config options.
     */
    public void populateMaps() {
        FileConfiguration config = Files.CONFIG.getFile();

        for (String category : config.getConfigurationSection("Categories").getKeys(false)) {
            String path = "Categories." + category;
            LostBook lostBook = new LostBook(
                    config.getInt(path + ".LostBook.Slot"),
                    config.getBoolean(path + ".LostBook.InGUI"),
                    new ItemBuilder()
                            .withType(config.getString(path + ".LostBook.Item", "book").toLowerCase()) // this is lowercased, because internally. the itembuilder uses mojang mapped ids.
                            //.setSkull(config.getString(path + ".LostBook.Player"))
                            .setDisplayName(config.getString(path + ".LostBook.Name", "Error getting name."))
                            .setDisplayLore(config.getStringList(path + ".LostBook.Lore"))
                            .setGlowing(config.getBoolean(path + ".LostBook.Glowing", true)),
                    config.getInt(path + ".LostBook.Cost"),
                    Currency.getCurrency(config.getString(path + ".LostBook.Currency")),
                    config.getBoolean(path + ".LostBook.FireworkToggle", false),
                    getColors(config.getString(path + ".LostBook.FireworkColors", "Red, White, Blue")),
                    config.getBoolean(path + ".LostBook.Sound-Toggle", false),
                    config.getString(path + ".LostBook.Sound", "BLOCK_ANVIL_PLACE")); //todo() switch to kyori api for sounds

            this.categories.add(new Category(
                    category,
                    config.getInt(path + ".Slot"),
                    config.getBoolean(path + ".InGUI", true),
                    new ItemBuilder()
                            .withType(config.getString(path + ".Item", ColorUtils.getRandomPaneColor().getType().getKey().getKey().toLowerCase())) // use the correct getKey from random pane
                            .setPlayer(config.getString(path + ".Player", ""))
                            .setDisplayName(config.getString(path + ".Name", "Error getting name."))
                            .setDisplayLore(config.getStringList(path + ".Lore"))
                            .setGlowing(config.getBoolean(path + ".Glowing", false)),
                    config.getInt(path + ".Cost"),
                    Currency.getCurrency(config.getString(path + ".Currency")),
                    config.getInt(path + ".Rarity"),
                    lostBook,
                    config.getInt(path + ".EnchOptions.SuccessPercent.Max"),
                    config.getInt(path + ".EnchOptions.SuccessPercent.Min"),
                    config.getInt(path + ".EnchOptions.DestroyPercent.Max"),
                    config.getInt(path + ".EnchOptions.DestroyPercent.Min"),
                    config.getBoolean(path + ".EnchOptions.MaxLvlToggle"),
                    config.getInt(path + ".EnchOptions.LvlRange.Max"),
                    config.getInt(path + ".EnchOptions.LvlRange.Min")));
        }
    }

    /**
     * @param name The name of the category you want.
     * @return The category object.
     */
    @Nullable
    public Category getCategory(String name) {
        for (Category category : this.categories) {
            if (category.getName().equalsIgnoreCase(name)) return category;
        }

        return null;
    }

    private List<Color> getColors(String string) {
        List<Color> colors = new ArrayList<>();
        ColorUtils.color(colors, string);

        return colors;
    }

    /**
     * @param item Item you are getting the level from.
     * @param enchant The enchantment you want the level from.
     * @return The level the enchantment has.
     */
    public int getLevel(@NotNull ItemStack item, @NotNull CEnchantment enchant) {
        String data = item.getItemMeta().getPersistentDataContainer().get(DataKeys.enchantments.getNamespacedKey(), PersistentDataType.STRING);

        int level = data == null ? 0 : this.gson.fromJson(data, Enchant.class).getLevel(enchant.getName());

        if (!useUnsafeEnchantments() && level > enchant.getMaxLevel()) level = enchant.getMaxLevel();

        return level;
    }

    /**
     * @param item Item you want to remove the enchantment from.
     * @param enchant Enchantment you want removed.
     * @return Item without the enchantment.
     */
    @NotNull
    public ItemStack removeEnchantment(@NotNull ItemStack item, @NotNull CEnchantment enchant) {
        if (!item.hasItemMeta()) return item;

        item.setItemMeta(removeEnchantment(item.getItemMeta(), enchant));

        return item;
    }

    @NotNull
    public ItemMeta removeEnchantment(@NotNull ItemMeta meta, @NotNull CEnchantment enchant) {
        List<Component> lore = meta.lore();

        if (lore != null) {
            lore.removeIf(loreComponent -> ColorUtils.toPlainText(loreComponent)
                    .contains(ColorUtils.stripStringColour(enchant.getCustomName())));
            meta.lore(lore);
        }

        Enchant data;

        if (meta.getPersistentDataContainer().has(DataKeys.enchantments.getNamespacedKey())) {
            data = this.gson.fromJson(meta.getPersistentDataContainer().get(DataKeys.enchantments.getNamespacedKey(), PersistentDataType.STRING), Enchant.class);
        } else {
            data = new Enchant(new HashMap<>());
        }

        data.removeEnchantment(enchant.getName());

        if (data.isEmpty()) {
            if (meta.getPersistentDataContainer().has(DataKeys.enchantments.getNamespacedKey()))
                meta.getPersistentDataContainer().remove(DataKeys.enchantments.getNamespacedKey());
        } else {
            meta.getPersistentDataContainer().set(DataKeys.enchantments.getNamespacedKey(), PersistentDataType.STRING, this.gson.toJson(data));
        }

        return meta;
    }

    @NotNull
    public ItemMeta removeEnchantments(@NotNull ItemMeta meta, @NotNull List<CEnchantment> enchants) {

        List<Component> lore = meta.lore();

        if (lore != null) {
            for (CEnchantment enchant : enchants) {
                lore.removeIf(loreComponent -> ColorUtils.toPlainText(loreComponent)
                        .contains(ColorUtils.stripStringColour(enchant.getCustomName())));
                meta.lore(lore);
            }
        }

        Enchant data;

        if (meta.getPersistentDataContainer().has(DataKeys.enchantments.getNamespacedKey())) {
            data = this.gson.fromJson(meta.getPersistentDataContainer().get(DataKeys.enchantments.getNamespacedKey(), PersistentDataType.STRING), Enchant.class);
        } else {
            data = new Enchant(new HashMap<>());
        }

        enchants.forEach(enchant -> data.removeEnchantment(enchant.getName()));

        if (data.isEmpty()) {
            if (meta.getPersistentDataContainer().has(DataKeys.enchantments.getNamespacedKey()))
                meta.getPersistentDataContainer().remove(DataKeys.enchantments.getNamespacedKey());
        } else {
            meta.getPersistentDataContainer().set(DataKeys.enchantments.getNamespacedKey(), PersistentDataType.STRING, this.gson.toJson(data));
        }

        return meta;
    }
}