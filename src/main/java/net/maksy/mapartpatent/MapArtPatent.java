package net.maksy.mapartpatent;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static org.spigotmc.SpigotConfig.config;

public final class MapArtPatent extends JavaPlugin {

    private static JavaPlugin instance;
    private static ConfigManager configManager;
    private static Economy eco;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
        configManager.init();
        setupEconomy();
        Objects.requireNonNull(getCommand("mapart")).setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new MapListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economy != null)
                eco = economy.getProvider();
        }
        return (eco != null);
    }

    public static JavaPlugin getInstance() { return instance; }

    public static ConfigManager getConfigManager() { return configManager; }

    public static Economy getEco() { return eco; }
}
