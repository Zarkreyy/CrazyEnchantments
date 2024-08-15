package com.badbones69.crazyenchantments.paper.tasks.support.types.items;

import com.badbones69.crazyenchantments.paper.tasks.support.interfaces.ICropSupport;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Farmland;

public class CropSupport implements ICropSupport {

    @Override
    public void fullyGrowPlant(final Block block) {
        if (block.getBlockData() instanceof Ageable age) {
            if (age.getAge() < age.getMaximumAge()) {
                age.setAge(age.getMaximumAge());

                block.setBlockData(age);
            }
        }
    }

    @Override
    public final boolean isFullyGrown(final Block block) {
        if (block.getBlockData() instanceof Ageable age) return age.getAge() == age.getMaximumAge();

        return false;
    }

    @Override
    public void hydrateSoil(final Block soil) {
        final Farmland farmland = (Farmland) soil.getBlockData();

        farmland.setMoisture(farmland.getMaximumMoisture());
        soil.setBlockData(farmland);
    }
}