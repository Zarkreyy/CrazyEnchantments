package com.badbones69.crazyenchantments.paper.tasks.support;

import com.badbones69.crazyenchantments.paper.tasks.support.enums.Currency;
import com.badbones69.crazyenchantments.paper.tasks.support.types.GenericProtection;
import com.badbones69.crazyenchantments.paper.tasks.support.types.currency.VaultCurrency;
import com.badbones69.crazyenchantments.paper.tasks.support.types.items.CropSupport;
import com.gmail.nossr50.api.PartyAPI;
import com.ryderbelserion.vital.paper.api.enums.Support;
import com.ryderbelserion.vital.paper.api.plugins.PluginManager;
import com.ryderbelserion.vital.paper.api.plugins.interfaces.Plugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class SupportManager {

    private static GenericProtection protection;
    private static CropSupport cropSupport;
    private static VaultCurrency vaultCurrency;

    public static void init() {
        protection = new GenericProtection();
        cropSupport = new CropSupport();

        List.of(
                new VaultCurrency()
        ).forEach(plugin -> {
            // Don't register if not enabled.
            if (!plugin.isEnabled()) return;

            PluginManager.registerPlugin(plugin);
        });

        final Plugin vault = getPlugin("Vault");

        if (vault != null) {
            vaultCurrency = (VaultCurrency) vault;
        }
    }

    public static GenericProtection getProtection() {
        return protection;
    }

    public static CropSupport getCropSupport() {
        return cropSupport;
    }

    public static boolean isVanished(final Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }

        return false;
    }

    public static boolean isInParty(final Player player, final Player other) {
        if (!Support.mcmmo.isEnabled()) return true;

        return PartyAPI.inSameParty(player, other);
    }

    public static int getCurrency(final Player player, final Currency currency) {
        return switch (currency) {
            case VAULT -> (int) vaultCurrency.get().getBalance(player);
            case XP_LEVEL -> player.getLevel();
            case XP_TOTAL -> player.getTotalExperience();
        };
    }

    public static void takeCurrency(Player player, final Currency currency, final int cost) {
        switch (currency) {
            case VAULT -> vaultCurrency.get().withdrawPlayer(player, cost);
            case XP_LEVEL -> player.setLevel(player.getLevel() - cost);
            case XP_TOTAL -> player.setTotalExperience(player.getTotalExperience() - cost);
        }
    }

    public static void giveCurrency(final Player player, final Currency currency, final int amount) {
        switch (currency) {
            case VAULT -> vaultCurrency.get().withdrawPlayer(player, amount);
            case XP_LEVEL -> player.setLevel(player.getLevel() + amount);
            case XP_TOTAL -> player.setTotalExperience(player.getTotalExperience() + amount);
        }
    }

    public static boolean canBuy(final Player player, final Currency currency, final int cost) {
        return getCurrency(player, currency) >= cost;
    }

    public static Economy getVaultCurrency() {
        return vaultCurrency.get();
    }

    public static @Nullable Plugin getPlugin(final String name) {
        return PluginManager.getPlugin(name);
    }
}