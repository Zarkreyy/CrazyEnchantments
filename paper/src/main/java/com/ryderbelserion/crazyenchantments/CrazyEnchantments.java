package com.ryderbelserion.crazyenchantments;

import com.ryderbelserion.vital.paper.VitalPaper;
import org.bukkit.plugin.java.JavaPlugin;

public class CrazyEnchantments extends JavaPlugin {

    private final VitalPaper vital;

    public CrazyEnchantments(final VitalPaper vital) {
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
}