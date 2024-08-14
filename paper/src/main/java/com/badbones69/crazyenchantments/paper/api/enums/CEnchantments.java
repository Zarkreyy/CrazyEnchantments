package com.badbones69.crazyenchantments.paper.api.enums;

import com.badbones69.crazyenchantments.paper.CrazyEnchantments;
import com.badbones69.crazyenchantments.paper.Methods;
import com.badbones69.crazyenchantments.paper.Starter;
import com.badbones69.crazyenchantments.paper.api.CrazyManager;
import com.badbones69.crazyenchantments.paper.api.FileManager.Files;
import com.badbones69.crazyenchantments.paper.api.objects.CEnchantment;
import com.badbones69.crazyenchantments.paper.api.objects.enchants.EnchantmentType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public enum CEnchantments {

    //	----------------Boots----------------  \\
    GEARS("Gears", "Boots", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Gives a speed boost",
            "&7when you put it on.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBoots"
    ), 3, List.of("Common, Rare, Legendary")),
    WINGS("Wings", "Boots", "&e&l%name% &7(&bI&7)", List.of(
            "&7Allows you to fly in your",
            "&7own territory.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBoots"
    ), 1, List.of("Common, Rare, Legendary")),
    ADRENALINE("Adrenaline", "Boots", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7If low on health there is a chance",
            "&7you will gain a speed boost.",
            "",
            "&6Found in:",
            "&7 - &5&l&nMythical",
            "&6Enchantable Items: &aBoots"
    ), 3, List.of("Mythical")),
    ROCKET("Rocket", "Boots", 15, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7If low on health there is a chance",
            "&7you will blast off backwards to escape.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBoots"
    ), 3, List.of("Common, Rare, Legendary")),
    SPRINGS("Springs", "Boots", "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Gives you a small",
            "&7jump boost while wearing it.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBoots"
    ), 5, List.of("Common, Rare, Legendary")),
    ANTIGRAVITY("AntiGravity", "Boots", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Gives a high jump boost",
            "&7when you put it on.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBoots"
    ), 3, List.of("Common, Rare, Legendary")),
    //	----------------Bows----------------  \\
    BOOM("Boom", "Bow", 20, 10, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to spawn",
            "&7TnT where the arrow lands.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBows"
    ), 3, List.of("Common, Rare, Legendary")),
    PULL("Pull", "Bow", 25, 10, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to send",
            "&7the enemy you hit flying at you.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBows"
    ), 3, List.of("Common, Rare, Legendary")),
    VENOM("Venom", "Bow", 10, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy poison.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBows"
    ), 5, List.of("Common, Rare, Legendary")),
    DOCTOR("Doctor", "Bow", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to",
            "&7heal a friendly",
            "player that you hit.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBows"
    ), 3, List.of("Common, Rare, Legendary")),
    PIERCING("Piercing", "Bow", 5, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to deal",
            "&7double damage to your enemy.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBows"
    ), 3, List.of("Common, Rare, Legendary")),
    ICEFREEZE("IceFreeze", "Bow", 25, 10, "&e&l%name% &7(&bI&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy slowness.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBows"
    ), 1, List.of("Common, Rare, Legendary")),
    LIGHTNING("Lightning", "Bow", 25, 10, "&e&l%name% &7(&bI&7)", List.of(
            "&7Has a chance to strike",
            "&7lightning where the arrow lands.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBows"
    ), 1, List.of("Common, Rare, Legendary")),
    MULTIARROW("MultiArrow", "Bow", 25, 10, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to shoot",
            "&7multiple arrows at once.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBows"
    ), 5, List.of("Common, Rare, Legendary")),
    STICKY_SHOT("Sticky-Shot", "Bow", 10, 10, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to spawn",
            "&7a web where the arrow lands.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBows"
    ), 3, List.of("Common, Rare, Legendary")),
    SNIPER("Sniper", "Bow", 25, 5, "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Shoot an enemy with this and",
            "&7it will have a high chance of",
            "&7doing high poison damage.",
            "",
            "&6Found in:",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBows"
    ), 2, List.of("Legendary")),
    //	----------------Helmets----------------  \\
    GLOWING("Glowing", "Helmet", "&e&l%name% &7(&bI&7)", List.of(
            "&7Gives you night vision",
            "&7when you put it on.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aHelmet"
    ), 1, List.of("Common, Rare, Legendary")),
    MERMAID("Mermaid", "Helmet", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Gives you water breathing",
            "&7when you put it on.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aHelmet"
    ), 3, List.of("Common, Rare, Legendary")),
    IMPLANTS("Implants", "Helmet", 5, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7While moving, your hunger will",
            "&7start to go up while equipped.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aHelmet"
    ), 5, List.of("Common, Rare, Legendary")),
    COMMANDER("Commander", "Helmet", "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Gives nearby faction members",
            "&7haste when wearing it.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aHelmet"
    ), 5, List.of("Common, Rare, Legendary")),
    //	----------------Swords----------------  \\
    TRAP("Trap", "Sword", 10, 5, "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy high slowness.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 2, List.of("Common, Rare, Legendary")),
    RAGE("Rage", "Sword", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Attacks will get stronger",
            "&7as long as you keep fighting.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    VIPER("Viper", "Sword", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy poison.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    SNARE("Snare", "Sword", 10, 3, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give your",
            "&7enemy slowness and mining fatigue.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    SLOWMO("SlowMo", "Sword", 5, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy slowness.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    WITHER("Wither", "Sword", 10, 3, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy high wither.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    VAMPIRE("Vampire", "Sword", 5, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to heal",
            "&7you when fighting.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 5, List.of("Common, Rare, Legendary")),
    EXECUTE("Execute", "Sword", "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to get strength",
            "&7when your enemy is low on health.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 5, List.of("Common, Rare, Legendary")),
    FASTTURN("FastTurn", "Sword", 5, 5, "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Has a chance to",
            "&7deal more damage.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 2, List.of("Common, Rare, Legendary")),
    DISARMER("Disarmer", "Sword", 5, 1, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to take a",
            "&7random armor piece off your enemy.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    HEADLESS("Headless", "Sword", 10, 10, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to drop",
            "&7your enemy''s head on death.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 5, List.of("Common, Rare, Legendary")),
    PARALYZE("Paralyze", "Sword", 15, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give your",
            "&7enemy high mining fatigue and slowness.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    BLINDNESS("Blindness", "Sword", 5, 1, "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy blindness.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 2, List.of("Common, Rare, Legendary")),
    LIFESTEAL("LifeSteal", "Sword", 15, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to steal",
            "&7health from your enemy.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 5, List.of("Common, Rare, Legendary")),
    CONFUSION("Confusion", "Sword", 15, 5, "&e&l%name% &7(&bI-&bIV&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy confusion.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 4, List.of("Common, Rare, Legendary")),
    NUTRITION("Nutrition", "Sword", 15, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to",
            "&7feed you while fighting.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    SKILLSWIPE("SkillSwipe", "Sword", 5, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to steal xp",
            "&7from your enemy while fighting.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 5, List.of("Common, Rare, Legendary")),
    OBLITERATE("Obliterate", "Sword", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to send",
            "&7your enemy flying backwards.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    INQUISITIVE("Inquisitive", "Sword", 50, 25, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to double or more XP",
            "&7based on the enchantment level.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 5, List.of("Common, Rare, Legendary")),
    LIGHTWEIGHT("LightWeight", "Sword", 15, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give",
            "&7you haste when fighting.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    DOUBLEDAMAGE("DoubleDamage", "Sword", 5, 1, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to",
            "&7deal double damage.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Common, Rare, Legendary")),
    DISORDER("Disorder", "Sword", 1, 0, "&e&l%name% &7(&bI&7)", List.of(
            "&7Has a chance to reorder",
            "&7your enemies hot-bar.",
            "",
            "&6Found in:",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aSword"
    ), 1, List.of("Legendary")),
    CHARGE("Charge", "Sword", "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7When you kill an enemy player",
            "&7nearby allies and you gain a speed boost.",
            "",
            "&6Found in:",
            "&7 - &9&l&nRare",
            "&6Enchantable Items: &aSword"
    ), 5, List.of("Rare")),
    REVENGE("Revenge", "Sword", "&e&l%name% &7(&bI&7)", List.of(
            "&7When a nearby ally is",
            "&7killed you receive speed",
            "&7regeneration, and haste.",
            "",
            "&6Found in:",
            "&7 - &9&l&nRare",
            "&6Enchantable Items: &aSword"
    ), 1, List.of("Rare")),
    FAMISHED("Famished", "Sword", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7During combat it has a",
            "&610%(+5% per level) &7chance",
            "&7to give the enemy hunger.",
            "",
            "&6Found in:",
            "&7 - &9&l&nRare",
            "&6Enchantable Items: &aSword"
    ), 3, List.of("Rare")),
    //	----------------Armor----------------  \\
    HULK("Hulk", "Armor", "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Gives strength, damage resistance,",
            "&7and slowness while wearing it.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 2, List.of("Common, Rare, Legendary")),
    VALOR("Valor", "Armor", "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Gives damage resistance",
            "&7based on your enchantment level.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 2, List.of("Common, Rare, Legendary")),
    DRUNK("Drunk", "Armor", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Gives strength, mining fatigue,",
            "&7and slowness when put on.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Common, Rare, Legendary")),
    NINJA("Ninja", "Armor", "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Gives speed and health boost",
            "&7when you put it on.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 2, List.of("Common, Rare, Legendary")),
    ANGEL("Angel", "Armor", "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Heals nearby faction members",
            "&7when you are wearing item.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 5, List.of("Common, Rare, Legendary")),
    TAMER("Tamer", "Armor", "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Spawns wolves to help",
            "&7when you are in trouble.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 5, List.of("Common, Rare, Legendary")),
    GUARDS("Guards", "Armor", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Spawns in iron golems",
            "&7when you are in trouble.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Common, Rare, Legendary")),
    VOODOO("Voodoo", "Armor", 15, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy weakness.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Common, Rare, Legendary")),
    MOLTEN("Molten", "Armor", 10, 1, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to set",
            "&7set your enemy on fire.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 5, List.of("Common, Rare, Legendary")),
    SAVIOR("Savior", "Armor", 15, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to cut",
            "&7your incoming damage by half.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 5, List.of("Common, Rare, Legendary")),
    CACTUS("Cactus", "Armor", 25, 25, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to damage",
            "&7the player that hits you.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Common, Rare, Legendary")),
    FREEZE("Freeze", "Armor", 10, 5, "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy slowness.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 2, List.of("Common, Rare, Legendary")),
    RECOVER("Recover", "Armor", "&e&l%name% &7(&bI&7)", List.of(
            "&7When you kill a player",
            "&7you get absorption and regen.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 1, List.of("Common, Rare, Legendary")),
    NURSERY("Nursery", "Armor", 5, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to",
            "&7heal you while walking.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aBoots"
    ), 5, List.of("Common, Rare, Legendary")),
    RADIANT("Radiant", "Armor", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Sets enemies within 3",
            "&7blocks on fire for a short time.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Common, Rare, Legendary")),
    FORTIFY("Fortify", "Armor", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy weakness.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Common, Rare, Legendary")),
    OVERLOAD("OverLoad", "Armor", "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Gives a health boost",
            "&7when you put it on.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 5, List.of("Common, Rare, Legendary")),
    BLIZZARD("Blizzard", "Armor", "&e&l%name% &7(&bI&7)", List.of(
            "&7Gives enemies within 3",
            "&7blocks slowness for a short time.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 1, List.of("Common, Rare, Legendary")),
    INSOMNIA("Insomnia", "Armor", 10, 5, "&e&l%name% &7(&bI&7)", List.of(
            "&7Gives confusion, mining fatigue, and slowness",
            "&7but has a high chance of doing double the damage.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 1, List.of("Common, Rare, Legendary")),
    ACIDRAIN("AcidRain", "Armor", 5, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Gives enemies within 3",
            "&7blocks poison for a short time.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Common, Rare, Legendary")),
    SANDSTORM("SandStorm", "Armor", 5, 5, "&e&l%name% &7(&bI&7)", List.of(
            "&7Gives enemies within 3",
            "&7blocks blindness for a short time.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 1, List.of("Common, Rare, Legendary")),
    SMOKEBOMB("SmokeBomb", "Armor", 5, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to blind and",
            "&7slow your enemy so you can escape.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Common, Rare, Legendary")),
    PAINGIVER("PainGiver", "Armor", 10, 5, "&e&l%name% &7(&bI-&bIV&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy poison.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 4, List.of("Common, Rare, Legendary")),
    INTIMIDATE("Intimidate", "Armor", "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Gives enemies within 3",
            "&7blocks weakness for a short time.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 2, List.of("Common, Rare, Legendary")),
    BURNSHIELD("BurnShield", "Armor", "&e&l%name% &7(&bI&7)", List.of(
            "&7Gives fire resistance",
            "&7when you put it on.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 1, List.of("Common, Rare, Legendary")),
    LEADERSHIP("Leadership", "Armor", 10, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Your attacks are stronger when",
            "&7you have allies around you.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 5, List.of("Common, Rare, Legendary")),
    INFESTATION("Infestation", "Armor", "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Spawns in endermites and silverfish",
            "&7when you are in trouble.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 2, List.of("Common, Rare, Legendary")),
    NECROMANCER("Necromancer", "Armor", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Spawns in zombies",
            "&7when you are in trouble.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Common, Rare, Legendary")),
    STORMCALLER("StormCaller", "Armor", 10, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to strike your enemy",
            "&7down with a lightning strike.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 5, List.of("Common, Rare, Legendary")),
    ENLIGHTENED("Enlightened", "Armor", 10, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to",
            "&7heal you while being attacked.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 5, List.of("Common, Rare, Legendary")),
    SELFDESTRUCT("SelfDestruct", "Armor", "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7When you die there will",
            "&7be an explosion where you died.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 5, List.of("Common, Rare, Legendary")),
    CYBORG("Cyborg", "Armor", "&e&l%name% &7(&bI&7)", List.of(
            "&7Gives you speed, strength and",
            "&7jump boost to take down your enemies.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 1, List.of("Common, Rare, Legendary")),
    BEEKEEPER("BeeKeeper", "Armor", "&e&l%name% &7(&bI-&bII&7)", List.of(
            "&7Spawns in bees",
            "&7when you are in trouble.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor"
    ), 2, List.of("Common, Rare, Legendary")),
    MANEUVER("Maneuver", "Armor", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Chance to dodge",
            "&7an attack.",
            "",
            "&6Found in:",
            "&7 - &5&l&nMythical",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Mythical")),
    CROUCH("Crouch", "Armor", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7During combat you take",
            "&610%(+5% per level) &7less",
            "&7damage while crouched.",
            "",
            "",
            "&6Found in:",
            "&7 - &5&l&nMythical",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Mythical")),
    SHOCKWAVE("Shockwave", "Armor", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Chance to knockback",
            "&7enemies when they",
            "&7attack you.",
            "",
            "&6Found in:",
            "&7 - &5&l&nMythical",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Mythical")),
    SYSTEMREBOOT("SystemReboot", "Armor", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Chance to become",
            "&7full hp when",
            "&7attacked at low hp.",
            "",
            "&6Found in:",
            "&7 - &5&l&nMythical",
            "&6Enchantable Items: &aArmor"
    ), 3, List.of("Mythical")),
    //	----------------Axes----------------  \\
    REKT("Rekt", "Axe", 5, 1, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to",
            "&7deal double the damage.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aAxe"
    ), 3, List.of("Common, Rare, Legendary")),
    DIZZY("Dizzy", "Axe", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy confusion.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aAxe"
    ), 3, List.of("Common, Rare, Legendary")),
    CURSED("Cursed", "Axe", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give",
            "&7your enemy mining fatigue.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aAxe"
    ), 3, List.of("Common, Rare, Legendary")),
    FEEDME("FeedMe", "Axe", 10, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to",
            "&7feed you while fighting.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aAxe"
    ), 5, List.of("Common, Rare, Legendary")),
    BERSERK("Berserk", "Axe", 10, 1, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give you",
            "&7strength and mining fatigue.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aAxe"
    ), 3, List.of("Common, Rare, Legendary")),
    BLESSED("Blessed", "Axe", 10, 5, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to",
            "&7remove all negative effects.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aAxe"
    ), 3, List.of("Common, Rare, Legendary")),
    DECAPITATION("Decapitation", "Axe", 10, 10, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to drop",
            "&7the player''s head on death.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aAxe"
    ), 5, List.of("Common, Rare, Legendary")),
    BATTLECRY("BattleCry", "Axe", 10, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7During combat it has a",
            "&610%(+5% per level) &7chance to send",
            "&7nearby players flying backwards.",
            "",
            "&6Found in:",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aAxe"
    ), 5, List.of("Legendary")),
    DEMONFORGED("DemonForged", "Axe", 10, 5, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7During combat it has a",
            "&610%(+5% per level) &7chance to deal",
            "&7more durability damage.",
            "",
            "&6Found in:",
            "&7 - &5&l&nMythical",
            "&6Enchantable Items: &aAxe"
    ), 5, List.of("Mythical")),
    //	----------------PickAxes----------------  \\
    VEINMINER("VeinMiner", "Pickaxe", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Breaks connected ore blocks.",
            "&7The higher the level, the more",
            "&7connected blocks it can break.",
            "",
            "&6Found in:",
            "&7 - &5&l&nMythical",
            "&6Enchantable Items: &aPickaxe"
    ), 3, List.of("Mythical")),
    BLAST("Blast", "Pickaxe", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Breaks blocks in a 3 x level x 3 radius",
            "&7depending on the power of the enchantment.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aPickaxe"
    ), 3, List.of("Common, Rare, Legendary")),
    AUTOSMELT("AutoSmelt", "Pickaxe", 25, 25, "&e&l%name% &7(&bI-&bV&7)", List.of(
            "&7Has a chance to auto smelt",
            "&7ores and give more ingots.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aPickaxe"
    ), 5, List.of("Common, Rare, Legendary")),
    EXPERIENCE("Experience", "Pickaxe", 25, 25, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Has a chance to give",
            "&72x or more xp from ores.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aPickaxe"
    ), 3, List.of("Common, Rare, Legendary")),
    FURNACE("Furnace", "Pickaxe", "&e&l%name% &7(&bI&7)", List.of(
            "&7Always smelts ores into",
            "&7items instead of blocks.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aPickaxe"
    ), 1, List.of("Common, Rare, Legendary")),
    //	----------------Tools----------------  \\
    HASTE("Haste", "Tool", "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7Gives you haste when",
            "&7holding the item in your hand.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aTools"
    ), 3, List.of("Common, Rare, Legendary")),
    TELEPATHY("Telepathy", "Tool", "&e&l%name% &7(&bI&7)", List.of(
            "&7Will automatically send",
            "&7mined blocks to your inventory.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aTools"
    ), 1, List.of("Common, Rare, Legendary")),
    OXYGENATE("Oxygenate", "Tool", "&e&l%name% &7(&bI&7)", List.of(
            "&7Gives you water breathing",
            "&7when holding item in your hand.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aTools"
    ), 1, List.of("Common, Rare, Legendary")),
    //	----------------Hoes----------------  \\
    GREENTHUMB("GreenThumb", "Hoe", 10, 10, "&e&l%name% &7(&bI-&bIII&7)", List.of(
            "&7When right clicking a seedling you",
            "&7have a &610%(+10% per level)&7 chance",
            "&7to fully grow the plant but at the cost",
            "&7of the hoe''s durability.",
            "",
            "&6Found in:",
            "&7 - &9&l&nRare",
            "&6Enchantable Items: &aHoes"
    ), 3, List.of("Rare")),
    HARVESTER("Harvester", "Hoe", "&e&l%name% &7(&bI&7)", List.of(
            "&7When breaking fully grown crops",
            "&7it will break all fully grown crops",
            "&7in a 3x3 area of the broken crop.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&6Enchantable Items: &aHoes"
    ), 1, List.of("Common")),
    TILLER("Tiller", "Hoe", "&e&l%name% &7(&bI&7)", List.of(
            "&7When tilling up soil",
            "&7it will automatically till soil",
            "&7in a 3x3 area of the tilled soil.",
            "&7It will also automatically hydrate",
            "&7the soil if it is 4 blocks away from",
            "&7a water source block.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&6Enchantable Items: &aHoes"
    ), 1, List.of("Common")),
    PLANTER("Planter", "Hoe", "&e&l%name% &7(&bI&7)", List.of(
            "&7When tilling up soil",
            "&7it will automatically plant",
            "&7a seed in your off-hand or",
            "&7in your hot-bar going from the",
            "&7right side to the left side.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&6Enchantable Items: &aHoes"
    ), 1, List.of("Common")),
    //	----------------All----------------  \\
    HELLFORGED("HellForged", "Damaged-Items", 5, 5, "&e&l%name% &7(&bI-&bIV&7)", List.of(
            "&7Has a chance to fix up",
            "&7your items as you walk.",
            "",
            "&6Found in:",
            "&7 - &a&l&nCommon",
            "&7 - &9&l&nRare",
            "&7 - &4&l&nLegendary",
            "&6Enchantable Items: &aArmor, Weapons, Tools, Fishing Rods, and Shears"
    ), 4, List.of("Common, Rare, Legendary"));

    @NotNull
    private final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    @NotNull
    private final Starter starter = this.plugin.getStarter();

    @NotNull
    private final CrazyManager crazyManager = this.starter.getCrazyManager();

    @NotNull
    private final Methods methods = this.starter.getMethods();

    private final String name;
    private final String typeName;
    
    // default values
    private final List<String> defaultDescription;
    private final String defaultName;
    private final int defaultMaxPower;
    private final List<String> defaultCategories;
    
    private final boolean hasChanceSystem;
    private final int chance;
    private final int chanceIncrease;

    private CEnchantment cachedEnchantment = null;

    public static void invalidateCachedEnchants() {
        for (CEnchantments value : values()) {
            value.cachedEnchantment = null;
        }
    }

    /**
     * @param name Name of the enchantment.
     * @param typeName Type of items it goes on.
     */
    CEnchantments(final String name, final String typeName, final String defaultName, final List<String> defaultDescription, final int defaultMaxPower, final List<String> defaultCategories) {
        this.name = name;
        this.typeName = typeName;
        
        this.defaultName = defaultName;
        this.defaultDescription = defaultDescription;
        this.defaultMaxPower = defaultMaxPower;
        this.defaultCategories = defaultCategories;
        
        this.chance = 0;
        this.chanceIncrease = 0;
        this.hasChanceSystem = false;
    }

    /**
     * @param name Name of the enchantment.
     * @param typeName Type of items it goes on.
     * @param chance The chance the enchantment has to active.
     * @param chanceIncrease The amount the chance increases by every level.
     */
    CEnchantments(final String name, final String typeName, final int chance, final int chanceIncrease, final String defaultName, final List<String> defaultDescription, final int defaultMaxPower, final List<String> defaultCategories) {
        this.name = name;
        this.typeName = typeName;

        this.defaultName = defaultName;
        this.defaultDescription = defaultDescription;
        this.defaultMaxPower = defaultMaxPower;
        this.defaultCategories = defaultCategories;
        
        this.chance = chance;
        this.chanceIncrease = chanceIncrease;
        this.hasChanceSystem = true;
    }

    public static void addMissingEnchantments() {
        boolean saveFile = false;

        FileConfiguration enchantments = Files.ENCHANTMENTS.getFile();

        for (CEnchantments enchantment : values()) {
            if (!enchantments.contains("Enchantments." + enchantment.getName())) {
                saveFile = true;

                enchantments.set("Enchantments." + enchantment.getName() + ".Enabled", false);
                
                final ConfigurationSection section = enchantments.getConfigurationSection("Enchantments." + enchantment.getName());
                
                if (section != null) {
                    final int chance = enchantment.getChance();
                    final int increase = enchantment.getChanceIncrease();

                    if (chance > 0) {
                        section.set("Chance-System.Base", chance);
                    }

                    if (increase > 1) {
                        section.set("Chance-System.Increase", increase);
                    }
                    
                    section.set("Enchantment-Type", enchantment.typeName);
                    section.set("Name", "&7" + enchantment.getName());
                    section.set("Sound", "ENTITY_PLAYER_LEVELUP");
                    section.set("MaxPower", enchantment.defaultMaxPower);
                    
                    section.set("Info.Name", enchantment.defaultName.replace("%name%", enchantment.getName()));
                    section.set("Info.Description", enchantment.defaultDescription);
                    section.set("Categories", enchantment.defaultCategories);
                }
            }
        }

        if (saveFile) {
            Files.ENCHANTMENTS.saveFile();
            Files.ENCHANTMENTS.reloadFile();
        }
    }
    
    /**
     * @return The name of the enchantment.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * @return The custom name in the Enchantment.yml.
     */
    public final String getCustomName() {
        return getEnchantment().getCustomName();
    }

    /**
     * Get the chance the enchantment will activate.
     * Not all enchantments have a chance to activate.
     *
     * @return The chance of the enchantment activating.
     */
    public final int getChance() {
        return this.chance;
    }

    /**
     * Get the amount the enchantment chance increases by every level.
     * Not all enchantments have a chance to activate.
     *
     * @return The amount the chance increases by every level.
     */
    public final int getChanceIncrease() {
        return this.chanceIncrease;
    }

    /**
     * @return The description of the enchantment in the Enchantments.yml.
     */
    public final List<String> getDescription() {
        return getEnchantment().getInfoDescription();
    }

    /**
     * @return The type the enchantment is.
     */
    public final EnchantmentType getType() {
        if (getEnchantment() == null || getEnchantment().getEnchantmentType() == null) {
            return this.methods.getFromName(this.typeName);
        } else {
            return getEnchantment().getEnchantmentType();
        }
    }

    /**
     * @return True if the enchantment is enabled and false if not.
     */
    public final boolean isActivated() {
        return getEnchantment() != null && getEnchantment().isActivated();
    }

    /**
     * Get the enchantment that this is tied to.
     * @return The enchantment this is tied to.
     */
    public final CEnchantment getEnchantment() {
        if (this.cachedEnchantment == null) this.cachedEnchantment = this.crazyManager.getEnchantmentFromName(this.name);

        return this.cachedEnchantment;
    }

    /**
     * Check to see if the enchantment's chance is successful.
     * @return True if the chance was successful and false if not.
     */
    public final boolean chanceSuccessful(final int level) {
        return this.chanceSuccessful(level, 1.0);
    }

    /**
     * Check to see if the enchantment's chance is successful.
     * @return True if the chance was successful and false if not.
     */
    public final boolean chanceSuccessful(final int level, final double multiplier) {
        return getEnchantment().chanceSuccessful(level, multiplier);
    }

    /**
     * Check if the CEnchantments uses a chance system.
     */
    public final boolean hasChanceSystem() {
        return this.hasChanceSystem;
    }
}