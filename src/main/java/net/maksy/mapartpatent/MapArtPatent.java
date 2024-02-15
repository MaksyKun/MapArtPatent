package net.maksy.mapartpatent;

import net.maksy.mapartpatent.util.Metrics;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

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

        int pluginId = 14728;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economy != null)
                eco = economy.getProvider();
        }
    }

    public static JavaPlugin getInstance() { return instance; }

    public static ConfigManager getConfigManager() { return configManager; }

    public static Economy getEco() { return eco; }
}
