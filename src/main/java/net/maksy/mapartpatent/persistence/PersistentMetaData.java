package net.maksy.mapartpatent.persistence;

import net.maksy.mapartpatent.MapArtPatent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PersistentMetaData {
    private static final JavaPlugin plugin = MapArtPatent.getInstance();

    public static void setNameSpace(ItemMeta meta, String key, String stringy) {
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, stringy);
    }

    public static void setNameSpace(ItemMeta meta, String key, int inty) {
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.INTEGER, inty);
    }

    public static String getNameSpaceString(ItemMeta itemMeta, String key) {
        if(!hasNameSpaceString(itemMeta, key))
            return null;

        return itemMeta.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    public static void setNameSpace(ItemMeta meta, String key, byte[] bytes) {
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.BYTE_ARRAY, bytes);
    }

    public static byte[] getNameSpaceBytes(ItemMeta itemMeta, String key) {
        if(!hasNameSpaceBytes(itemMeta, key))
            return null;

        return itemMeta.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.BYTE_ARRAY);
    }

    public static int getNameSpaceInt(ItemMeta itemMeta, String key) {
        if(!hasNameSpaceInt(itemMeta, key))
            return 0;

        return itemMeta.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.INTEGER);
    }

    public static boolean hasNameSpaceString(ItemMeta itemMeta, String key) {
        return itemMeta.getPersistentDataContainer().has(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    public static boolean hasNameSpaceBytes(ItemMeta itemMeta, String key) {
        return itemMeta.getPersistentDataContainer().has(new NamespacedKey(plugin, key), PersistentDataType.BYTE_ARRAY);
    }

    public static boolean hasNameSpaceInt(ItemMeta itemMeta, String key) {
        return itemMeta.getPersistentDataContainer().has(new NamespacedKey(plugin, key), PersistentDataType.INTEGER);
    }

    public static BufferedImage loadImageFromItem(ItemStack item, NamespacedKey key) throws IOException {
        if (item == null || item.getType() != Material.FILLED_MAP) {
            throw new IllegalArgumentException("ItemStack is not a filled map!");
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        if (!dataContainer.has(key, PersistentDataType.BYTE_ARRAY)) return null;

        byte[] imageBytes = dataContainer.get(key, PersistentDataType.BYTE_ARRAY);
        if (imageBytes == null) {
            System.err.println("Image bytes are null!");
            return null;
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(bais);
        if (image == null) {
            System.err.println("Failed to read image from bytes!");
        }
        return image;
    }
}
