package com.badbones69.crazyenchantments.paper.utils;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.ryderbelserion.vital.common.utils.StringUtils;
import com.ryderbelserion.vital.paper.util.DyeUtil;
import com.ryderbelserion.vital.paper.util.ItemUtil;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.ryderbelserion.vital.paper.util.ItemUtil.getEnchantment;

public class ItemUtils {

    private static @NotNull final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    /**
     * Converts an {@link ItemStack} to an {@link ItemBuilder}.
     *
     * @param player {@link Player}
     * @param itemStack the {@link ItemStack}
     * @return the {@link ItemBuilder}
     */
    public static ItemBuilder convertItemStack(Player player, ItemStack itemStack) {
        ItemBuilder itemBuilder = new ItemBuilder(itemStack.getType(), itemStack.getAmount());

        if (player != null) {
            itemBuilder.setPlayer(player);
        }

        return itemBuilder;
    }

    /**
     * Converts an {@link ItemStack} without a {@link Player}.
     *
     * @param itemStack the {@link ItemStack}
     * @return the {@link ItemBuilder}
     */
    public static ItemBuilder convertItemStack(ItemStack itemStack) {
        return convertItemStack(null, itemStack);
    }

    /**
     * Converts a {@link List <String>} to a list of {@link ItemBuilder}.
     *
     * @param itemStrings the {@link List<String>}
     * @return list of {@link ItemBuilder}
     */
    public static List<ItemBuilder> convertStringList(List<String> itemStrings) {
        return convertStringList(itemStrings, null);
    }

    /**
     * Converts a {@link List<String>} to a list of {@link ItemBuilder}.
     *
     * @param itemStrings the {@link List<String>}
     * @param section the section in the {@link org.bukkit.configuration.file.YamlConfiguration}
     * @return list of {@link ItemBuilder}
     */
    public static List<ItemBuilder> convertStringList(List<String> itemStrings, String section) {
        return itemStrings.stream().map(itemString -> convertString(itemString, section)).collect(Collectors.toList());
    }

    /**
     * Converts a {@link String} to an {@link ItemBuilder}.
     *
     * @param itemString the {@link String} you wish to convert
     * @return the {@link ItemBuilder}
     */
    public static ItemBuilder convertString(String itemString) {
        return convertString(itemString, null);
    }

    /**
     * Converts a {@link List<String>} to a list of {@link ItemBuilder}.
     *
     * @param itemString the {@link String} you wish to convert
     * @param section the section in the {@link org.bukkit.configuration.file.YamlConfiguration}
     * @return the {@link ItemBuilder}
     */
    public static ItemBuilder convertString(String itemString, String section) {
        ItemBuilder itemBuilder = new ItemBuilder();

        try {
            for (String optionString : itemString.split(", ")) {
                String option = optionString.split(":")[0];
                String value = optionString.replace(option + ":", "").replace(option, "");

                switch (option.toLowerCase()) {
                    case "item" -> itemBuilder.withType(value.toLowerCase());
                    case "data" -> itemBuilder = itemBuilder.fromBase64(value);
                    case "name" -> itemBuilder.setDisplayName(value);
                    case "mob" -> {
                        final EntityType type = ItemUtil.getEntity(value);

                        if (type != null) {
                            itemBuilder.setEntityType(type);
                        }
                    }
                    case "glowing" -> itemBuilder.setGlowing(Boolean.valueOf(value));
                    case "amount" -> {
                        final Optional<Number> amount = StringUtils.tryParseInt(value);
                        itemBuilder.setAmount(amount.map(Number::intValue).orElse(1));
                    }
                    case "damage" -> {
                        final Optional<Number> amount = StringUtils.tryParseInt(value);
                        itemBuilder.setDamage(amount.map(Number::intValue).orElse(1));
                    }
                    case "lore" -> itemBuilder.setDisplayLore(List.of(value.split(",")));
                    case "player" -> itemBuilder.setPlayer(value);
                    case "skull" -> itemBuilder.setSkull(value, plugin.getApi());
                    case "custom-model-data" -> itemBuilder.setCustomModelData(StringUtils.tryParseInt(value).orElse(-1).intValue());
                    case "unbreakable-item" -> itemBuilder.setUnbreakable(value.isEmpty() || value.equalsIgnoreCase("true"));
                    case "trim-pattern" -> itemBuilder.applyTrimPattern(value);
                    case "trim-material" -> itemBuilder.applyTrimMaterial(value);
                    default -> {
                        if (getEnchantment(option.toLowerCase()) != null) {
                            final Optional<Number> amount = StringUtils.tryParseInt(value);

                            itemBuilder.addEnchantment(option.toLowerCase(), amount.map(Number::intValue).orElse(1), true);

                            break;
                        }

                        for (ItemFlag itemFlag : ItemFlag.values()) {
                            if (itemFlag.name().equalsIgnoreCase(option)) {
                                itemBuilder.addItemFlag(itemFlag);

                                break;
                            }
                        }

                        try {
                            DyeColor color = DyeUtil.getDyeColor(value);

                            PatternType patternType = ItemUtil.getPatternType(option.toLowerCase());

                            if (patternType != null) {
                                itemBuilder.addPattern(patternType, color);
                            }
                        } catch (Exception ignored) {}
                    }
                }
            }
        } catch (Exception exception) {
            itemBuilder.withType(Material.RED_TERRACOTTA).setDisplayName("<red>Error found!, Section Name: " + section);

            plugin.getComponentLogger().warn("An error has occurred with the item builder: ", exception);
        }

        return itemBuilder;
    }
}