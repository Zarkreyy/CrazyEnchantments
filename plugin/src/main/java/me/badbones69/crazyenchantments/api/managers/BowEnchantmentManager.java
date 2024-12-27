package me.badbones69.crazyenchantments.api.managers;

import me.badbones69.crazyenchantments.api.enums.CEnchantments;
import me.badbones69.crazyenchantments.api.objects.BowEnchantment;
import me.badbones69.crazyenchantments.api.objects.PotionEffects;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BowEnchantmentManager {

    private static final BowEnchantmentManager instance = new BowEnchantmentManager();
    private final List<BowEnchantment> bowEnchantments = new ArrayList<>();

    public static BowEnchantmentManager getInstance() {
        return instance;
    }

    public void load() {
        bowEnchantments.clear();
        if (CEnchantments.PIERCING.isActivated()) {
            bowEnchantments.add(new BowEnchantment(CEnchantments.PIERCING, 2, false));
        }
        if (CEnchantments.ICEFREEZE.isActivated()) {
            bowEnchantments.add(new BowEnchantment(CEnchantments.ICEFREEZE, Collections.singletonList(new PotionEffects(PotionEffectType.SLOW, 5 * 20, 1)), false));
        }
        if (CEnchantments.VENOM.isActivated()) {
            bowEnchantments.add(new BowEnchantment(CEnchantments.VENOM, Collections.singletonList(new PotionEffects(PotionEffectType.POISON, 2 * 20, -1)), true));
        }
        if (CEnchantments.SNIPER.isActivated()) {
            bowEnchantments.add(new BowEnchantment(CEnchantments.SNIPER, Collections.singletonList(new PotionEffects(PotionEffectType.POISON, 5 * 20, 1)), false));
        }
    }

    public List<BowEnchantment> getBowEnchantments() {
        return bowEnchantments;
    }

}