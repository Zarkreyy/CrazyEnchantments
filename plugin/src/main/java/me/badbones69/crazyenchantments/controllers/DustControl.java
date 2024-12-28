package me.badbones69.crazyenchantments.controllers;

import me.badbones69.crazyenchantments.Methods;
import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import me.badbones69.crazyenchantments.api.FileManager.Files;
import me.badbones69.crazyenchantments.api.enums.Dust;
import me.badbones69.crazyenchantments.api.objects.CEBook;
import me.badbones69.crazyenchantments.api.objects.CEnchantment;
import me.badbones69.crazyenchantments.api.objects.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DustControl implements Listener {

    private static final CrazyEnchantments ce = CrazyEnchantments.getInstance();
    private final Random random = new Random();

    private static void setLore(ItemStack book, int percent, boolean success) {
        CEBook ceBook = ce.getCEBook(book);
        if (ceBook == null) return;
        ItemMeta itemMeta = book.getItemMeta();
        if (success) ceBook.setSuccessRate(percent);
        else ceBook.setDestroyRate(percent);
        itemMeta.setLore(ceBook.getItemBuilder().getUpdatedLore());
        book.setItemMeta(itemMeta);
    }

    private static String getPercentString(Dust dust, ItemStack item) {
        String arg = "";
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            List<String> fileLore = Files.CONFIG.getFile().getStringList("Settings.Dust." + dust.getConfigName() + ".Lore");
            int i = 0;
            if (lore != null && lore.size() == fileLore.size()) {
                for (String l : fileLore) {
                    l = Methods.color(l);
                    String lo = lore.get(i);
                    if (l.contains("%Percent%")) {
                        String[] b = l.split("%Percent%");
                        if (b.length >= 1) arg = lo.replace(b[0], "");
                        if (b.length >= 2) arg = arg.replace(b[1], "");
                        break;
                    }
                    if (l.contains("%percent%")) {
                        String[] b = l.split("%percent%");
                        if (b.length >= 1) arg = lo.replace(b[0], "");
                        if (b.length >= 2) arg = arg.replace(b[1], "");
                        break;
                    }
                    i++;
                }
            }
        }
        return arg;
    }

    public static boolean hasPercent(Dust dust, ItemStack item) {
        return Methods.isInt(getPercentString(dust, item));
    }

    public static Integer getPercent(Dust dust, ItemStack item) {
        String arg = getPercentString(dust, item);
        if (Methods.isInt(arg)) {
            return Integer.parseInt(arg);
        } else {
            return 0;
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        if (inv != null && e.getCurrentItem() != null && e.getCursor() != null) {
            ItemStack book = e.getCurrentItem();
            ItemStack dust = e.getCursor();
            if (book.getAmount() == 1 && book.hasItemMeta() && dust.hasItemMeta() && book.getItemMeta().hasLore() && dust.getItemMeta().hasLore() && book.getItemMeta().hasDisplayName() &&
                    dust.getItemMeta().hasDisplayName() && book.getType() == ce.getEnchantmentBookItem().getType()) {
                boolean toggle = false;
                String name = book.getItemMeta().getDisplayName();
                for (CEnchantment en : ce.getRegisteredEnchantments()) {
                    if (name.contains(Methods.color(en.getBookColor() + en.getCustomName()))) {
                        toggle = true;
                    }
                }
                if (!toggle) {
                    return;
                }
                List<String> bookLore = Files.CONFIG.getFile().getStringList("Settings.EnchantmentBookLore");
                if (dust.getItemMeta().getDisplayName().equals(Methods.color(Files.CONFIG.getFile().getString("Settings.Dust.SuccessDust.Name"))) &&
                        dust.getType() == new ItemBuilder().setMaterial(Files.CONFIG.getFile().getString("Settings.Dust.SuccessDust.Item")).getMaterial()) {
                    int percent = getPercent(Dust.SUCCESS_DUST, dust);
                    if (Methods.hasArgument("%success_rate%", bookLore)) {
                        int total = Methods.getPercent("%success_rate%", book, bookLore, 100);
                        if (total >= 100) return;
                        if (player.getGameMode() == GameMode.CREATIVE && dust.getAmount() > 1) {
                            player.sendMessage(Methods.getPrefix() + Methods.color("&cPlease unstack the dust for them to work."));
                            return;
                        }
                        percent += total;
                        if (percent < 0) percent = 0;
                        if (percent > 100) percent = 100;
                        e.setCancelled(true);
                        setLore(book, percent, true);
                        player.setItemOnCursor(Methods.removeItem(dust));
                        player.updateInventory();
                    }
                    return;
                }
                if (dust.getItemMeta().getDisplayName().equals(Methods.color(Files.CONFIG.getFile().getString("Settings.Dust.DestroyDust.Name"))) &&
                        dust.getType() == new ItemBuilder().setMaterial(Files.CONFIG.getFile().getString("Settings.Dust.DestroyDust.Item")).getMaterial()) {
                    int percent = getPercent(Dust.DESTROY_DUST, dust);
                    if (Methods.hasArgument("%destroy_rate%", bookLore)) {
                        int total = Methods.getPercent("%destroy_rate%", book, bookLore, 0);
                        if (total <= 0) return;
                        if (player.getGameMode() == GameMode.CREATIVE && dust.getAmount() > 1) {
                            player.sendMessage(Methods.getPrefix() + Methods.color("&cPlease unstack the dust for them to work."));
                            return;
                        }
                        percent = total - percent;
                        if (percent < 0) percent = 0;
                        if (percent > 100) percent = 100;
                        e.setCancelled(true);
                        setLore(book, percent, false);
                        player.setItemOnCursor(Methods.removeItem(dust));
                        player.updateInventory();
                    }
                }
            }
        }
    }

    @EventHandler
    public void openDust(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        FileConfiguration config = Files.CONFIG.getFile();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = Methods.getItemInHand(player);
            if (item != null) {
                if (hasPercent(Dust.SUCCESS_DUST, item)) {
                    if (Methods.isSimilar(item, Dust.SUCCESS_DUST.getDust(getPercent(Dust.SUCCESS_DUST, item), 1))) {
                        e.setCancelled(true);
                    }
                } else if (hasPercent(Dust.DESTROY_DUST, item)) {
                    if (Methods.isSimilar(item, Dust.DESTROY_DUST.getDust(getPercent(Dust.DESTROY_DUST, item), 1))) {
                        e.setCancelled(true);
                    }
                } else if (hasPercent(Dust.MYSTERY_DUST, item) && Methods.isSimilar(item, Dust.MYSTERY_DUST.getDust(getPercent(Dust.MYSTERY_DUST, item), 1))) {
                    e.setCancelled(true);
                    Methods.setItemInHand(player, Methods.removeItem(item));
                    player.getInventory().addItem(pickDust().getDust(Methods.percentPick(getPercent(Dust.MYSTERY_DUST, item) + 1, 1), 1));
                    player.updateInventory();
                    player.playSound(player.getLocation(), ce.getSound("BLOCK_LAVA_POP", "LAVA_POP"), 1, 1);
                    if (config.getBoolean("Settings.Dust.MysteryDust.Firework.Toggle")) {
                        String colorString = config.getString("Settings.Dust.MysteryDust.Firework.Colors", "Black, Gray, Lime");
                        List<Color> colors = ce.getColors(colorString);
                        Methods.fireWork(player.getLocation().add(0, 1, 0), colors);
                    }
                }
            }
        }
    }

    private Dust pickDust() {
        List<Dust> dusts = new ArrayList<>();
        if (Files.CONFIG.getFile().getBoolean("Settings.Dust.MysteryDust.Dust-Toggle.Success")) {
            dusts.add(Dust.SUCCESS_DUST);
        }
        if (Files.CONFIG.getFile().getBoolean("Settings.Dust.MysteryDust.Dust-Toggle.Destroy")) {
            dusts.add(Dust.DESTROY_DUST);
        }
        if (Files.CONFIG.getFile().getBoolean("Settings.Dust.MysteryDust.Dust-Toggle.Failed")) {
            dusts.add(Dust.FAILED_DUST);
        }
        return dusts.get(random.nextInt(dusts.size()));
    }

}