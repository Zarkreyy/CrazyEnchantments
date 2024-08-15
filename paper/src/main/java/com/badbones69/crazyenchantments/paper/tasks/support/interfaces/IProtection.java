package com.badbones69.crazyenchantments.paper.tasks.support.interfaces;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.ryderbelserion.vital.paper.api.enums.Support;
import me.youhavetrouble.yardwatch.Protection;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Collection;

public abstract class IProtection {

    protected final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    public abstract boolean canDamage(final Player player, final Entity target);

    public abstract boolean canPlaceBlock(final Player player, final Location location);

    public abstract boolean canBreakBlock(final Player player, final BlockState blockState);

    public abstract boolean canInteract(final Player player, final Entity entity);

    public abstract boolean canInteract(final Player player, final BlockState blockState);

    public abstract boolean isProtected(final Location location);

    public abstract boolean inRegion(final Location location, final String regionName);

    public abstract boolean isMember(final Player player);

    public abstract boolean isOwner(final Player player);

    public final boolean isYardWatchEnabled() {
        return Support.yard_watch.isEnabled();
    }

    public final boolean isWorldGuardEnabled() {
        return Support.world_guard.isEnabled();
    }

    public final Collection<RegisteredServiceProvider<Protection>> getProtections() {
        return this.plugin.getServer().getServicesManager().getRegistrations(Protection.class);
    }
}