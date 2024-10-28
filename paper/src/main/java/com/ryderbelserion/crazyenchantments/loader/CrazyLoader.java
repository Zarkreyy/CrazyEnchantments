package com.ryderbelserion.crazyenchantments.loader;

import com.ryderbelserion.crazyenchantments.CrazyEnchantments;
import com.ryderbelserion.crazyenchantments.enchants.EnchantmentRegistry;
import com.ryderbelserion.vital.paper.VitalPaper;
import com.ryderbelserion.vital.paper.api.files.FileManager;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CrazyLoader implements PluginBootstrap {

    private EnchantmentRegistry registry;
    private VitalPaper vital;

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        this.vital = new VitalPaper(context);

        final FileManager fileManager = this.vital.getFileManager();

        fileManager.addFile("curses.yml", "types")
                .addFile("enchants.yml", "types")
                .init();

        this.registry = new EnchantmentRegistry(fileManager);

        this.registry.populateEnchantments();
        this.registry.populateCurses();

        /*final Set<CustomEnchantment> enchants = getCustomEnchantments();

        // Register a new handled for the freeze lifecycle event on the enchantment registry
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler(event -> {
            if (enchants.isEmpty()) return;

            for (CustomEnchantment enchantment : enchants) {
                if (enchantment == null) continue; // if null, do nothing
                if (!enchantment.isEnabled()) continue; // if is not enabled, do nothing

                final WritableRegistry<Enchantment, EnchantmentRegistryEntry.@NotNull Builder> registry = event.registry();

                final TypedKey<Enchantment> typedKey = TypedKey.create(RegistryKey.ENCHANTMENT, enchantment.getKey());

                registry.register(typedKey, builder -> {
                    builder.description(enchantment.getDescription());

                    builder.anvilCost(enchantment.getAnvilCost());

                    builder.activeSlots(enchantment.getActiveSlots());

                    builder.maxLevel(enchantment.getMaxLevel());

                    builder.weight(enchantment.getWeight());

                    builder.maximumCost(enchantment.getMaximumCost());

                    builder.minimumCost(enchantment.getMinimumCost());
                });
            }
        }));*/
    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        return new CrazyEnchantments(this.vital, this.registry);
    }
}