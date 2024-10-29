package com.ryderbelserion.crazyenchantments;

import com.ryderbelserion.crazyenchantments.enchants.EnchantmentRegistry;
import com.ryderbelserion.vital.paper.VitalPaper;
import org.bukkit.plugin.java.JavaPlugin;

public class CrazyEnchantments extends JavaPlugin {

    private final EnchantmentRegistry registry;
    private final VitalPaper vital;

    public CrazyEnchantments(final VitalPaper vital, final EnchantmentRegistry registry) {
        this.registry = registry;
        this.vital = vital;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public final VitalPaper getVital() {
        return this.vital;
    }

    public final EnchantmentRegistry getRegistry() {
        return this.registry;
    }
}