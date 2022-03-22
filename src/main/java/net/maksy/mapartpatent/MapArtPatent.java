package net.maksy.mapartpatent;

import org.bukkit.plugin.java.JavaPlugin;

public final class MapArtPatent extends JavaPlugin {

    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getInstance() { return instance; }
}
