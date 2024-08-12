package com.badbones69.crazyenchantments.paper.api.builders.types;

import com.badbones69.crazyenchantments.paper.Starter;
import com.badbones69.crazyenchantments.paper.api.builders.InventoryBuilder;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.badbones69.crazyenchantments.paper.api.economy.Currency;
import com.badbones69.crazyenchantments.paper.api.economy.CurrencyAPI;
import com.badbones69.crazyenchantments.paper.api.managers.ShopManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;

public class ShopMenu extends InventoryBuilder {

    @NotNull
    private final Starter starter = this.plugin.getStarter();

    @NotNull
    private final CurrencyAPI currencyAPI = this.starter.getCurrencyAPI();

    private final ShopManager shopManager = this.starter.getShopManager();

    public ShopMenu(Player player, int size, String title) {
        super(player, size, title);
    }

    @Override
    public InventoryBuilder build() {
        HashMap<String, String> placeholders = new HashMap<>();

        for (Currency currency : Currency.values()) {
            placeholders.put("%" + currency.getName() + "%", String.valueOf(this.currencyAPI.getCurrency(getPlayer(), currency)));
        }

        for (Map.Entry<ItemBuilder, Integer> itemBuilders : this.shopManager.getCustomizerItems().entrySet()) {
            itemBuilders.getKey().setNamePlaceholders(placeholders).setLorePlaceholders(placeholders);
            getInventory().setItem(itemBuilders.getValue(), itemBuilders.getKey().getStack());
        }

        this.shopManager.getShopItems().keySet().forEach(itemBuilder -> getInventory().setItem(this.shopManager.getShopItems().get(itemBuilder), itemBuilder.getStack()));

        return this;
    }
}