package net.maksy.mapartpatent;

import org.bukkit.plugin.java.JavaPlugin;

public final class MapArtPatent extends JavaPlugin {

    private static JavaPlugin instance;
    private static ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getInstance() { return instance; }

    public static ConfigManager getConfigManager() { return configManager; }
}
