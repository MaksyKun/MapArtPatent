package net.maksy.mapartpatent;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ConfigManager {

    private final FileConfiguration config = MapArtPatent.getInstance().getConfig();

    public Component getDisplay(String value) {
        return Component.text(Objects.requireNonNull(config.getString(value)));
    }

    public List<Component> getLore(String value, String replacable, String replaceValue) {
        List<Component> lore = new ArrayList<>();
        for (String s : config.getStringList(value)) {
            lore.add(Component.text(s.replace(replacable, replaceValue)));
        }
        return lore;
    }

    public double getCosts(String value) {
        return config.getDouble(value);
    }
}
