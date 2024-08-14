package com.badbones69.crazyenchantments.paper.utils;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.ryderbelserion.vital.common.utils.FileUtil;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {

    private @NotNull final static CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    public static void loadFiles() {
        Path path = plugin.getDataFolder().toPath();
        Class<? extends @NotNull CrazyEnchantments> classObject = plugin.getClass();

        List.of(
                "BlockList.yml",
                "config.yml",
                "Data.yml",
                "Enchantment-Types.yml",
                "Enchantments.yml",
                "GKitz.yml",
                "HeadMap.yml",
                "Messages.yml",
                "Tinker.yml"
        ).forEach(file -> FileUtil.extract(file, "examples", true));
    }
}