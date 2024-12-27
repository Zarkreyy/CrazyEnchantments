package me.badbones69.crazyenchantments.api.objects;

import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlockProcessInfo {

    private final ItemStack item;
    private final Block block;
    private final List<ItemStack> drops;
    private final boolean isSpawner;

    public BlockProcessInfo(ItemStack item, Block block) {
        this.item = item;
        this.block = block;
        this.drops = new ArrayList<>(block.getDrops(item));
        isSpawner = block.getType() == CrazyEnchantments.getInstance().getMaterial("SPAWNER", "MOB_SPAWNER");
    }

    public ItemStack getItem() {
        return item;
    }

    public Block getBlock() {
        return block;
    }

    public List<ItemStack> getDrops() {
        return drops;
    }

    public boolean isSpawner() {
        return isSpawner;
    }

}