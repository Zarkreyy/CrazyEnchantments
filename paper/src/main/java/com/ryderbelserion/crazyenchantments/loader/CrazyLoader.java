package com.ryderbelserion.crazyenchantments.loader;

import com.ryderbelserion.crazyenchantments.CrazyEnchantments;
import com.ryderbelserion.crazyenchantments.enchants.EnchantmentRegistry;
import com.ryderbelserion.crazyenchantments.enchants.interfaces.CustomEnchantment;
import com.ryderbelserion.vital.paper.VitalPaper;
import com.ryderbelserion.vital.paper.api.files.FileManager;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.event.WritableRegistry;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.tag.PreFlattenTagRegistrar;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Set;

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

        final ComponentLogger logger = context.getLogger();

        this.registry = new EnchantmentRegistry(fileManager, logger);

        this.registry.populateEnchantments();
        this.registry.populateCurses();

        final Collection<CustomEnchantment> enchants = this.registry.getEnchantments().values();

        context.getLifecycleManager().registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.ITEM).newHandler((event) -> {
            final PreFlattenTagRegistrar<ItemType> registry = event.registrar();

            for (final CustomEnchantment enchant : enchants) {
                if (!enchant.isEnabled()) continue;

                logger.info("Registering item tag {}", enchant.getTagForSupportedItems().key());

                registry.addToTag(ItemTypeTagKeys.create(enchant.getTagForSupportedItems().key()), enchant.getSupportedItems());
            }
        }));

        context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler(event -> {
            final WritableRegistry<Enchantment, EnchantmentRegistryEntry.@NotNull Builder> registry = event.registry();

            for (final CustomEnchantment enchant : enchants) {
                if (!enchant.isEnabled()) continue;

                if (enchant.isCurse()) {
                    logger.info("Registering curse {}", enchant.getKey());
                } else {
                    logger.info("Registering enchantment {}", enchant.getKey());
                }

                registry.register(TypedKey.create(RegistryKey.ENCHANTMENT, enchant.getKey()), enchantment -> {
                    enchantment.description(enchant.getDescription());
                    enchantment.anvilCost(enchant.getAnvilCost());
                    enchantment.maxLevel(enchant.getMaxLevel());
                    enchantment.weight(enchant.getWeight());
                    enchantment.minimumCost(enchant.getMinimumCost());
                    enchantment.maximumCost(enchant.getMaximumCost());
                    enchantment.activeSlots(enchant.getActiveSlots());
                    enchantment.supportedItems(event.getOrCreateTag(enchant.getTagForSupportedItems()));
                });
            }
        }));

        context.getLifecycleManager().registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.ENCHANTMENT).newHandler((event) -> {
            final PreFlattenTagRegistrar<Enchantment> registry = event.registrar();

            for (final CustomEnchantment enchant : enchants) {
                if (!enchant.isEnabled()) continue;

                enchant.getEnchantTagKeys().forEach(enchantmentTagKey -> {
                    logger.info("Registering enchantment tag {}", enchantmentTagKey.key());

                    registry.addToTag(enchantmentTagKey, Set.of(enchant.getTagEntry()));
                });
            }
        }));
    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        return new CrazyEnchantments(this.vital, this.registry);
    }
}