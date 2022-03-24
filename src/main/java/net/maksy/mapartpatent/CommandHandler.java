package net.maksy.mapartpatent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.maksy.mapartpatent.enums.ConfigValue;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.maksy.mapartpatent.enums.ConfigValue.LANG_NO_PERMISSION;

public class CommandHandler implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player))
            return true;

        switch (args.length) {
            case 0 -> {
                if (!_hasPerm(player, "mapart.use"))
                    return true;
                PatentGuiRegistry.get().getPatentGui(player).open();
            }
            case 1 -> {
                if (!_hasPerm(player, "mapart.reload"))
                    return true;

                if (args[0].equals("reload")) {
                    sender.sendMessage(Component.text("Reloading config..", NamedTextColor.AQUA));
                    MapArtPatent.getConfigManager().reload();
                    sender.sendMessage(Component.text("Config reloaded!", NamedTextColor.AQUA));
                }

            }
        }
        return true;
    }

    private boolean _hasPerm(Player player, String perm) {
        if (!player.hasPermission(perm)) {
            player.sendMessage(MapArtPatent.getConfigManager().getDisplay(ConfigValue.getPath(LANG_NO_PERMISSION)));
            return false;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1)
            if("reload".startsWith(args[0]) && sender.hasPermission("mapart.reload")) return List.of("reload");
        return null;
    }
}
