package net.maksy.mapartpatent;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class PatentGuiRegistry implements Listener {
    private static PatentGuiRegistry instance;

    private final HashMap<Player, PatentGui> patentGuis = new HashMap<>();

    public static PatentGuiRegistry get() {
        return instance == null ? instance = new PatentGuiRegistry() : instance;
    }

    public PatentGui getPatentGui(Player player) {
        patentGuis.putIfAbsent(player, new PatentGui(player));
        return patentGuis.get(player);
    }

    private PatentGuiRegistry() {
        MapArtPatent.getInstance().getServer().getPluginManager().registerEvents(this, MapArtPatent.getInstance());
    }

    public void unregister(Player player) {
        patentGuis.put(player, null);
    }
}
