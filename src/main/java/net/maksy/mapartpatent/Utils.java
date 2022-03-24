package net.maksy.mapartpatent;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class Utils {

    private static final Field craftMetaSkullProfile = getSkullProfileField();

    private static Field getSkullProfileField() {
        try {
            Class<?> craftMetaSkullClass = Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".inventory.CraftMetaSkull");
            return craftMetaSkullClass.getDeclaredField("profile");
        } catch (Exception e) {
            throw new RuntimeException("RIP [Skull]", e);
        }
    }

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

    public static ItemStack getSkull(String texture, Component name, boolean glowing, List<Component> lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap properties = profile.getProperties();
        Property property = new Property("textures", texture);
        properties.put("textures", property);
        try {
            craftMetaSkullProfile.setAccessible(true);
            craftMetaSkullProfile.set(itemMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (name != null) {
            itemMeta.displayName(name);
        }

        if (lore != null) {
            if (!lore.isEmpty()) {
                itemMeta.lore(lore);
            }
        }

        if (glowing) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(itemMeta);
        return item;
    }
}
