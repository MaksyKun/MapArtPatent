package net.maksy.mapartpatent;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PatentGui implements Listener {

    private static PatentGui instance;
    private final Inventory inventory;
    private final Player player;
    private double costs = 0.0;
    private boolean craftable = true;
    private boolean usable = true;

    public static PatentGui get(Player player) {
        return instance == null ? instance = new PatentGui(player) : instance;
    }

    private PatentGui(Player player) {
        this.player = player;
        MapArtPatent.getInstance().getServer().getPluginManager().registerEvents(this, MapArtPatent.getInstance());
        inventory = Bukkit.createInventory(player, 27, Component.text("MapArtPatent"));
        initialize();
    }

    public void initialize() {
        Utils.setFillerItems(inventory, Material.GRAY_STAINED_GLASS_PANE);
        inventory.setItem(10, confirmButton(!craftable || !usable));
    }

    public ItemStack confirmButton(boolean allowed) {
        String texture = allowed ?
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE4ZjZiMTMxZWY4NDdkOTE2MGU1MTZhNmY0NGJmYTkzMjU1NGQ0MGMxOGE4MTc5NmQ3NjZhNTQ4N2I5ZjcxMCJ9fX0="
                :
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19";
        return Utils.getSkull(texture, Component.text("Test"), allowed, null);
    }
}
