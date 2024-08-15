package com.badbones69.crazyenchantments.paper.tasks.support.types;

import com.badbones69.crazyenchantments.paper.tasks.support.interfaces.IProtection;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.youhavetrouble.yardwatch.Protection;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class GenericProtection extends IProtection {

    @Override
    public final boolean canDamage(final Player player, final Entity target) {
        if (isYardWatchEnabled()) return true;

        // if they can damage the target, continue until they can't then return false.
        for (final RegisteredServiceProvider<Protection> protection : getProtections()) {
            if (protection.getProvider().canDamage(player, target)) continue;

            return false;
        }

        return true;
    }

    @Override
    public final boolean canPlaceBlock(final Player player, final Location location) {
        if (isYardWatchEnabled()) return true;

        // if they can damage the target, continue until they can't then return false.
        for (final RegisteredServiceProvider<Protection> protection : getProtections()) {
            if (protection.getProvider().canPlaceBlock(player, location)) continue;

            return false;
        }

        return true;
    }

    @Override
    public final boolean canBreakBlock(final Player player, final BlockState blockState) {
        if (isYardWatchEnabled()) return true;

        // if they can damage the target, continue until they can't then return false.
        for (final RegisteredServiceProvider<Protection> protection : getProtections()) {
            if (protection.getProvider().canBreakBlock(player, blockState)) continue;

            return false;
        }

        return true;
    }

    @Override
    public final boolean canInteract(final Player player, final Entity entity) {
        if (isYardWatchEnabled()) return true;

        // if they can damage the target, continue until they can't then return false.
        for (final RegisteredServiceProvider<Protection> protection : getProtections()) {
            if (protection.getProvider().canInteract(player, entity)) continue;

            return false;
        }

        return true;
    }

    @Override
    public final boolean canInteract(final Player player, final BlockState blockState) {
        if (isYardWatchEnabled()) return true;

        // if they can damage the target, continue until they can't then return false.
        for (final RegisteredServiceProvider<Protection> protection : getProtections()) {
            if (protection.getProvider().canInteract(player, blockState)) continue;

            return false;
        }

        return true;
    }

    @Override
    public final boolean isProtected(final Location location) {
        if (isYardWatchEnabled()) return true;

        // if they can damage the target, continue until they can't then return false.
        for (final RegisteredServiceProvider<Protection> protection : getProtections()) {
            if (protection.getProvider().isProtected(location)) continue;

            return false;
        }

        return true;
    }

    @Override
    public final boolean inRegion(final Location location, final String regionName) {
        final BukkitWorld world = new BukkitWorld(location.getWorld());
        final BlockVector3 v = BlockVector3.at(location.getX(), location.getY(), location.getZ());

        boolean inRegion = false;

        try {
            final RegionManager set = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);

            if (set != null) {
                for (final ProtectedRegion region : set.getApplicableRegions(v).getRegions()) {
                    if (regionName.equalsIgnoreCase(region.getId())) {
                        inRegion = true;

                        break;
                    }
                }
            }
        } catch (NullPointerException exception) {
            return inRegion;
        }

        return inRegion;
    }

    @Override
    public final boolean isMember(final Player player) {
        final Location location = player.getLocation();

        final BukkitWorld world = new BukkitWorld(location.getWorld());
        final BlockVector3 vector3 = BlockVector3.at(location.getX(), location.getY(), location.getZ());

        boolean isMember = false;

        try {
            final RegionManager set = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);

            if (set != null) {
                for (final ProtectedRegion region : set.getApplicableRegions(vector3).getRegions()) {
                    if (region.getMembers().contains(player.getUniqueId())) {
                        isMember = true;

                        break;
                    }
                }
            }
        } catch (NullPointerException exception) {
            return isMember;
        }

        return isMember;
    }

    @Override
    public final boolean isOwner(final Player player) {
        final Location location = player.getLocation();

        final BukkitWorld world = new BukkitWorld(location.getWorld());
        final BlockVector3 vector3 = BlockVector3.at(location.getX(), location.getY(), location.getZ());

        boolean isOwner = false;

        try {
            final RegionManager set = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);

            if (set != null) {
                for (final ProtectedRegion region : set.getApplicableRegions(vector3).getRegions()) {
                    if (region.getOwners().contains(player.getUniqueId())) {
                        isOwner = true;

                        break;
                    }
                }
            }
        } catch (NullPointerException exception) {
            return isOwner;
        }

        return isOwner;
    }
}