package com.badbones69.crazyenchantments.paper.api.builders;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.badbones69.crazyenchantments.paper.Starter;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.DataKeys;
import com.badbones69.crazyenchantments.paper.api.enums.pdc.Enchant;
import com.badbones69.crazyenchantments.paper.api.objects.CEnchantment;
import com.badbones69.crazyenchantments.paper.api.utils.ColorUtils;
import com.badbones69.crazyenchantments.paper.api.utils.NumberUtils;
import com.badbones69.crazyenchantments.paper.controllers.settings.EnchantmentBookSettings;
import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("CopyConstructorMissesField")
public class ItemBuilder extends com.ryderbelserion.vital.paper.api.builders.items.ItemBuilder<ItemBuilder> {

    private final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    private final Starter starter = plugin.getStarter();

    private final SkullCreator skullCreator = this.starter.getSkullCreator();

    private Map<CEnchantment, Integer> customEnchantments = new HashMap<>();

    private final Map<NamespacedKey, String> keys = new HashMap<>();

    public ItemBuilder() {}

    public ItemBuilder(final Material material, final int amount) {
        super(material, amount);
    }

    public ItemBuilder(final ItemStack itemStack) {
        super(itemStack, false);
    }

    public ItemBuilder(final ItemBuilder itemBuilder) {
        super(itemBuilder);

        itemBuilder.customEnchantments = customEnchantments;
    }

    @Override
    public final @NotNull ItemStack getStack() {
        return super.getStack(itemMeta -> {
            if (!this.keys.isEmpty()) {
                final PersistentDataContainer container = itemMeta.getPersistentDataContainer();

                this.keys.forEach((key, data) -> container.set(key, PersistentDataType.STRING, data));
            }

            //todo() everything below here really needs to be looked at, including the method in CrazyManager that does the same. it just feels awful
            Map<CEnchantment, Integer> enchantments = this.customEnchantments;

            EnchantmentBookSettings enchantmentBookSettings = this.starter.getEnchantmentBookSettings();

            Gson gson = new Gson();

            Map<CEnchantment, Integer> currentEnchantments = enchantmentBookSettings.getEnchantments(itemMeta);

            itemMeta = enchantmentBookSettings.removeEnchantments(itemMeta, enchantments.keySet().stream().filter(currentEnchantments::containsKey).toList());

            String data = itemMeta.getPersistentDataContainer().get(DataKeys.enchantments.getNamespacedKey(), PersistentDataType.STRING);
            Enchant enchantData = data != null ? gson.fromJson(data, Enchant.class) : new Enchant(new HashMap<>());

            List<Component> lore = itemMeta.lore();
            if (lore == null) lore = new ArrayList<>();

            for (Map.Entry<CEnchantment, Integer> entry : enchantments.entrySet()) {
                CEnchantment enchantment = entry.getKey();
                int level = entry.getValue();

                String loreString = enchantment.getCustomName() + " " + NumberUtils.convertLevelString(level);

                lore.add(ColorUtils.legacyTranslateColourCodes(loreString));

                for (Map.Entry<CEnchantment, Integer> x : enchantments.entrySet()) {
                    enchantData.addEnchantment(x.getKey().getName(), x.getValue());
                }
            }

            itemMeta.lore(lore);
            itemMeta.getPersistentDataContainer().set(DataKeys.enchantments.getNamespacedKey(), PersistentDataType.STRING, gson.toJson(enchantData));
        });
    }

    /**
     * @param key The name spaced key value.
     * @param data The data that the key holds.
     * @return The ItemBuilder with an updated item count.
     */
    public final ItemBuilder addKey(final NamespacedKey key, final String data) {
        this.keys.put(key, data);

        return this;
    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment The enchantment you wish to add.
     * @param level The level of the enchantment ( Unsafe levels included )
     * @return The ItemBuilder with updated enchantments.
     */
    public final ItemBuilder addCustomEnchantment(CEnchantment enchantment, int level) {
        this.customEnchantments.put(enchantment, level);

        return this;
    }

    /**
     * Gets a list of custom enchantments
     *
     * @return the map of custom enchantments
     */
    public final Map<CEnchantment, Integer> getCustomEnchantments() {
        return this.customEnchantments;
    }
}