package net.maksy.mapartpatent;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

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

    public static int getNameSpaceInt(ItemMeta itemMeta, String key) {
        if(!hasNameSpaceInt(itemMeta, key))
            return 0;

        return itemMeta.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.INTEGER);
    }

    public static boolean hasNameSpaceString(ItemMeta itemMeta, String key) {
        return itemMeta.getPersistentDataContainer().has(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    public static boolean hasNameSpaceInt(ItemMeta itemMeta, String key) {
        return itemMeta.getPersistentDataContainer().has(new NamespacedKey(plugin, key), PersistentDataType.INTEGER);
    }
}
