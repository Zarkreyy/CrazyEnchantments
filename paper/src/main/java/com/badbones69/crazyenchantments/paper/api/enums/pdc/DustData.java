package com.badbones69.crazyenchantments.paper.api.enums.pdc;

public class DustData {

    private final String name;
    private final int min;
    private final int max;
    private final int chance;

    public DustData(final String name, final int min, final int max, final int chance) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.chance = chance;
    }

    public final int getMax() {
        return this.max;
    }

    public final int getMin() {
        return this.min;
    }

    public final String getConfigName() {
        return this.name;
    }

    public final int getChance() {
        return this.chance;
    }
}