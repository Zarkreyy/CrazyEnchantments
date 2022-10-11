package com.badbones69.crazyenchantments.multisupport;

import com.badbones69.crazyenchantments.Methods;
import com.badbones69.crazyenchantments.api.CrazyEnchantments;
import com.badbones69.crazyenchantments.api.managers.WingsManager;
import com.badbones69.crazyenchantments.multisupport.factions.MCMMOParty;
import com.badbones69.crazyenchantments.multisupport.mobstackers.MobStacker;
import com.badbones69.crazyenchantments.multisupport.mobstackers.MobStacker2;
import com.badbones69.crazyenchantments.multisupport.plotsquared.PlotSquaredVersion;
import com.badbones69.crazyenchantments.multisupport.skyblocks.SuperiorSkyblockSupport;
import com.badbones69.crazyenchantments.multisupport.worldguard.WorldGuardVersion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Support {
    
    private static Support instance = new Support();
    //private static FactionPlugin factionPlugin = null;
    private CrazyEnchantments ce = CrazyEnchantments.getInstance();
    private WingsManager wingsManager;
    private WorldGuardVersion worldGuardVersion;
    private PlotSquaredVersion plotSquaredVersion;
    
    public static Support getInstance() {
        return instance;
    }
    
    public void load() {
        wingsManager = ce.getWingsManager();
        worldGuardVersion = ce.getWorldGuardSupport();
        plotSquaredVersion = ce.getPlotSquaredSupport();
    }
    
    public boolean inTerritory(Player player) {
        //if (factionPlugin != null && factionPlugin.inTerritory(player)) {
        //    return true;
        //}
        return SupportedPlugins.SUPERIOR_SKYBLOCK.isPluginLoaded() && SuperiorSkyblockSupport.inTerritory(player);
    }
    
    public boolean isFriendly(Entity pEntity, Entity oEntity) {
        if (pEntity instanceof Player && oEntity instanceof Player) {
            Player player = (Player) pEntity;
            Player other = (Player) oEntity;
            //if (factionPlugin != null && factionPlugin.isFriendly(player, other)) {
            //    return true;
            //}

            if (SupportedPlugins.SUPERIOR_SKYBLOCK.isPluginLoaded() && SuperiorSkyblockSupport.isFriendly(player, other)) {
                return true;
            }

            return SupportedPlugins.MCMMO.isPluginLoaded() && MCMMOParty.isFriendly(player, other);
        }
        return false;
    }
    
    public boolean isVanished(Entity p) {
        for (MetadataValue meta : p.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
    
    public boolean canBreakBlock(Player player, Block block) {
        if (player != null) {
            //if (factionPlugin != null && !factionPlugin.canBreakBlock(player, block)) {
            //    return false;
            //}
            //return !SupportedPlugins.PRECIOUS_STONES.isPluginLoaded() || PreciousStonesSupport.canBreakBlock(player, block);
        }

        return true;
    }
    
    public boolean allowsPVP(Location location) {
        //if (SupportedPlugins.TOWNY.isPluginLoaded() && !TownySupport.allowsPvP(location)) {
        //    return false;
        //}

        return !SupportedPlugins.WORLD_EDIT.isPluginLoaded() || !SupportedPlugins.WORLD_GUARD.isPluginLoaded() || worldGuardVersion.allowsPVP(location);
    }
    
    public boolean allowsBreak(Location location) {
        return !SupportedPlugins.WORLD_EDIT.isPluginLoaded() || !SupportedPlugins.WORLD_GUARD.isPluginLoaded() || worldGuardVersion.allowsBreak(location);
    }
    
    public boolean allowsExplotions(Location location) {
        return !SupportedPlugins.WORLD_EDIT.isPluginLoaded() || !SupportedPlugins.WORLD_GUARD.isPluginLoaded() || worldGuardVersion.allowsExplosions(location);
    }
    
    public boolean inWingsRegion(Player player) {
        if (SupportedPlugins.WORLD_EDIT.isPluginLoaded() && SupportedPlugins.WORLD_GUARD.isPluginLoaded()) {
            for (String region : wingsManager.getRegions()) {
                if (worldGuardVersion.inRegion(region, player.getLocation())) {
                    return true;
                } else {
                    if (wingsManager.canOwnersFly() && worldGuardVersion.isOwner(player)) {
                        return true;
                    }
                    if (wingsManager.canMembersFly() && worldGuardVersion.isMember(player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void noStack(Entity entity) {
        if (SupportedPlugins.MOB_STACKER.isPluginLoaded()) {
            MobStacker.noStack(entity);
        }
        if (SupportedPlugins.MOB_STACKER_2.isPluginLoaded()) {
            MobStacker2.noStack(entity);
        }
        if (SupportedPlugins.STACK_MOB.isPluginLoaded()) {
            //StackMobSupport.preventStacking(entity);
        }
    }
    
    public enum SupportedPlugins {
        
        MCMMO("mcMMO"),
        GRIEF_PREVENTION("GriefPrevention"),
        TOWNY("Towny"),
        AAC("AAC"),
        NO_CHEAT_PLUS("NoCheatPlus"),
        VAULT("Vault"),
        WORLD_EDIT("WorldEdit"),
        WORLD_GUARD("WorldGuard"),
        FACTIONS_UUID("Factions"),
        ACID_ISLAND("AcidIsland"),
        ASKYBLOCK("ASkyBlock"),
        SUPERIOR_SKYBLOCK("SuperiorSkyblock2"),
        SILK_SPAWNERS("SilkSpawners"),
        SPARTAN("Spartan"),
        MOB_STACKER("MobStacker"),
        MOB_STACKER_2("MobStacker2"),
        STACK_MOB("StackMob"),
        MEGA_SKILLS("MegaSkills"),
        PRECIOUS_STONES("PreciousStones");
        
        private String name;
        private static Map<SupportedPlugins, Boolean> cachedPluginState = new HashMap<>();
        
        private SupportedPlugins(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
        public boolean isPluginLoaded() {
            return cachedPluginState.get(this);
        }
        
        public Plugin getPlugin() {
            return Bukkit.getServer().getPluginManager().getPlugin(name);
        }
        
        /**
         * Used to update the states of plugins CE hooks into.
         */
        public static void updatePluginStates() {
            cachedPluginState.clear();
            for (SupportedPlugins supportedPlugin : values()) {
                Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(supportedPlugin.name);
                if (plugin != null && plugin.isEnabled()) {
                    List<String> authors = plugin.getDescription().getAuthors();
                    String version = plugin.getDescription().getVersion();
                    String website = plugin.getDescription().getWebsite() != null ? plugin.getDescription().getWebsite() : "";
                    switch (supportedPlugin) {
                        case SILK_SPAWNERS:
                            cachedPluginState.put(supportedPlugin, authors.contains("xGhOsTkiLLeRx"));
                            break;
                        case FACTIONS_UUID:
                            cachedPluginState.put(supportedPlugin, website.equalsIgnoreCase("https://www.spigotmc.org/resources/factionsuuid.1035/"));
                            break;
                        case STACK_MOB:
                            //CE does not support StackMob Legacy due to issues with package naming.
                            int v = Methods.isInt(version.split("\\.")[0]) ? Integer.parseInt(version.split("\\.")[0]) : 2;
                            cachedPluginState.put(supportedPlugin, v >= 3);
                            break;
                        case MCMMO:
                            //CE does not support MCMMO classic. They use different website links in the plugin.yml.
                            cachedPluginState.put(supportedPlugin, website.equalsIgnoreCase("https://www.mcmmo.org"));
                            break;
                        default:
                            cachedPluginState.put(supportedPlugin, true);
                            break;
                    }
                } else {
                    cachedPluginState.put(supportedPlugin, false);
                }
            }
            updateFactionPlugin();
        }
        
        public static void printHooks() {
            if (cachedPluginState.isEmpty()) updatePluginStates();
            Bukkit.getLogger().info(Methods.color("&4&lCrazy Enchantment Hooks"));
            for (SupportedPlugins plugin : cachedPluginState.keySet()) {
                if (plugin.isPluginLoaded()) {
                    Bukkit.getLogger().info(Methods.color("&6&l" + plugin.name() + ": &a&lEnabled"));
                }
            }
        }
        
        private static void updateFactionPlugin() {
            for (SupportedPlugins supportedPlugin : values()) {
                if (supportedPlugin.isPluginLoaded()) {
                    switch (supportedPlugin) {
                        case GRIEF_PREVENTION:
                            //factionPlugin = new GriefPreventionSupport();
                            return;
                        case TOWNY:
                            //factionPlugin = new TownySupport();
                            return;
                        case FACTIONS_UUID:
                            //factionPlugin = new FactionsUUID();
                            return;
                    }
                }
            }
        }
    }
    
}