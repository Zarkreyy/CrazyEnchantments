package me.badbones69.crazyenchantments.enchantments;

import me.badbones69.crazyenchantments.Methods;
import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import me.badbones69.crazyenchantments.api.FileManager;
import me.badbones69.crazyenchantments.api.enums.ArmorType;
import me.badbones69.crazyenchantments.api.enums.CEnchantments;
import me.badbones69.crazyenchantments.api.events.ArmorEquipEvent;
import me.badbones69.crazyenchantments.api.events.AuraActiveEvent;
import me.badbones69.crazyenchantments.api.events.EnchantmentUseEvent;
import me.badbones69.crazyenchantments.api.objects.ArmorEnchantment;
import me.badbones69.crazyenchantments.api.objects.PotionEffects;
import me.badbones69.crazyenchantments.controllers.ProtectionCrystal;
import me.badbones69.crazyenchantments.multisupport.Support;
import me.badbones69.crazyenchantments.multisupport.Version;
import me.badbones69.crazyenchantments.multisupport.anticheats.NoCheatPlusSupport;
import me.badbones69.crazyenchantments.multisupport.anticheats.SpartanSupport;
import me.badbones69.crazyenchantments.multisupport.particles.ParticleEffect;
import me.badbones69.crazyenchantments.processors.ArmorMoveProcessor;
import me.badbones69.crazyenchantments.processors.Processor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.Map.Entry;

public class Armor implements Listener {

    private final List<Player> fall = new ArrayList<>();
    private final HashMap<Player, HashMap<CEnchantments, Calendar>> timer = new HashMap<>();
    private final CrazyEnchantments ce = CrazyEnchantments.getInstance();
    private final Support support = Support.getInstance();
    private final Processor<PlayerMoveEvent> armorMoveProcessor = new ArmorMoveProcessor();

    public Armor() {
        armorMoveProcessor.start();
    }

    public void stop() {
        armorMoveProcessor.stop();
    }

