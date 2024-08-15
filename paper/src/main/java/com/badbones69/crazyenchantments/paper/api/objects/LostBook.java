package com.badbones69.crazyenchantments.paper.api.objects;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.badbones69.crazyenchantments.paper.api.FileManager.Files;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.badbones69.crazyenchantments.paper.tasks.support.enums.Currency;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.DataKeys;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LostBook {

    private final int slot;
    private final boolean inGUI;
    private final ItemBuilder displayItem;
    private final int cost;
    private final Currency currency;
    private final boolean useFirework;
    private final List<Color> fireworkColors;
    private final boolean useSound;
    private Sound sound;

    public LostBook(int slot, boolean inGUI, ItemBuilder displayItem, int cost, Currency currency,
                    boolean useFirework, List<Color> fireworkColors, boolean useSound, String sound) {
        this.slot = slot - 1;
        this.inGUI = inGUI;
        this.displayItem = displayItem;
        this.cost = cost;
        this.currency = currency;
        this.useFirework = !fireworkColors.isEmpty() && useFirework;
        this.fireworkColors = fireworkColors;

        try { // If the sound doesn't exist it will not error.
            this.sound = Sound.valueOf(sound); //todo() use the registry to get sounds, and allow kyori custom sounds. I'll handle this one - ryder
        } catch (Exception e) {
            CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);
            // use our plugin logger, instead of the server so we know what is wrong.
            plugin.getLogger().info(("The sound " + sound + " is not a sound found in this minecraft version."));
            this.sound = null;
        }

        this.useSound = sound != null && useSound;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public boolean isInGUI() {
        return this.inGUI;
    }
    
    public ItemBuilder getDisplayItem() {
        return this.displayItem;
    }
    
    public int getCost() {
        return this.cost;
    }
    
    public Currency getCurrency() {
        return this.currency;
    }

    public boolean useFirework() {
        return this.useFirework;
    }
    
    public List<Color> getFireworkColors() {
        return this.fireworkColors;
    }
    
    public boolean playSound() {
        return this.useSound;
    }
    
    public Sound getSound() {
        return this.sound;
    }
    
    public ItemBuilder getLostBook(Category category) {
        return getLostBook(category, 1);
    }

    public ItemBuilder getLostBook(Category category, int amount) {
        FileConfiguration file = Files.CONFIG.getFile();
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("%Category%", category.getDisplayItem().getDisplayName());

        return new ItemBuilder()
        .withType(file.getString("Settings.LostBook.Item", "book").toLowerCase()) // this is lowercased, because internally. the itembuilder uses mojang mapped ids.
        .setAmount(amount)
        .setDisplayName(file.getString("Settings.LostBook.Name", "Error getting name."))
        .setNamePlaceholders(placeholders)
        .setDisplayLore(file.getStringList("Settings.LostBook.Lore"))
        .setLorePlaceholders(placeholders)
                .setPersistentString(DataKeys.lost_book.getNamespacedKey(), category.getName());
    }
}