package com.badbones69.crazyenchantments.paper.tasks.support.interfaces;

import org.bukkit.block.Block;

public interface ICropSupport {

    void fullyGrowPlant(final Block block);

    boolean isFullyGrown(final Block block);

    void hydrateSoil(final Block soil);

}