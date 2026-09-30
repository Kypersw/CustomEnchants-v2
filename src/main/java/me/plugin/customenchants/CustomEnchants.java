package me.plugin.customenchants;

import me.plugin.customenchants.commands.EnchantCommand;
import me.plugin.customenchants.listeners.EnchantTableListener;
import me.plugin.customenchants.listeners.EnchantEffectListener;
import me.plugin.customenchants.managers.EnchantManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomEnchants extends JavaPlugin {

    private static CustomEnchants instance;
    private EnchantManager enchantManager;

    @Override
    public void onEnable() {
        instance = this;

        // Dosya Yapılandırması
        saveDefaultConfig();

        // Yöneticileri Başlatma
        enchantManager = new EnchantManager();
        enchantManager.loadEnchants();

        // Dinleyicileri Kaydetme
        getServer().getPluginManager().registerEvents(new EnchantTableListener(this), this);
        getServer().getPluginManager().registerEvents(new EnchantEffectListener(this), this);

        // Komutları Kaydetme
        getCommand("enchantgui").setExecutor(new EnchantCommand(this));

        getLogger().info("CustomEnchants basariyla yuklendi! (60 Buyu Aktif)");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomEnchants devre disi birakildi.");
    }

    public static CustomEnchants getInstance() {
        return instance;
    }

    public EnchantManager getEnchantManager() {
        return enchantManager;
    }
}