package com.badbones69.crazyenchantments.paper.api.enums;

import com.badbones69.crazyenchantments.paper.api.FileManager.Files;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.DataKeys;
import com.badbones69.crazyenchantments.paper.api.utils.ColorUtils;
import io.papermc.paper.persistence.PersistentDataContainerView;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum Scrolls {
    
    BLACK_SCROLL("Black-Scroll", "BlackScroll", Arrays.asList("b", "black", "blackscroll")),
    WHITE_SCROLL("White-Scroll", "WhiteScroll", Arrays.asList("w", "white", "whitescroll")),
    TRANSMOG_SCROLL("Transmog-Scroll", "TransmogScroll", Arrays.asList("t", "transmog", "transmogscroll"));
    
    private static final HashMap<Scrolls, ItemBuilder> itemBuilderScrolls = new HashMap<>();
    private final String name;
    private final String configName;
    private final List<String> knownNames;
    
    Scrolls(String name, String configName, List<String> knowNames) {
        this.name = name;
        this.knownNames = knowNames;
        this.configName = configName;
    }
    
    public static void loadScrolls() {
        FileConfiguration config = Files.CONFIG.getFile();
        itemBuilderScrolls.clear();

        for (Scrolls scroll : values()) {
            String path = "Settings." + scroll.getConfigName() + ".";
            itemBuilderScrolls.put(scroll, new ItemBuilder()
            .setDisplayName(config.getString(path + "Name", "Error getting name."))
            .setDisplayLore(config.getStringList(path + "Item-Lore"))
            .withType(config.getString(path + "Item", "book").toLowerCase())
            .setGlowing(config.getBoolean(path + "Glowing", false)));
        }
    }
    
    public static Scrolls getFromName(String nameString) {
        for (Scrolls scroll : Scrolls.values()) {
            if (scroll.getKnownNames().contains(nameString.toLowerCase())) return scroll;
        }

        return null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<String> getKnownNames() {
        return this.knownNames;
    }

    /**
     *
     * @return The name that is stored on the item, and defines that the item is in fact a Scroll.
     */
    public String getConfigName() {
        return this.configName;
    }

    private static final NamespacedKey scroll = DataKeys.scroll.getNamespacedKey();

    public static Scrolls getFromPDC(ItemStack item) {
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        if (!item.hasItemMeta() || !data.has(scroll)) return null;

        return getFromName(data.get(scroll, PersistentDataType.STRING));
    }

    public ItemStack getScroll() {
        ItemStack item = itemBuilderScrolls.get(this).build();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(scroll, PersistentDataType.STRING, this.configName);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getScroll(int amount) {
        ItemStack item = itemBuilderScrolls.get(this).setAmount(amount).build();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(scroll, PersistentDataType.STRING, this.configName);
        item.setItemMeta(meta);
        return item;
    }

    private static final NamespacedKey whiteScrollProtectionKey = DataKeys.white_scroll_protection.getNamespacedKey();

    public static String getWhiteScrollProtectionName() {
        String protectNamed;

        FileConfiguration config = Files.CONFIG.getFile();

        protectNamed = ColorUtils.color(config.getString("Settings.WhiteScroll.ProtectedName"));

        return protectNamed;
    }

    public static boolean hasWhiteScrollProtection(@NotNull ItemStack item) {
        return item.hasItemMeta() && hasWhiteScrollProtection(item.getItemMeta());
    }

    public static boolean hasWhiteScrollProtection(@Nullable ItemMeta meta) {
        return meta != null && hasWhiteScrollProtection(meta.getPersistentDataContainer());
    }

    public static boolean hasWhiteScrollProtection(@Nullable PersistentDataContainer data) {
        return data != null && data.has(whiteScrollProtectionKey);
    }

    public static ItemStack addWhiteScrollProtection(@NotNull ItemStack item) {
        assert item.hasItemMeta();
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = item.lore() != null ? item.lore() : new ArrayList<>();

        assert lore != null;
        lore.add(ColorUtils.legacyTranslateColourCodes(getWhiteScrollProtectionName()));
        meta.getPersistentDataContainer().set(whiteScrollProtectionKey, PersistentDataType.BOOLEAN, true);

        return itemBuilder.getStack(itemMeta -> { //todo() debug this, it adds the pdc on getStack build through a consumer, so editMeta is only called once.
            // adds the item protection key
            if (!itemBuilder.hasKey(whiteScrollProtectionKey)) itemMeta.getPersistentDataContainer().set(whiteScrollProtectionKey, PersistentDataType.BOOLEAN, true); //todo() test this, it gets added when the stack is built in the consumer
        });
    }

    public static ItemStack removeWhiteScrollProtection(@NotNull ItemStack item) {
        if (!item.hasItemMeta()) return item;

        // creates a new itembuilder without creating a new itemstack!
        final ItemBuilder itemBuilder = new ItemBuilder(item);

        // removes the item protection key
        if (itemBuilder.hasKey(whiteScrollProtectionKey)) itemBuilder.removePersistentKey(whiteScrollProtectionKey);

        if (item.lore() == null) {
            item.setItemMeta(meta);
            return item;
        }

        List<Component> lore = item.lore();

        lore.removeIf(loreComponent -> ColorUtils.toPlainText(loreComponent)
                .contains(ColorUtils.stripStringColour(getWhiteScrollProtectionName())));
        meta.lore(lore);

        meta.lore(lore);
        item.setItemMeta(meta);

        return item;
    }
}