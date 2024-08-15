package com.badbones69.crazyenchantments.paper.tasks.support.types.currency;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.ryderbelserion.vital.paper.api.plugins.interfaces.Plugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class VaultCurrency implements Plugin {

    private final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    private Economy economy = null;

    @Override
    public final boolean isEnabled() {
        return this.plugin.getServer().getPluginManager().isPluginEnabled(getName());
    }

    @Override
    public final @NotNull String getName() {
        return "Vault";
    }

    @Override
    public void init() {
        final RegisteredServiceProvider<Economy> provider = this.plugin.getServer().getServicesManager().getRegistration(Economy.class);

        if (provider != null) {
            this.economy = provider.getProvider();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public final Economy get() {
        return this.economy;
    }
}