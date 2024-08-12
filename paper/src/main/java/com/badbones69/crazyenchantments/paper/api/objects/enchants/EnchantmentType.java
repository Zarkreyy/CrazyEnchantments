package com.badbones69.crazyenchantments.paper.api.objects.enchants;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.badbones69.crazyenchantments.paper.Methods;
import com.badbones69.crazyenchantments.paper.api.FileManager.Files;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.badbones69.crazyenchantments.paper.api.objects.CEnchantment;
import com.ryderbelserion.vital.paper.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class EnchantmentType {

    @NotNull
    private final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    @NotNull
    private final Methods methods = this.plugin.getStarter().getMethods();

    private final String displayName;
    private final int slot;
    private final ItemStack displayItem;
    private final List<CEnchantment> enchantments = new ArrayList<>();
    private final List<Material> enchantableMaterials = new ArrayList<>();

    public EnchantmentType(String name) {
        FileConfiguration file = Files.ENCHANTMENT_TYPES.getFile();
        String path = "Types." + name;
        this.displayName = name;
        this.slot = file.getInt(path + ".Display-Item.Slot", 1) - 1;
        this.displayItem = new ItemBuilder()
        .withType(file.getString(path + ".Display-Item.Item", "stone")) // this is lowercased, because internally. the itembuilder uses mojang mapped ids.
        .setDisplayName(file.getString(path + ".Display-Item.Name", "Error getting name."))
        .setDisplayLore(file.getStringList(path + ".Display-Item.Lore")).getStack();

        for (String type : file.getStringList(path + ".Enchantable-Items")) {
            // this is lowercased, because internally. the itembuilder uses mojang mapped ids.
            // also why was I creating an entire itembuilder object into memory just for material
            Material material = ItemUtil.getMaterial(type.toLowerCase());

            if (material != null) this.enchantableMaterials.add(material);
        }
    }

    public EnchantmentType getFromName(String name) {
        return this.methods.getFromName(name);
    }

    public String getName() {
        return this.displayName;
    }

    public int getSlot() {
        return this.slot;
    }

    public ItemStack getDisplayItem() {
        return this.displayItem;
    }

    public List<Material> getEnchantableMaterials() {
        return this.enchantableMaterials;
    }

    public boolean canEnchantItem(ItemStack item) {
        return this.enchantableMaterials.contains(item.getType());
    }

    public List<CEnchantment> getEnchantments() {
        return this.enchantments;
    }

    public void addEnchantment(CEnchantment enchantment) {
        this.enchantments.add(enchantment);
    }

    public void removeEnchantment(CEnchantment enchantment) {
        this.enchantments.remove(enchantment);
    }
}