package com.badbones69.crazyenchantments.paper.api.utils;

import com.badbones69.crazyenchantments.paper.api.FileManager.Files;
import com.badbones69.crazyenchantments.paper.api.builders.ItemBuilder;
import com.ryderbelserion.vital.paper.util.DyeUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Matcher.quoteReplacement;

public class ColorUtils {

    public static void color(List<Color> colors, String colorString) {
        if (colorString.contains(", ")) {
            for (String key : colorString.split(", ")) {
                Color color = DyeUtil.getColor(key);

                if (color != null) colors.add(color);
            }
        } else {
            Color color = DyeUtil.getColor(colorString);

            if (color != null) colors.add(color);
        }
    }

    public static String color(String message) { //todo() remove this shit
        Matcher matcher = Pattern.compile("#[a-fA-F\\d]{6}").matcher(message);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static void sendMessage(CommandSender commandSender, String message, boolean prefixToggle) { //todo() move this into messages enum after re-doing the config files and message enum
        if (message == null || message.isEmpty()) return;

        String prefix = getPrefix();

        if (commandSender instanceof Player player) {
            if (!prefix.isEmpty() && prefixToggle) player.sendMessage(color(message.replaceAll("%prefix%", quoteReplacement(prefix))).replaceAll("%Prefix%", quoteReplacement(prefix))); else player.sendMessage(color(message));

            return;
        }

        if (!prefix.isEmpty() && prefixToggle) commandSender.sendMessage(color(message.replaceAll("%prefix%", quoteReplacement(prefix))).replaceAll("%Prefix%", quoteReplacement(prefix))); else commandSender.sendMessage(color(message));
    }

    public static String getPrefix() { //todo() move this into messages enum after re-doing the config files and message enum
        return color(Files.CONFIG.getFile().getString("Settings.Prefix"));
    }

    public static String getPrefix(String msg) { //todo() move this into messages enum after re-doing the config files and message enum
        return color(getPrefix() + msg);
    }

    public static String sanitizeColor(String msg) { //todo() see about removing this
        return sanitizeFormat(color(msg));
    }

    public static String sanitizeFormat(String string) { //todo() see about removing this
        return TextComponent.toLegacyText(TextComponent.fromLegacyText(string));
    }

    public static String removeColor(String msg) { //todo() see about removing this
        return ChatColor.stripColor(msg);
    }

    public static net.kyori.adventure.text.TextComponent legacyTranslateColourCodes(String input) { //todo() see about removing this
        return (net.kyori.adventure.text.TextComponent) LegacyComponentSerializer.legacyAmpersand().deserialize(input).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public static String toLegacy(Component text) { //todo() see about removing this
        return LegacyComponentSerializer.legacyAmpersand().serialize(text).replaceAll("§", "&").replaceAll("&&", "&");
    }

    public static String toPlainText(Component text) { //todo() see about removing this
        return PlainTextComponentSerializer.plainText().serialize(text);
    }

    public static ItemBuilder getRandomPaneColor() {
        List<String> colors = Arrays.asList(
                "white_stained_glass_pane",
                "orange_stained_glass_pane",
                "magenta_stained_glass_pane",
                "light_blue_stained_glass_pane",
                "yellow_stained_glass_pane",
                "lime_stained_glass_pane",
                "pink_stained_glass_pane",
                "gray_stained_glass_pane",
                // skipped 8 due to it being basically invisible in a gui.
                "cyan_stained_glass_pane",
                "purple_stained_glass_pane",
                "blue_stained_glass_pane",
                "brown_stained_glass_pane",
                "green_stained_glass_pane",
                "red_stained_glass_pane",
                "black_stained_glass_pane");

        return new ItemBuilder().withType(colors.get(ThreadLocalRandom.current().nextInt(colors.size())));
    }

    public static String stripStringColour(String key) { //todo() see about removing this
        return key.replaceAll("([&§]?#[0-9a-fA-F]{6}|[&§][1-9a-fA-Fk-or])", "");
    }
}