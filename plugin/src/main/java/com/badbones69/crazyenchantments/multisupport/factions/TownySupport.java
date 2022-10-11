package com.badbones69.crazyenchantments.multisupport.factions;

import com.badbones69.crazyenchantments.multisupport.FactionPlugin;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.utils.CombatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class TownySupport implements FactionPlugin {
    
    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("Towny");
    
    public boolean inTerritory(Player player) {
        try {
            TownyAPI api = TownyAPI.getInstance();
            TownBlock block = api.getTownBlock(player.getLocation());
            Resident resident = api.getDataSource().getResident(player.getName());
            if (block != null && block.hasTown() && resident.hasTown() && resident.getTown().equals(block.getTown())) {
                return true;
            }
        } catch (NotRegisteredException ignored) {}
        return false;
    }
    
    public boolean isFriendly(Player player, Player other) {
        return CombatUtil.preventDamageCall((Towny) plugin, player, other, EntityDamageEvent.DamageCause.CONTACT);
    }
    
    public boolean canBreakBlock(Player player, Block block) {
        return true;
    }
    
    public static boolean allowsPvP(Location location) {
        try {
            TownBlock block = TownyAPI.getInstance().getTownBlock(location);
            if (block != null && block.hasTown() && !block.getTown().isPVP()) {
                return false;
            }
        } catch (NotRegisteredException ignored) {}
        return true;
    }
}