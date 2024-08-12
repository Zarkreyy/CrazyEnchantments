package com.badbones69.crazyenchantments.paper.listeners;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.badbones69.crazyenchantments.paper.Methods;
import com.badbones69.crazyenchantments.paper.Starter;
import com.badbones69.crazyenchantments.paper.api.FileManager.Files;
import com.badbones69.crazyenchantments.paper.api.builders.types.MenuManager;
import com.badbones69.crazyenchantments.paper.api.enums.Messages;
import com.badbones69.crazyenchantments.paper.api.enums.Scrolls;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.DataKeys;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.Enchant;
import com.badbones69.crazyenchantments.paper.api.objects.CEBook;
import com.badbones69.crazyenchantments.paper.api.objects.CEnchantment;
import com.badbones69.crazyenchantments.paper.api.objects.enchants.EnchantmentType;
import com.badbones69.crazyenchantments.paper.api.utils.ColorUtils;
import com.badbones69.crazyenchantments.paper.api.utils.EnchantUtils;
import com.badbones69.crazyenchantments.paper.api.utils.NumberUtils;
import com.badbones69.crazyenchantments.paper.controllers.settings.EnchantmentBookSettings;
import com.badbones69.crazyenchantments.paper.controllers.settings.ProtectionCrystalSettings;
import com.google.gson.Gson;
import io.papermc.paper.persistence.PersistentDataContainerView;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ScrollListener implements Listener {

    private final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    private final Starter starter = this.plugin.getStarter();

    private final Methods methods = this.starter.getMethods();

    private final EnchantmentBookSettings enchantmentBookSettings = this.starter.getEnchantmentBookSettings();

    private String suffix;
    private boolean countVanillaEnchantments;
    private boolean useSuffix;
    private boolean blackScrollChanceToggle;
    private int blackScrollChance;

    public void loadScrollControl() {
        FileConfiguration config = Files.CONFIG.getFile();
        this.suffix = config.getString("Settings.TransmogScroll.Amount-of-Enchantments", " &7[&6&n%amount%&7]");
        this.countVanillaEnchantments = config.getBoolean("Settings.TransmogScroll.Count-Vanilla-Enchantments");
        this.useSuffix = config.getBoolean("Settings.TransmogScroll.Amount-Toggle");
        this.blackScrollChance = config.getInt("Settings.BlackScroll.Chance", 75);
        this.blackScrollChanceToggle = config.getBoolean("Settings.BlackScroll.Chance-Toggle");
    }

    @EventHandler(ignoreCancelled = true)
    public void onScrollUse(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        ItemStack scroll = event.getCursor();

        if (item == null || item.getType().isAir() || scroll.getType().isAir()) return;

        InventoryType.SlotType slotType = event.getSlotType();

        if (slotType != InventoryType.SlotType.ARMOR && slotType != InventoryType.SlotType.CONTAINER && slotType != InventoryType.SlotType.QUICKBAR) return;

        Scrolls type = Scrolls.getFromPDC(scroll);
        if (type == null) return;

        if (scroll.getAmount() > 1) {
            player.sendMessage(Messages.NEED_TO_UNSTACK_ITEM.getMessage());
            return;
        }

        switch (type.getConfigName()) {
            case "BlackScroll" -> {
                if (this.methods.isInventoryFull(player)) return;

                List<CEnchantment> enchantments = this.enchantmentBookSettings.getEnchantmentsOnItem(item); //todo() what do we use then?
                if (!enchantments.isEmpty()) { // Item has enchantments
                    event.setCancelled(true);
                    player.setItemOnCursor(this.methods.removeItem(scroll));

                    if (this.blackScrollChanceToggle && !this.methods.randomPicker(this.blackScrollChance, 100)) {
                        player.sendMessage(Messages.BLACK_SCROLL_UNSUCCESSFUL.getMessage());
                        return;
                    }

                    CEnchantment enchantment = enchantments.get(ThreadLocalRandom.current().nextInt(enchantments.size()));
                    player.getInventory().addItem(new CEBook(enchantment, this.enchantmentBookSettings.getLevel(item, enchantment), 1).buildBook());
                    event.setCurrentItem(this.enchantmentBookSettings.removeEnchantment(item, enchantment));
                }
            }

            case "WhiteScroll" -> {
                //todo() debug and run spark profiler, it should no longer call item meta, but we must check
                if (Scrolls.hasWhiteScrollProtection(item.getPersistentDataContainer())) return;
                for (EnchantmentType enchantmentType : MenuManager.getEnchantmentTypes()) {
                    if (enchantmentType.getEnchantableMaterials().contains(item.getType())) {
                        event.setCancelled(true);
                        event.setCurrentItem(Scrolls.addWhiteScrollProtection(item));
                        player.setItemOnCursor(this.methods.removeItem(scroll));
                        return;
                    }
                }
            }

            case "TransmogScroll" -> {
                if (this.enchantmentBookSettings.getEnchantments(item).isEmpty()) return;
                if (item.lore() == null) return;

                ItemStack orderedItem = newOrderNewEnchantments(item.clone());

                if (item.isSimilar(orderedItem)) return;

                event.setCancelled(true);
                event.setCurrentItem(orderedItem);
                player.setItemOnCursor(this.methods.removeItem(scroll));
            }
        }
    }

    @EventHandler()
    public void onScrollClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (checkScroll(player.getInventory().getItemInMainHand(), player, event)) return;

        checkScroll(player.getInventory().getItemInOffHand(), player, event);
    }

    private boolean checkScroll(ItemStack scroll, Player player, PlayerInteractEvent event) {
        if (scroll.isEmpty()) return false;

        PersistentDataContainerView container = scroll.getPersistentDataContainer(); //todo() debug and run spark profiler, it should no longer call item meta, but we must check

        if (!container.has(DataKeys.scroll.getNamespacedKey())) return false;

        String data = container.get(DataKeys.scroll.getNamespacedKey(), PersistentDataType.STRING);

        assert data != null;

        if (data.equalsIgnoreCase(Scrolls.BLACK_SCROLL.getConfigName())) {
            event.setCancelled(true);
            player.sendMessage(Messages.RIGHT_CLICK_BLACK_SCROLL.getMessage());
            return true;
        } else if (data.equalsIgnoreCase(Scrolls.WHITE_SCROLL.getConfigName()) || data.equalsIgnoreCase(Scrolls.TRANSMOG_SCROLL.getConfigName())) {
            event.setCancelled(true);
            return true;
        }
        return false;
    }

    @Deprecated
    private ItemStack orderNewEnchantments(ItemStack item) { //todo() change how adding lore should be, simply deconstructing the ItemBuilder like I've explained in other todo()'s should be enough.
        Map<CEnchantment, Integer> enchantmentLevels = new HashMap<>();
        Map<CEnchantment, Integer> categories = new HashMap<>();
        List<CEnchantment> newEnchantmentOrder = new ArrayList<>();

        for (Map.Entry<CEnchantment, Integer> enchantment : this.enchantmentBookSettings.getEnchantments(item).entrySet()) {
            enchantmentLevels.put(enchantment.getKey(), enchantment.getValue());
            categories.put(enchantment.getKey(), EnchantUtils.getHighestEnchantmentCategory(enchantment.getKey()).getRarity());
            newEnchantmentOrder.add(enchantment.getKey());
        }

        orderInts(newEnchantmentOrder, categories);
        ItemMeta newMeta = item.getItemMeta();

        List<Component> newLore = new ArrayList<>();

        newEnchantmentOrder.forEach(enchantment ->
                newLore.add(ColorUtils.legacyTranslateColourCodes(
                        enchantment.getCustomName() + " " + NumberUtils.convertLevelString(enchantmentLevels.get(enchantment)))));

        newMeta.lore(newLore);

        useSuffix(item, newMeta, newEnchantmentOrder);

        item.setItemMeta(newMeta);
        return item;
    }

    private ItemStack newOrderNewEnchantments(ItemStack item) { //todo() change how adding lore should be, simply deconstructing the ItemBuilder like I've explained in other todo()'s should be enough.
        Gson gson = new Gson();

        FileConfiguration config = Files.CONFIG.getFile(); // reduced file configuration calls.

        PersistentDataContainerView container = item.getPersistentDataContainer(); //todo() debug and run spark profiler, it should no longer call item meta, but we must check
        Enchant data = gson.fromJson(container.get(DataKeys.enchantments.getNamespacedKey(), PersistentDataType.STRING), Enchant.class);
        boolean addSpaces = config.getBoolean("Settings.TransmogScroll.Add-Blank-Lines", true);
        List<CEnchantment> newEnchantmentOrder = new ArrayList<>();
        Map<CEnchantment, Integer> enchantments = new HashMap<>();
        List<String> order = config.getStringList("Settings.TransmogScroll.Lore-Order");
        if (order.isEmpty()) order = Arrays.asList("CE_Enchantments", "Protection", "Normal_Lore");

        if (data == null) return item; // Only order if it has CE_Enchants

        for (CEnchantment enchantment : this.enchantmentBookSettings.getRegisteredEnchantments()) {
            if (!data.hasEnchantment(enchantment.getName())) continue;
            enchantments.put(enchantment,ColorUtils.stripStringColour((enchantment.getCustomName() + " " +
                    NumberUtils.toRoman(data.getLevel(enchantment.getName())))).length());
            newEnchantmentOrder.add(enchantment);
        }

        orderInts(newEnchantmentOrder, enchantments); // Order Enchantments by length.

        List<Component> enchantLore = newEnchantmentOrder.stream().map(i ->
                ColorUtils.legacyTranslateColourCodes("%s %s".formatted(i.getCustomName(), NumberUtils.toRoman(data.getLevel(i.getName()))))).collect(Collectors.toList());

        ItemMeta meta = item.getItemMeta(); //todo() following the todo above will eliminate the need to call this, but we also don't have to rebuild the itemstack when deconstructing.
        List<Component> lore = item.lore();
        assert meta != null && lore != null;

        List<Component> normalLore = stripNonNormalLore(lore, newEnchantmentOrder);
        List<Component> protectionLore = getAllProtectionLore(container);

        List<Component> newLore = new ArrayList<>();
        boolean wasEmpty = true;

        for (String selection : order) {
            switch (selection) {
                case "CE_Enchantments" -> {
                    if (addSpaces && !wasEmpty && !enchantLore.isEmpty()) newLore.add(Component.text(""));
                    newLore.addAll(enchantLore);
                    wasEmpty = enchantLore.isEmpty();
                }
                case "Protection" -> {
                    if (addSpaces && !wasEmpty && !protectionLore.isEmpty()) newLore.add(Component.text(""));
                    newLore.addAll(protectionLore);
                    wasEmpty = protectionLore.isEmpty();
                }
                case "Normal_Lore" -> {
                    if (addSpaces && !wasEmpty && !normalLore.isEmpty()) newLore.add(Component.text(""));
                    newLore.addAll(normalLore);
                    wasEmpty = normalLore.isEmpty();
                }
            }
        }

        useSuffix(item, meta, newEnchantmentOrder);
        meta.lore(newLore); //todo() linked to the todo()'s above.
        item.setItemMeta(meta);

        return item;
    }

    private List<Component> getAllProtectionLore(PersistentDataContainerView container) { //todo() changed it to use the itemstack pdc instead of item meta
        List<Component> lore = new ArrayList<>();

        FileConfiguration config = Files.CONFIG.getFile(); // reduced file configuration calls.

        //todo() following the todo above will eliminate the need to call this, but we also don't have to rebuild the itemstack when deconstructing.
        if (Scrolls.hasWhiteScrollProtection(container)) lore.add(ColorUtils.legacyTranslateColourCodes(config.getString("Settings.WhiteScroll.ProtectedName")));
        if (ProtectionCrystalSettings.isProtected(container)) lore.add(ColorUtils.legacyTranslateColourCodes(config.getString("Settings.ProtectionCrystal.Protected")));

        return lore;
    }

    private List<Component> stripNonNormalLore(List<Component> lore, List<CEnchantment> enchantments) {  //todo() following the todo above will eliminate the need to call this, but we also don't have to rebuild the itemstack when deconstructing.

        // Remove blank lines
        lore.removeIf(loreComponent -> ColorUtils.toPlainText(loreComponent).replaceAll(" ", "").isEmpty());

        // Remove CE enchantment lore
        enchantments.forEach(enchant -> lore.removeIf(loreComponent ->
                ColorUtils.toPlainText(loreComponent).contains(ColorUtils.stripStringColour(enchant.getCustomName()))
        ));

        // Remove white-scroll protection lore
        lore.removeIf(loreComponent -> ColorUtils.toPlainText(loreComponent).contains(ColorUtils.stripStringColour(Scrolls.getWhiteScrollProtectionName())));

        // Remove Protection-crystal protection lore
        lore.removeIf(loreComponent -> ColorUtils.toPlainText(loreComponent).contains(
                ColorUtils.stripStringColour(Files.CONFIG.getFile().getString("Settings.ProtectionCrystal.Protected"))
        ));

        return lore;
    }

    private void useSuffix(ItemStack item, ItemMeta meta, List<CEnchantment> newEnchantmentOrder) { //todo() following the todo above will eliminate the need to call this, but we also don't have to rebuild the itemstack when deconstructing.
        if (this.useSuffix) {
            String newName = meta.hasDisplayName() ? ColorUtils.toLegacy(meta.displayName()) :
                    "&b" + WordUtils.capitalizeFully(item.getType().toString().replace("_", " "));

            if (meta.hasDisplayName()) {
                for (int i = 0; i <= 100; i++) {
                    String suffixWithAmount = this.suffix.replace("%Amount%", String.valueOf(i)).replace("%amount%", String.valueOf(i));

                    if (!newName.endsWith(suffixWithAmount)) continue;

                    newName = newName.substring(0, newName.length() - suffixWithAmount.length());
                    break;
                }
            }

            String amount = String.valueOf(this.countVanillaEnchantments ? newEnchantmentOrder.size() + item.getEnchantments().size() : newEnchantmentOrder.size());

            meta.displayName(ColorUtils.legacyTranslateColourCodes(newName + this.suffix.replace("%Amount%", amount).replace("%amount%", amount)));
        }
    }

    private void orderInts(List<CEnchantment> list, final Map<CEnchantment, Integer> map) {
        list.sort((a1, a2) -> {
            Integer string1 = map.get(a1);
            Integer string2 = map.get(a2);
            return string2.compareTo(string1);
        });
    }
}