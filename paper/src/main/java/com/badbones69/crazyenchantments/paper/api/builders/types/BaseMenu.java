package com.badbones69.crazyenchantments.paper.api.builders.types;

import com.badbones69.crazyenchantments.paper.Starter;
import com.badbones69.crazyenchantments.paper.api.builders.InventoryBuilder;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.badbones69.crazyenchantments.paper.api.builders.types.gkitz.KitsManager;
import com.badbones69.crazyenchantments.paper.api.objects.CEnchantment;
import com.badbones69.crazyenchantments.paper.api.objects.enchants.EnchantmentType;
import com.badbones69.crazyenchantments.paper.controllers.settings.EnchantmentBookSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class BaseMenu extends InventoryBuilder {

    @NotNull
    private final Starter starter = this.plugin.getStarter();

    @NotNull
    private final EnchantmentBookSettings bookSettings = this.starter.getEnchantmentBookSettings();

    public BaseMenu(Player player, int size, String title) {
        super(player, size, title);
    }

    @Override
    public InventoryBuilder build() {
        if (getEnchantmentType() != null) {
            List<CEnchantment> enchantments = getEnchantmentType().getEnchantments();

            ItemBuilder book = this.bookSettings.getNormalBook().setGlowing(true);

            for (CEnchantment enchantment : enchantments) {
                if (enchantment.isActivated()) {
                    getInventory().addItem(book.setDisplayName(enchantment.getInfoName()).setDisplayLore(enchantment.getInfoDescription()).getStack());
                }
            }

            getInventory().setItem(getSize() - 1, KitsManager.getBackRight());
        } else {
            MenuManager.getEnchantmentTypes().forEach(key -> getInventory().setItem(key.getSlot(), key.getDisplayItem()));
        }

        return this;
    }

    public static class InfoMenuListener implements Listener {

        @EventHandler(ignoreCancelled = true)
        public void onInfoClick(InventoryClickEvent event) {
            if (!(event.getInventory().getHolder() instanceof BaseMenu holder)) return;

            event.setCancelled(true);

            Player player = holder.getPlayer();

            ItemStack itemStack = event.getCurrentItem();

            if (itemStack == null) return;

            if (!itemStack.hasItemMeta()) return; //todo() if we switch to pdc, and use item.getPersistentDataContainer. we should not need this hasItemMeta check

            ItemMeta itemMeta = itemStack.getItemMeta(); //todo() we also won't need this, we likely won't need this anywhere reading pdc values only.

            if (itemMeta == null) return; //todo() we also won't need this, same as above

            if (!itemMeta.hasDisplayName()) return; //todo() why does it need a display name?

            if (itemStack.isSimilar(KitsManager.getBackLeft()) || itemStack.isSimilar(KitsManager.getBackRight())) {
                //todo() add pdc for the left/right buttons, instead of the hefty isSimilar()
                // isSimilar checks everything
                MenuManager.openInfoMenu(player);

                return;
            }

            for (EnchantmentType enchantmentType : MenuManager.getEnchantmentTypes()) {
                if (itemStack.isSimilar(enchantmentType.getDisplayItem())) { //todo() USE PDC
                    MenuManager.openInfoMenu(player, enchantmentType);

                    return;
                }
            }
        }
    }
}