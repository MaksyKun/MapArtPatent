package net.maksy.mapartpatent;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.maksy.mapartpatent.persistence.CapturingMapRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class Utils {

    public static void setFillerItems(Inventory inventory, Material material) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, createItem(material, Component.text(" "), false, null));
        }
    }

    public static ItemStack createItem(final Material material, final Component name, final boolean glowing, final List<Component> lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        if (name != null) {
            meta.displayName(name.decoration(TextDecoration.ITALIC, false));
        }

        if (lore != null) {
            if (!lore.isEmpty()) {
                meta.lore(lore);
            }
        }

        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static byte[] captureMapImage(ItemStack mapItem) {
        try {
            if (mapItem.getType() != Material.FILLED_MAP) {
                throw new IllegalArgumentException("ItemStack is not a filled map!");
            }

            short mapId = mapItem.getDurability();
            MapView mapView = Bukkit.getMap(mapId);

            // Add the capturing renderer
            CapturingMapRenderer capturingRenderer = new CapturingMapRenderer();
            mapView.addRenderer(capturingRenderer);

            // Render the map (this usually requires the map to be viewed by a player)
            // In practice, you'll need to trigger the rendering process appropriately
            // This is a simplified example assuming the renderer gets called

            // Retrieve the image from the renderer
            BufferedImage image = capturingRenderer.getImage();

            // Convert the image to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
