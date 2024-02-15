package net.maksy.mapartpatent;

import net.maksy.mapartpatent.enums.ConfigValue;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static net.maksy.mapartpatent.enums.ConfigValue.*;

public class PatentGui implements Listener {

    private final ConfigManager config = MapArtPatent.getConfigManager();

    private final Inventory inventory;
    private final Player player;
    private ItemStack mapArt;
    private double costs = 0.0;
    private boolean craftable = false;
    private boolean usable = false;

    String KEY_OWNER = "owner";
    String KEY_CRAFTABLE = "craftable";
    String KEY_USABLE = "usable";

    public PatentGui(Player player) {
        this.player = player;
        MapArtPatent.getInstance().getServer().getPluginManager().registerEvents(this, MapArtPatent.getInstance());
        inventory = Bukkit.createInventory(player, 27, config.getDisplay(ConfigValue.getPath(GUI_TITLE)));
        initialize();
    }

    public void initialize() {
        Utils.setFillerItems(inventory, Material.GRAY_STAINED_GLASS_PANE);
        inventory.setItem(10, confirmButton());
        inventory.setItem(11, insertButton());
        inventory.setItem(13, craftableButton());
        inventory.setItem(14, usableButton());
        inventory.setItem(16, exitButton());
    }

    public ItemStack confirmButton() {
        String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE4ZjZiMTMxZWY4NDdkOTE2MGU1MTZhNmY0NGJmYTkzMjU1NGQ0MGMxOGE4MTc5NmQ3NjZhNTQ4N2I5ZjcxMCJ9fX0=";
        return Utils.getSkull(texture, config.getDisplay(ConfigValue.getPath(BUTTON_CONFIRM_DISPLAY)), false, config.getLore(ConfigValue.getPath(BUTTON_CONFIRM_LORE), "<costs>", String.valueOf(costs), true));
    }

