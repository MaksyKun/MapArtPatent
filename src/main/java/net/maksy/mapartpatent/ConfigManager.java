package net.maksy.mapartpatent;

import net.kyori.adventure.text.Component;
import net.maksy.mapartpatent.enums.ConfigValue;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.maksy.mapartpatent.enums.ConfigValue.*;


public class ConfigManager {

    private final JavaPlugin plugin = MapArtPatent.getInstance();
    private FileConfiguration config = plugin.getConfig();

    public void init() {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveDefaultConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public Component getDisplay(String value) {
        return Component.text(Objects.requireNonNull(Objects.requireNonNull(config.getString(value)).replace("&", "§")));
    }

    public List<Component> getLore(String value, String replacable, String replaceValue, boolean state) {
        String stateMessage = state ? config.getString(ConfigValue.getPath(GUI_STATE_ACTIVE)) : config.getString(ConfigValue.getPath(GUI_STATE_UNACTIVE));
        List<Component> lore = new ArrayList<>();
        for (String s : config.getStringList(value)) {
            lore.add(Component.text(s.replace(replacable, replaceValue).replace("<state>", stateMessage).replace("&", "§")));
        }
        return lore;
    }

    public ItemStack getPatentedMapArt(Player player, ItemStack mapArt, boolean craftable, boolean usable) {
        String craftState = !craftable ? config.getString(ConfigValue.getPath(GUI_STATE_ACTIVE)) : config.getString(ConfigValue.getPath(GUI_STATE_UNACTIVE));
        String useState = !usable ? config.getString(ConfigValue.getPath(GUI_STATE_ACTIVE)) : config.getString(ConfigValue.getPath(GUI_STATE_UNACTIVE));
        List<Component> lore = new ArrayList<>();
        for (String s : config.getStringList(ConfigValue.getPath(MAPART_LORE))) {
            lore.add(Component.text(s.replace("<player>", player.getName()).replace("<craft_state>", craftState).replace("<use_state>", useState).replace("&", "§")));
        }
        final ItemMeta meta = mapArt.getItemMeta();
        meta.lore(lore);
        mapArt.setItemMeta(meta);
        return mapArt;
    }

    public double getCosts(String value) {
        return config.getDouble(value);
    }
}