    @EventHandler
    public void onEquip(ArmorEquipEvent e) {
        Player player = e.getPlayer();
        ItemStack newItem = e.getNewArmorPiece();
        ItemStack oldItem = e.getOldArmorPiece();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ce.hasEnchantments(oldItem)) {// Removing the potion effects.
                    for (CEnchantments enchantment : ce.getEnchantmentPotions().keySet()) {
                        if (enchantment.isActivated() && ce.hasEnchantment(oldItem, enchantment.getEnchantment())) {
                            Map<PotionEffectType, Integer> effects = ce.getUpdatedEffects(player, new ItemStack(Material.AIR), oldItem, enchantment);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (Entry<PotionEffectType, Integer> type : effects.entrySet()) {
                                        if (type.getValue() < 0) {
                                            player.removePotionEffect(type.getKey());
                                        } else {
                                            player.removePotionEffect(type.getKey());
                                            player.addPotionEffect(new PotionEffect(type.getKey(), Integer.MAX_VALUE, type.getValue()));
                                        }
                                    }
                                }
                            }.runTask(ce.getPlugin());
                        }
                    }
                }
                if (ce.hasEnchantments(newItem)) {// Adding the potion effects.
                    for (CEnchantments enchantment : ce.getEnchantmentPotions().keySet()) {
                        if (enchantment.isActivated() && ce.hasEnchantment(newItem, enchantment.getEnchantment())) {
                            Map<PotionEffectType, Integer> effects = ce.getUpdatedEffects(player, newItem, oldItem, enchantment);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    EnchantmentUseEvent event = new EnchantmentUseEvent(player, enchantment.getEnchantment(), newItem);
                                    Bukkit.getPluginManager().callEvent(event);
                                    if (!event.isCancelled()) {
                                        for (Entry<PotionEffectType, Integer> type : effects.entrySet()) {
                                            if (type.getValue() < 0) {
                                                player.removePotionEffect(type.getKey());
                                            } else {
                                                player.removePotionEffect(type.getKey());
                                                player.addPotionEffect(new PotionEffect(type.getKey(), Integer.MAX_VALUE, type.getValue()));
                                            }
                                        }
                                    }
                                }
                            }.runTask(ce.getPlugin());
                        }
                    }
                }
            }
        }.runTaskAsynchronously(ce.getPlugin());
    }

    @EventHandler
    public void onArmorRightClickEquip(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR)) return;

        Player player = event.getPlayer();

        ItemStack itemStack = Version.isLegacy() ? player.getItemInHand() : player.getInventory().getItemInMainHand();

        //String name = itemStack.getType().name();

        //if (!name.endsWith("_BOOTS") || !name.endsWith("_HELMET") || !name.endsWith("_CHESTPLATE") || !name.endsWith("_LEGGINGS")) return;

        runEquip(player, ArmorType.matchType(itemStack), new ItemStack(Material.AIR), itemStack);
    }

    private void runEquip(Player player, ArmorType armorType, ItemStack itemStack, ItemStack activeItem) {
        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.HOTBAR, armorType, itemStack, activeItem);
        player.getServer().getPluginManager().callEvent(armorEquipEvent);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (e.isCancelled() || ce.isIgnoredEvent(e) || ce.isIgnoredUUID(e.getDamager().getUniqueId())) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (support.isFriendly(e.getDamager(), e.getEntity())) return;
                if (e.getDamager() instanceof LivingEntity && e.getEntity() instanceof Player) {
                    final Player player = (Player) e.getEntity();
                    final LivingEntity damager = (LivingEntity) e.getDamager();
                    for (ItemStack armor : player.getEquipment().getArmorContents()) {
                        if (ce.hasEnchantments(armor)) {
                            for (ArmorEnchantment armorEnchantment : ce.getArmorManager().getArmorEnchantments()) {
                                CEnchantments enchantment = armorEnchantment.getEnchantment();
                                if (ce.hasEnchantment(armor, enchantment) && enchantment.chanceSuccessful(armor)) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            EnchantmentUseEvent event = new EnchantmentUseEvent(player, enchantment, armor);
                                            Bukkit.getPluginManager().callEvent(event);
                                            if (!event.isCancelled()) {
                                                if (armorEnchantment.isPotionEnchantment()) {
                                                    for (PotionEffects effect : armorEnchantment.getPotionEffects()) {
                                                        //Debug for the enchantment info.
//                                                        System.out.println(
//                                                        "===========================" + "\n"
//                                                        + "Enchantment: " + enchantment.getName() + "\n"
//                                                        + "Level: " + ce.getLevel(armor, enchantment) + "\n"
//                                                        + "Effect: " + effect.getPotionEffect().getName() + "\n"
//                                                        + "Duration: " + (effect.getDuration() / 20) + "s \n"
//                                                        + "Amplifier: " + effect.getAmplifier() + "\n"
//                                                        + "Amp + Lvl: " + ((armorEnchantment.isLevelAddedToAmplifier() ? ce.getLevel(armor, enchantment) : 0) + effect.getAmplifier()) + "\n" +
//                                                        "===========================");
                                                        damager.addPotionEffect(new PotionEffect(effect.getPotionEffect(), effect.getDuration(), (armorEnchantment.isLevelAddedToAmplifier() ? ce.getLevel(armor, enchantment) : 0) + effect.getAmplifier()));
                                                    }
                                                } else {
                                                    e.setDamage(e.getDamage() * ((armorEnchantment.isLevelAddedToAmplifier() ? ce.getLevel(armor, enchantment) : 0) + armorEnchantment.getDamageAmplifier()));
                                                }
                                            }
                                        }
                                    }.runTask(ce.getPlugin());
                                }
                            }
                            if (player.getHealth() <= 8 && ce.hasEnchantment(armor, CEnchantments.ROCKET.getEnchantment()) && CEnchantments.ROCKET.chanceSuccessful(armor)) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.ROCKET.getEnchantment(), armor);
                                        Bukkit.getPluginManager().callEvent(event);

                                        if (!event.isCancelled()) {
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    player.setVelocity(player.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize().setY(1));
                                                }
                                            }.runTaskLater(ce.getPlugin(), 1);
                                            fall.add(player);
                                            if (Version.isNewer(Version.v1_8_R3)) {
                                                player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 1);
                                            } else {
                                                ParticleEffect.EXPLOSION_HUGE.display(0, 0, 0, 1, 1, player.getLocation(), 100);
                                            }
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    fall.remove(player);
                                                }
                                            }.runTaskLater(ce.getPlugin(), 8 * 20);
                                        }
                                    }
                                }.runTask(ce.getPlugin());
                            }
                            if (ce.hasEnchantment(armor, CEnchantments.ENLIGHTENED) && CEnchantments.ENLIGHTENED.chanceSuccessful(armor) && player.getHealth() > 0) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.ENLIGHTENED.getEnchantment(), armor);
                                        Bukkit.getPluginManager().callEvent(event);
                                        if (!event.isCancelled()) {
                                            double heal = ce.getLevel(armor, CEnchantments.ENLIGHTENED);
                                            //Uses getValue as if the player has health boost it is modifying the base so the value after the modifier is needed.
                                            double maxHealth = ce.useHealthAttributes() ? player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() : player.getMaxHealth();
                                            if (player.getHealth() + heal < maxHealth) {
                                                player.setHealth(player.getHealth() + heal);
                                            }
                                            if (player.getHealth() + heal >= maxHealth) {
                                                player.setHealth(maxHealth);
                                            }
                                        }
                                    }
                                }.runTask(ce.getPlugin());
                            }
                            if (ce.hasEnchantment(armor, CEnchantments.MOLTEN) && CEnchantments.MOLTEN.chanceSuccessful(armor)) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.MOLTEN.getEnchantment(), armor);
                                        Bukkit.getPluginManager().callEvent(event);
                                        if (!event.isCancelled()) {
                                            damager.setFireTicks((ce.getLevel(armor, CEnchantments.MOLTEN) * 2) * 20);
                                        }
                                    }
                                }.runTask(ce.getPlugin());
                            }
                            if (ce.hasEnchantment(armor, CEnchantments.SAVIOR) && CEnchantments.SAVIOR.chanceSuccessful(armor)) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.SAVIOR.getEnchantment(), armor);
                                        Bukkit.getPluginManager().callEvent(event);
                                        if (!event.isCancelled()) {
                                            e.setDamage(e.getDamage() / 2);
                                        }
                                    }
                                }.runTask(ce.getPlugin());
                            }
                            if (ce.hasEnchantment(armor, CEnchantments.CACTUS) && CEnchantments.CACTUS.chanceSuccessful(armor)) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.CACTUS.getEnchantment(), armor);
                                        Bukkit.getPluginManager().callEvent(event);
                                        if (!event.isCancelled()) {
                                            damager.damage(ce.getLevel(armor, CEnchantments.CACTUS));
                                        }
                                    }
                                }.runTask(ce.getPlugin());
                            }
                            if (ce.hasEnchantment(armor, CEnchantments.STORMCALLER) && CEnchantments.STORMCALLER.chanceSuccessful(armor)) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.STORMCALLER.getEnchantment(), armor);
                                        Bukkit.getPluginManager().callEvent(event);
                                        if (!event.isCancelled()) {
                                            Location loc = damager.getLocation();
                                            loc.getWorld().spigot().strikeLightningEffect(loc, true);
                                            int lightningSoundRange = FileManager.Files.CONFIG.getFile().getInt("Settings.EnchantmentOptions.Lightning-Sound-Range", 160);
                                            try {
                                                loc.getWorld().playSound(loc, ce.getSound("ENTITY_LIGHTNING_BOLT_IMPACT", "ENTITY_LIGHTNING_IMPACT"), (float) lightningSoundRange / 16f, 1);
                                            } catch (Exception ignore) {
                                            }
                                            if (Support.SupportedPlugins.NO_CHEAT_PLUS.isPluginLoaded()) {
                                                NoCheatPlusSupport.exemptPlayer(player);
                                            }
                                            if (Support.SupportedPlugins.SPARTAN.isPluginLoaded()) {
                                                SpartanSupport.cancelNoSwing(player);
                                            }
                                            for (LivingEntity en : Methods.getNearbyLivingEntities(loc, 2D, player)) {
                                                EntityDamageByEntityEvent damageByEntityEvent = new EntityDamageByEntityEvent(player, en, DamageCause.CUSTOM, 5D);
                                                ce.addIgnoredEvent(damageByEntityEvent);
                                                ce.addIgnoredUUID(player.getUniqueId());
                                                Bukkit.getPluginManager().callEvent(damageByEntityEvent);
                                                if (!damageByEntityEvent.isCancelled() && support.allowsPVP(en.getLocation()) && !support.isFriendly(player, en)) {
                                                    en.damage(5D);
                                                }
                                                ce.removeIgnoredEvent(damageByEntityEvent);
                                                ce.removeIgnoredUUID(player.getUniqueId());
                                            }
                                            damager.damage(5D);
                                            if (Support.SupportedPlugins.NO_CHEAT_PLUS.isPluginLoaded()) {
                                                NoCheatPlusSupport.unexemptPlayer(player);
                                            }
                                        }
                                    }
                                }.runTask(ce.getPlugin());
                            }
                        }
                    }
                    if (damager instanceof Player) {
                        for (ItemStack armor : Objects.requireNonNull(damager.getEquipment()).getArmorContents()) {
                            if (ce.hasEnchantment(armor, CEnchantments.LEADERSHIP) && CEnchantments.LEADERSHIP.chanceSuccessful(armor) || Support.SupportedPlugins.FACTIONS_UUID.isPluginLoaded()) {
                                int radius = 4 + ce.getLevel(armor, CEnchantments.LEADERSHIP);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        int players = 0;
                                        for (Entity entity : damager.getNearbyEntities(radius, radius, radius)) {
                                            if (entity instanceof Player) {
                                                Player other = (Player) entity;
                                                if (support.isFriendly(damager, other)) {
                                                    players++;
                                                }
                                            }
                                        }
                                        if (players > 0) {
                                            EnchantmentUseEvent event = new EnchantmentUseEvent((Player) damager, CEnchantments.LEADERSHIP.getEnchantment(), armor);
                                            Bukkit.getPluginManager().callEvent(event);
                                            if (!event.isCancelled()) {
                                                e.setDamage(e.getDamage() + (players / 2));
                                            }
                                        }
                                    }
                                }.runTask(ce.getPlugin());
                            }
                        }
                    }
                }
            }
        }.runTaskAsynchronously(ce.getPlugin());
    }

    @EventHandler
    public void onAura(AuraActiveEvent e) {
        Player player = e.getPlayer();
        Player other = e.getOther();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.canSee(other) || !other.canSee(player)) return;
                if (support.isVanished(player) || support.isVanished(other)) return;
                CEnchantments enchant = e.getEnchantment();
                int level = e.getLevel();
                //Debug code for checking.
