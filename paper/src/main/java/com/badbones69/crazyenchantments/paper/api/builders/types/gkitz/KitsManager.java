package com.badbones69.crazyenchantments.paper.api.builders.types.gkitz;

import com.badbones69.crazyenchantments.paper.api.FileManager.Files;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.badbones69.crazyenchantments.paper.api.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class KitsManager {

    private static Component inventoryName;
    private static int inventorySize;

    private static ItemStack backRight, backLeft;

    public static void load() {
        FileConfiguration file = Files.ENCHANTMENT_TYPES.getFile();

        String path = "Info-GUI-Settings";

        inventoryName = ColorUtils.legacyTranslateColourCodes(file.getString(path + ".Inventory.Name", "&c&lEnchantment Info"));
        inventorySize = file.getInt(path + ".Inventory.Size", 18);

        backRight = new ItemBuilder()
                .withType(file.getString(path + ".Back-Item.Right.Item", "nether_star").toLowerCase()) // this is lowercased, because internally. the itembuilder uses mojang mapped ids.
                .setPlayer(file.getString(path + ".Back-Item.Right.Player", ""))
                .setDisplayName(file.getString(path + ".Back-Item.Right.Name", "&7&l<<&b&lBack"))
                .setDisplayLore(file.getStringList(path + ".Back-Item.Right.Lore"))
                .getStack();

        backLeft = new ItemBuilder()
                .withType(file.getString(path + ".Back-Item.Left.Item", "nether_star").toLowerCase()) // this is lowercased, because internally. the itembuilder uses mojang mapped ids.
                .setPlayer(file.getString(path + ".Back-Item.Left.Player", ""))
                .setDisplayName(file.getString(path + ".Back-Item.Left.Name", "&b&lBack&7&l>>"))
                .setDisplayLore(file.getStringList(path + ".Back-Item.Left.Lore"))
                .getStack();
    }

    public static Component getInventoryName() {
        return inventoryName;
    }

    public static int getInventorySize() {
        return inventorySize;
    }

    public static ItemStack getBackLeft() {
        return backLeft;
    }

    public static ItemStack getBackRight() {
        return backRight;
    }
}