    public ItemStack insertButton() {
        return Utils.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, config.getDisplay(ConfigValue.getPath(BUTTON_INSERT_DISPLAY)), craftable, config.getLore(ConfigValue.getPath(BUTTON_INSERT_LORE), "", "", craftable));
    }

    public ItemStack craftableButton() {
        return Utils.createItem(Material.CRAFTING_TABLE, config.getDisplay(ConfigValue.getPath(BUTTON_CRAFTABILITY_DISPLAY)), craftable, config.getLore(ConfigValue.getPath(BUTTON_CRAFTABILITY_LORE), "<costs>", String.valueOf(config.getCosts(ConfigValue.getPath(BUTTON_CRAFTABILITY_COSTS))), craftable));
    }

    public ItemStack usableButton() {
        return Utils.createItem(Material.ITEM_FRAME, config.getDisplay(ConfigValue.getPath(BUTTON_USABILITY_DISPLAY)), usable, config.getLore(ConfigValue.getPath(BUTTON_USABILITY_LORE), "<costs>", String.valueOf(config.getCosts(ConfigValue.getPath(BUTTON_USABILITY_COSTS))), usable));
    }

    public ItemStack exitButton() {
        return Utils.createItem(Material.BARRIER, config.getDisplay(ConfigValue.getPath(BUTTON_EXIT_DISPLAY)), false, config.getLore(ConfigValue.getPath(BUTTON_EXIT_LORE), "", "", false));
    }

    public void updateCosts() {
        double craftCost = config.getCosts(ConfigValue.getPath(BUTTON_CRAFTABILITY_COSTS));
        double useCosts = config.getCosts(ConfigValue.getPath(BUTTON_USABILITY_COSTS));
        costs = 0.0;
        if (craftable)
            costs += craftCost;
        if (usable)
            costs += useCosts;
    }

    public void open() {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventory(InventoryClickEvent event) {
        if (event.getInventory() != inventory)
            return;

        int slot = event.getRawSlot();
        if (slot < 27)
            event.setCancelled(true);

        if (event.isShiftClick()) {
            setMap(event);
            return;
        }

        switch (slot) {
            case 10 -> {
                if (!isMapArt(mapArt)) {
                    player.sendMessage(config.getDisplay(ConfigValue.getPath(LANG_NO_MAPART)));
                    return;
                }

                double balance = MapArtPatent.getEco().getBalance(player);
                ItemStack newMapArt = mapArt.clone();
                ItemMeta meta = newMapArt.getItemMeta();
                PersistentMetaData.setNameSpace(meta, KEY_OWNER, player.getUniqueId().toString());
                if (craftable || usable) {
                    if (balance < costs) {
                        player.sendMessage(config.getDisplay(ConfigValue.getPath(LANG_NOT_ENOUGH_MONEY)));
                        return;
                    }
                    if (craftable)
                        PersistentMetaData.setNameSpace(meta, KEY_CRAFTABLE, 1);
                    if (usable)
                        PersistentMetaData.setNameSpace(meta, KEY_USABLE, 1);
                }
                newMapArt.setItemMeta(meta);

                if (!player.getInventory().addItem(config.getPatentedMapArt(player, newMapArt, craftable, usable)).isEmpty()) {
                    player.getWorld().dropItem(player.getLocation(), newMapArt);
                    player.sendMessage(config.getDisplay(ConfigValue.getPath(LANG_INVENTORY_FULL)));
                }

                if(costs > 0.0) {
                    MapArtPatent.getEco().withdrawPlayer(player, costs);
                }
                mapArt = null;
                player.sendMessage(config.getDisplay(ConfigValue.getPath(LANG_FINISH)));
                player.closeInventory();
            }
            case 11 -> setMap(event);
            case 13 -> {
                if (!isMapArt(mapArt)) {
                    player.sendMessage(config.getDisplay(ConfigValue.getPath(LANG_NO_MAPART)));
                    return;
                }

                craftable = !craftable;
                updateCosts();
                inventory.setItem(10, confirmButton());
                inventory.setItem(13, craftableButton());
            }
            case 14 -> {
                if (!isMapArt(mapArt)) {
                    player.sendMessage(config.getDisplay(ConfigValue.getPath(LANG_NO_MAPART)));
                    return;
                }

                usable = !usable;
                updateCosts();
                inventory.setItem(10, confirmButton());
                inventory.setItem(14, usableButton());
            }
            case 16 -> player.closeInventory();
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory() != inventory)
            return;
        reset();
        PatentGuiRegistry.get().unregister((Player) event.getPlayer());
    }

    public boolean isMapArt(ItemStack item) {
        if (item == null)
            return false;
        return item.getType() == Material.FILLED_MAP;
    }

    public void setMap(InventoryClickEvent event) {
        ItemStack item = event.getCursor();

        if (event.isShiftClick()) {
            item = event.getCurrentItem();
            event.setCancelled(true);
        }

        if (item == null)
            return;
        if (!isMapArt(item)) {
            player.sendMessage(config.getDisplay(ConfigValue.getPath(LANG_NO_MAPART)));
            event.setCancelled(true);
            return;
        }

        if (PersistentMetaData.hasNameSpaceString(item.getItemMeta(), KEY_OWNER)) {
            player.sendMessage(config.getDisplay(ConfigValue.getPath(LANG_ALREADY_PATENTED)));
            event.setCancelled(true);
            return;
        }

        if (mapArt == null) {
            mapArt = item.clone();
            mapArt.setAmount(1);
            inventory.setItem(11, mapArt);
            item.setAmount(item.getAmount() - 1);
        }
    }

    public void reset() {
        if (mapArt == null)
            return;
        if (!player.getInventory().addItem(mapArt).isEmpty()) {
            player.getWorld().dropItem(player.getLocation(), mapArt);
            player.sendMessage(config.getDisplay(ConfigValue.getPath(LANG_INVENTORY_FULL)));
        }
        mapArt = new ItemStack(Material.AIR);
        inventory.setItem(11, mapArt);
    }
}