//                System.out.println("PvP: " + support.allowsPVP(other.getLocation()));
//                System.out.println("Enemy: " + !support.isFriendly(player, other));
//                System.out.println("NoBypass: " + !Methods.hasPermission(other, "bypass.aura", false));
                if (support.allowsPVP(other.getLocation()) && !support.isFriendly(player, other) && !Methods.hasPermission(other, "bypass.aura", false)) {
                    Calendar cal = Calendar.getInstance();
                    HashMap<CEnchantments, Calendar> effect = new HashMap<>();
                    if (timer.containsKey(other)) {
                        effect = timer.get(other);
                    }
                    HashMap<CEnchantments, Calendar> finalEffect = effect;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            switch (enchant) {
                                case BLIZZARD:
                                    if (CEnchantments.BLIZZARD.isActivated()) {
                                        other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, level - 1));
                                    }
                                    break;
                                case INTIMIDATE:
                                    if (CEnchantments.INTIMIDATE.isActivated()) {
                                        other.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 3 * 20, level - 1));
                                    }
                                    break;
                                case ACIDRAIN:
                                    if (CEnchantments.ACIDRAIN.isActivated() && (!timer.containsKey(other) ||
                                            (timer.containsKey(other) && !timer.get(other).containsKey(enchant)) ||
                                            (timer.containsKey(other) && timer.get(other).containsKey(enchant) &&
                                                    cal.after(timer.get(other).get(enchant))
                                                    && CEnchantments.ACIDRAIN.chanceSuccessful()))) {
                                        other.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4 * 20, 1));
                                        int time = 35 - (level * 5);
                                        cal.add(Calendar.SECOND, time > 0 ? time : 5);
                                        finalEffect.put(enchant, cal);
                                    }
                                    break;
                                case SANDSTORM:
                                    if (CEnchantments.SANDSTORM.isActivated() && (!timer.containsKey(other) ||
                                            (timer.containsKey(other) && !timer.get(other).containsKey(enchant)) ||
                                            (timer.containsKey(other) && timer.get(other).containsKey(enchant) &&
                                                    cal.after(timer.get(other).get(enchant))
                                                    && CEnchantments.SANDSTORM.chanceSuccessful()))) {
                                        other.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 0));
                                        int time = 35 - (level * 5);
                                        cal.add(Calendar.SECOND, time > 0 ? time : 5);
                                        finalEffect.put(enchant, cal);
                                    }
                                    break;
                                case RADIANT:
                                    if (CEnchantments.RADIANT.isActivated() && (!timer.containsKey(other) ||
                                            (timer.containsKey(other) && !timer.get(other).containsKey(enchant)) ||
                                            (timer.containsKey(other) && timer.get(other).containsKey(enchant) &&
                                                    cal.after(timer.get(other).get(enchant))
                                                    && CEnchantments.RADIANT.chanceSuccessful()))) {
                                        other.setFireTicks(5 * 20);
                                        int time = 20 - (level * 5);
                                        cal.add(Calendar.SECOND, Math.max(time, 0));
                                        finalEffect.put(enchant, cal);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }.runTask(ce.getPlugin());
                    timer.put(other, effect);
                }
            }
        }.runTaskAsynchronously(ce.getPlugin());
    }

    @SuppressWarnings({"squid:CallToDeprecatedMethod"})
    @EventHandler
    public void onMovement(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        if (Objects.requireNonNull(to).getBlockX() == from.getBlockX() && to.getBlockY() == from.getBlockY() && to.getBlockZ() == from.getBlockZ())
            return;

        armorMoveProcessor.add(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getKiller() == null) return;
                Player killer = player.getKiller();
                if (!support.allowsPVP(player.getLocation())) return;
                if (CEnchantments.SELFDESTRUCT.isActivated()) {
                    for (ItemStack item : Objects.requireNonNull(player.getEquipment()).getArmorContents()) {
                        if (ce.hasEnchantments(item) && ce.hasEnchantment(item, CEnchantments.SELFDESTRUCT.getEnchantment())) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.SELFDESTRUCT.getEnchantment(), item);
                                    Bukkit.getPluginManager().callEvent(event);
                                    if (!event.isCancelled()) {
                                        Methods.explode(player);
                                        List<ItemStack> items = new ArrayList<>();
                                        for (ItemStack drop : e.getDrops()) {
                                            if (drop != null && ProtectionCrystal.isProtected(drop) && ProtectionCrystal.isProtectionSuccessful(player)) {
                                                items.add(drop);
                                            }
                                        }
                                        e.getDrops().clear();
                                        e.getDrops().addAll(items);
                                    }
                                }
                            }.runTask(ce.getPlugin());
                        }
                    }
                }
                if (CEnchantments.RECOVER.isActivated()) {
                    for (ItemStack item : Objects.requireNonNull(killer.getEquipment()).getArmorContents()) {
                        if (ce.hasEnchantments(item) && ce.hasEnchantment(item, CEnchantments.RECOVER)) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.RECOVER.getEnchantment(), item);
                                    Bukkit.getPluginManager().callEvent(event);
                                    if (!event.isCancelled()) {
                                        killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 8 * 20, 2));
                                        killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 1));
                                    }
                                }
                            }.runTask(ce.getPlugin());
                        }
                    }
                }
            }
        }.runTaskAsynchronously(ce.getPlugin());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerFallDamage(EntityDamageEvent e) {
        if (fall.contains(e.getEntity()) && e.getCause() == DamageCause.FALL) {
            e.setCancelled(true);
        }
    }

}