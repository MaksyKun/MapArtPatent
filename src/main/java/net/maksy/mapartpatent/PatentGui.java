package net.maksy.mapartpatent;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static net.maksy.mapartpatent.ConfigValue.*;

public class PatentGui implements Listener {

    private final ConfigManager config = MapArtPatent.getConfigManager();

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
        inventory.setItem(10, confirmButton());
        inventory.setItem(11, new ItemStack(Material.AIR));
        inventory.setItem(13, craftableButton());
        inventory.setItem(14, usableButton());
        inventory.setItem(16, exitButton());
    }

    public ItemStack confirmButton() {
        boolean allowed = (!craftable || !usable);
        String texture = allowed ?
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE4ZjZiMTMxZWY4NDdkOTE2MGU1MTZhNmY0NGJmYTkzMjU1NGQ0MGMxOGE4MTc5NmQ3NjZhNTQ4N2I5ZjcxMCJ9fX0="
                :
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19";
        return Utils.getSkull(texture, config.getDisplay(ConfigValue.getPath(BUTTON_CONFIRM_DISPLAY)), allowed, config.getLore(ConfigValue.getPath(BUTTON_CONFIRM_LORE), "<costs>", String.valueOf(costs)));
    }

    public ItemStack craftableButton() {
        return Utils.createItem(Material.CRAFTING_TABLE, config.getDisplay(ConfigValue.getPath(BUTTON_CRAFTABILITY_DISPLAY)), craftable, config.getLore(ConfigValue.getPath(BUTTON_CRAFTABILITY_LORE), "<costs>", String.valueOf(config.getCosts(ConfigValue.getPath(BUTTON_CRAFTABILITY_COSTS)))));
    }

    public ItemStack usableButton() {
        return Utils.createItem(Material.ITEM_FRAME, config.getDisplay(ConfigValue.getPath(BUTTON_USABILITY_DISPLAY)), usable, config.getLore(ConfigValue.getPath(BUTTON_USABILITY_LORE), "<costs>", String.valueOf(config.getCosts(ConfigValue.getPath(BUTTON_USABILITY_COSTS)))));
    }

    public ItemStack exitButton() {
        return Utils.createItem(Material.BARRIER, config.getDisplay(ConfigValue.getPath(BUTTON_EXIT_DISPLAY)), false, config.getLore(ConfigValue.getPath(BUTTON_EXIT_LORE), "", ""));
    }

    public void updateCosts() {
        double craftCost = config.getCosts(ConfigValue.getPath(BUTTON_CRAFTABILITY_COSTS));
        double useCosts = config.getCosts(ConfigValue.getPath(BUTTON_USABILITY_COSTS));
        costs = 0.0;
        if(!craftable)
            costs += craftCost;
        if(!usable)
            costs += useCosts;
    }

    public void open() {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventory(InventoryClickEvent event) {
        if(event.getInventory() != inventory)
            return;

        event.setCancelled(true);
        int slot = event.getSlot();

        switch (slot) {
            case 10 -> {}
            case 11 -> {}
            case 13 -> {
                craftable = !craftable;
                updateCosts();
                inventory.setItem(10, confirmButton());
                inventory.setItem(13, craftableButton());
            }
            case 14 -> {
                usable = !usable;
                updateCosts();
                inventory.setItem(10, confirmButton());
                inventory.setItem(14, usableButton());
            }
            case 16 -> {
                player.closeInventory();
                //remove GUI
            }
        }
    }
}
