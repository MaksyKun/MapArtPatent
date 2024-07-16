package net.maksy.mapartpatent;

import io.papermc.paper.event.player.PlayerItemFrameChangeEvent;
import net.maksy.mapartpatent.enums.ConfigValue;
import net.maksy.mapartpatent.persistence.ImageMapRenderer;
import net.maksy.mapartpatent.persistence.PersistentMetaData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.UUID;

import static net.maksy.mapartpatent.enums.ConfigValue.LANG_NOT_ALLOWED;

public class MapListener implements Listener {

    String KEY_OWNER = "owner";
    String KEY_CRAFTABLE = "craftable";
    String KEY_USABLE = "usable";
    String KEY_MAP_DATA = "mapview";

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        ItemStack item = event.getCurrentItem();

        if (item == null)
            return;

        ItemMeta meta = item.getItemMeta();

        if (item.getType() != Material.FILLED_MAP || !PersistentMetaData.hasNameSpaceString(item.getItemMeta(), KEY_OWNER))
            return;
        if (PersistentMetaData.getNameSpaceInt(item.getItemMeta(), KEY_CRAFTABLE) == 1
                && !event.getWhoClicked().getUniqueId().equals(UUID.fromString(Objects.requireNonNull(PersistentMetaData.getNameSpaceString(meta, KEY_OWNER))))) {
            event.getWhoClicked().sendMessage(MapArtPatent.getConfigManager().getDisplay(ConfigValue.getPath(LANG_NOT_ALLOWED)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onUse(PlayerItemFrameChangeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemStack();
        ItemMeta meta = item.getItemMeta();

        if (item.getType() != Material.FILLED_MAP || !PersistentMetaData.hasNameSpaceString(item.getItemMeta(), KEY_OWNER))
            return;
        if (PersistentMetaData.getNameSpaceInt(item.getItemMeta(), KEY_USABLE) == 1
                && !player.getUniqueId().equals(UUID.fromString(Objects.requireNonNull(PersistentMetaData.getNameSpaceString(meta, KEY_OWNER))))) {
            player.sendMessage(MapArtPatent.getConfigManager().getDisplay(ConfigValue.getPath(LANG_NOT_ALLOWED)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onUse(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item == null)
            return;
        if(event.getInventory().getType() != InventoryType.CARTOGRAPHY)
            return;

        ItemMeta meta = item.getItemMeta();

        if (item.getType() != Material.FILLED_MAP || !PersistentMetaData.hasNameSpaceString(item.getItemMeta(), KEY_OWNER))
            return;
        if (PersistentMetaData.getNameSpaceInt(item.getItemMeta(), KEY_CRAFTABLE) == 1
                && !event.getWhoClicked().getUniqueId().equals(UUID.fromString(Objects.requireNonNull(PersistentMetaData.getNameSpaceString(meta, KEY_OWNER))))) {
            event.getWhoClicked().sendMessage(MapArtPatent.getConfigManager().getDisplay(ConfigValue.getPath(LANG_NOT_ALLOWED)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        if(item == null || item.getType() != Material.FILLED_MAP || !PersistentMetaData.hasNameSpaceString(item.getItemMeta(), KEY_MAP_DATA))
            return;
        try {
            BufferedImage image = PersistentMetaData.loadImageFromItem(item, new NamespacedKey(MapArtPatent.getInstance(), "mapview"));
            if (image == null) return;
            ImageMapRenderer renderer = new ImageMapRenderer(image);
            short mapId = item.getDurability();
            MapView mapView = Bukkit.getMap(mapId);

            if (mapView != null) {
                mapView.getRenderers().clear();
                mapView.addRenderer(renderer);
                player.sendMap(mapView); // Force the player to view the updated map
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
