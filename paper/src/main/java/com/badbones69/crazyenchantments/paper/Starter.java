package com.badbones69.crazyenchantments.paper;

import com.badbones69.crazyenchantments.paper.api.CrazyManager;
import com.badbones69.crazyenchantments.paper.api.FileManager;
import com.badbones69.crazyenchantments.paper.api.builders.types.MenuManager;
import com.badbones69.crazyenchantments.paper.api.builders.types.blacksmith.BlackSmithManager;
import com.badbones69.crazyenchantments.paper.api.builders.types.gkitz.KitsManager;
import com.badbones69.crazyenchantments.paper.api.enums.CEnchantments;
import com.badbones69.crazyenchantments.paper.api.managers.AllyManager;
import com.badbones69.crazyenchantments.paper.api.managers.ArmorEnchantmentManager;
import com.badbones69.crazyenchantments.paper.api.managers.BowEnchantmentManager;
import com.badbones69.crazyenchantments.paper.api.managers.ShopManager;
import com.badbones69.crazyenchantments.paper.api.managers.WingsManager;
import com.badbones69.crazyenchantments.paper.api.utils.BowUtils;
import com.badbones69.crazyenchantments.paper.controllers.EnchantmentControl;
import com.badbones69.crazyenchantments.paper.controllers.settings.EnchantmentBookSettings;
import com.badbones69.crazyenchantments.paper.controllers.settings.ProtectionCrystalSettings;
import com.badbones69.crazyenchantments.paper.listeners.ScramblerListener;
import com.badbones69.crazyenchantments.paper.listeners.ScrollListener;
import com.badbones69.crazyenchantments.paper.listeners.SlotCrystalListener;
import com.badbones69.crazyenchantments.paper.tasks.support.types.items.OraxenSupport;
import com.ryderbelserion.vital.paper.VitalPaper;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Starter extends VitalPaper {

    @NotNull
    private final CrazyEnchantments plugin = JavaPlugin.getPlugin(CrazyEnchantments.class);

    private FileManager fileManager;
    private CrazyManager crazyManager;
    private Methods methods;

    // Settings.
    private ProtectionCrystalSettings protectionCrystalSettings;
    private EnchantmentBookSettings enchantmentBookSettings;

    // Plugin Utils.
    private BowUtils bowUtils;

    private OraxenSupport oraxenSupport;

    // Plugin Managers.
    private ArmorEnchantmentManager armorEnchantmentManager;
    private BowEnchantmentManager bowEnchantmentManager;
    private WingsManager wingsManager;
    private AllyManager allyManager;
    private ShopManager shopManager;

    // Listeners.
    private ScramblerListener scramblerListener;
    private ScrollListener scrollListener;
    private SlotCrystalListener slotCrystalListener;

    public Starter(JavaPlugin plugin) {
        super(plugin);
    }

    public void run() {
        this.fileManager = new FileManager();
        this.fileManager.setup();

        // Plugin Support.
        //todo() update plugin support
        //this.pluginSupport = new PluginSupport();
        //this.pluginSupport.initializeWorldGuard();

        //if (SupportedPlugins.SUPERIORSKYBLOCK.isPluginLoaded()) this.superiorSkyBlockSupport = new SuperiorSkyBlockSupport();

        //if (SupportedPlugins.ORAXEN.isPluginLoaded()) this.oraxenSupport = new OraxenSupport();

        // Methods
        this.methods = new Methods();

        // Settings.
        this.protectionCrystalSettings = new ProtectionCrystalSettings();
        this.enchantmentBookSettings = new EnchantmentBookSettings();

        BlackSmithManager.load();
        KitsManager.load();

        MenuManager.load();

        this.shopManager = new ShopManager();

        // Plugin Managers.
        this.armorEnchantmentManager = new ArmorEnchantmentManager();
        this.bowEnchantmentManager = new BowEnchantmentManager();
        this.wingsManager = new WingsManager();
        this.allyManager = new AllyManager();

        // Listeners.
        this.plugin.pluginManager.registerEvents(this.scramblerListener = new ScramblerListener(), this.plugin);
        this.plugin.pluginManager.registerEvents(this.scrollListener = new ScrollListener(), this.plugin);
        this.plugin.pluginManager.registerEvents(this.slotCrystalListener = new SlotCrystalListener(), this.plugin);

        this.crazyManager = new CrazyManager();

        // add any missing enchantments to the Enchantments.yml
        CEnchantments.addMissingEnchantments();

        // Plugin Utils.
        this.bowUtils = new BowUtils();

        this.plugin.pluginManager.registerEvents(new EnchantmentControl(), this.plugin);
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public Methods getMethods() {
        return this.methods;
    }

    public CrazyManager getCrazyManager() {
        return this.crazyManager;
    }

    // Settings.
    public ProtectionCrystalSettings getProtectionCrystalSettings() {
        return this.protectionCrystalSettings;
    }

    public EnchantmentBookSettings getEnchantmentBookSettings() {
        return this.enchantmentBookSettings;
    }

    public OraxenSupport getOraxenSupport() {
        return this.oraxenSupport;
    }

    // Plugin Managers.
    public ArmorEnchantmentManager getArmorEnchantmentManager() {
        return this.armorEnchantmentManager;
    }

    public BowEnchantmentManager getBowEnchantmentManager() {
        return this.bowEnchantmentManager;
    }

    public WingsManager getWingsManager() {
        return this.wingsManager;
    }

    public AllyManager getAllyManager() {
        return this.allyManager;
    }

    public ShopManager getShopManager() {
        return this.shopManager;
    }

    // Listeners.
    public ScramblerListener getScramblerListener() {
        return this.scramblerListener;
    }

    public ScrollListener getScrollListener() {
        return this.scrollListener;
    }

    public SlotCrystalListener getSlotCrystalListener() {
        return this.slotCrystalListener;
    }

    // Plugin Utils.
    public BowUtils getBowUtils() {
        return this.bowUtils;
    }

    @Override
    public final boolean isLegacy() {
        return true;
    }

    @Override
    public final boolean isVerbose() {
        return true;
    }
}