package com.ryderbelserion.crazyenchantments.loader;

import com.ryderbelserion.crazyenchantments.CrazyEnchantments;
import com.ryderbelserion.vital.paper.VitalPaper;
import com.ryderbelserion.vital.paper.api.files.FileManager;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import io.papermc.paper.registry.event.RegistryEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CrazyLoader implements PluginBootstrap {

    private VitalPaper vital;

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        this.vital = new VitalPaper(context);

        //this.fileManager.addFile("enchantments.yml");

        final FileManager fileManager = this.vital.getFileManager();

        fileManager.addFile("enchantments.yml");

    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        return new CrazyEnchantments(this.vital);
    }
}