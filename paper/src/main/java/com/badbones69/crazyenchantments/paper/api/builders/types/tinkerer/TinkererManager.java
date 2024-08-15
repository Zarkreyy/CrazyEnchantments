package com.badbones69.crazyenchantments.paper.api.builders.types.tinkerer;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.badbones69.crazyenchantments.paper.Methods;
import com.badbones69.crazyenchantments.paper.Starter;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.badbones69.crazyenchantments.paper.api.economy.Currency;
import com.badbones69.crazyenchantments.paper.api.economy.CurrencyAPI;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.DataKeys;
import com.badbones69.crazyenchantments.paper.api.objects.CEBook;
import com.badbones69.crazyenchantments.paper.api.objects.CEnchantment;
import com.ryderbelserion.vital.common.utils.StringUtil;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TinkererManager {

    @NotNull
    private static final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    @NotNull
    private static final Starter starter = plugin.getStarter();

    @NotNull
    private static final Methods methods = starter.getMethods();

    @NotNull
    private static final CurrencyAPI currencyAPI = starter.getCurrencyAPI();

    public static boolean useExperience(Player player, PlayerInteractEvent event, boolean mainHand, FileConfiguration configuration) {
        PlayerInventory inventory = player.getInventory();

        ItemStack item = mainHand ? inventory.getItemInMainHand() : inventory.getItemInOffHand();

        if (item.isEmpty()) return false;

        //todo() debug and run spark profiler, it should no longer call item meta, but we must check
        if (!item.getPersistentDataContainer().has(DataKeys.experience.getNamespacedKey())) return false;
        int amount = Integer.parseInt(item.getPersistentDataContainer().getOrDefault(DataKeys.experience.getNamespacedKey(), PersistentDataType.STRING, "0"));

        event.setCancelled(true);

        if (mainHand) {
            inventory.setItemInMainHand(methods.removeItem(item));
        } else {
            inventory.setItemInOffHand(methods.removeItem(item));
        }

        if (Currency.isCurrency(configuration.getString("Settings.Currency"))) {
            currencyAPI.giveCurrency(player, Currency.getCurrency(configuration.getString("Settings.Currency")), amount);
        }

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

        return true;
    }

    /**
     * @param amount Amount of XP to store.
     * @return XP Bottle with custom amount of xp stored in it.
     */
    public static ItemStack getXPBottle(String amount, FileConfiguration config) {
        String id = config.getString("Settings.BottleOptions.Item", "experience_bottle").toLowerCase(); // this is lowercased, because internally. the itembuilder uses mojang mapped ids.
        String name = config.getString("Settings.BottleOptions.Name", "");
        List<String> lore = new ArrayList<>();

        for (String l : config.getStringList("Settings.BottleOptions.Lore")) {
            lore.add(l.replace("%Total%", amount).replace("%total%", amount));
        }

        return new ItemBuilder().withType(id).setDisplayName(name).setDisplayLore(lore).addKey(DataKeys.experience.getNamespacedKey(), amount).getStack();
    }

    public static int getTotalXP(ItemStack item, FileConfiguration config) {
        int total = 0;

        Map<CEnchantment, Integer> ceEnchants = starter.getEnchantmentBookSettings().getEnchantments(item);

        if (!ceEnchants.isEmpty()) { // CrazyEnchantments
            for (Map.Entry<CEnchantment, Integer> enchantment : ceEnchants.entrySet()) {
                String[] values = config.getString("Tinker.Crazy-Enchantments." + enchantment.getKey().getName() + ".Items", "0").replaceAll(" ", "").split(",");
                int baseAmount = Integer.parseInt(values[0]);
                int multiplier = values.length < 2 ? 0 : Integer.parseInt(values[1]);
                int enchantmentLevel = enchantment.getValue();

                total += baseAmount + enchantmentLevel * multiplier;
            }
        }

        if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) { // Vanilla Enchantments
            for (Map.Entry<Enchantment, Integer> enchantment : item.getEnchantments().entrySet()) {
                String[] values = config.getString("Tinker.Vanilla-Enchantments." + enchantment.getKey().getKey().value(), "0").replaceAll(" ", "").split(",");
                int baseAmount = Integer.parseInt(values[0]);
                int multiplier = values.length < 2 ? 0 : Integer.parseInt(values[1]);
                int enchantmentLevel = enchantment.getValue();
                total += baseAmount + enchantmentLevel * multiplier;
            }
        }

        return total;
    }

    public static int getMaxDustLevelFromBook(CEBook book, FileConfiguration config) {
        String path = "Tinker.Crazy-Enchantments." + book.getEnchantment().getName() + ".Book";
        if (!config.contains(path)) return 1;

        String[] values = config.getString(path, "0").replaceAll(" ", "").split(",");
        int baseAmount = Integer.parseInt(values[0]);
        int multiplier = values.length < 2 ? 0 : Integer.parseInt(values[1]);

        return baseAmount + book.getLevel() * multiplier;
    }

    public static Map<Integer, Integer> getSlots() {
        return new HashMap<>(23) {{
            put(1, 5);
            put(2, 6);
            put(3, 7);
            put(9, 14);
            put(10, 15);
            put(11, 16);
            put(12, 17);
            put(18, 23);
            put(19, 24);
            put(20, 25);
            put(21, 26);
            put(27, 32);
            put(28, 33);
            put(29, 34);
            put(30, 35);
            put(36, 41);
            put(37, 42);
            put(38, 43);
            put(39, 44);
            put(45, 50);
            put(46, 51);
            put(47, 52);
            put(48, 53);
        }};
    }
}