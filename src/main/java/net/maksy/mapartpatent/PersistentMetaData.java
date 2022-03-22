package net.maksy.mapartpatent;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class PersistentMetaData {
    private static final JavaPlugin plugin = MapArtPatent.getInstance();

    public static void setNameSpace(ItemMeta meta, String key, String value) {
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
    }

    public static void setNameSpace(ItemMeta meta, String key, int value) {
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.INTEGER, value);
    }

    public static String getNameSpaceString(ItemMeta meta, String key) {
        return meta.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    public static int getNameSpacePseudoBoolean(ItemMeta meta, String key) {
        return meta.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.INTEGER);
    }
}
