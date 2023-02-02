package com.mysticalsurvival.games.commands.lobby;

import com.mysticalsurvival.games.core.lobby.ParkourGUI;
import com.mysticalsurvival.games.core.parkour.Parkour;
import com.mysticalsurvival.games.util.MessagesConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LobbyCommands implements TabExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("parkours")) {
                if (Parkour.getParkourRunInstance((Player) sender) == null) {

                    new ParkourGUI((Player) sender);

                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.gui.other-parkour"))
                            .replaceAll("<player>", sender.getName())
                            .replaceAll("<playerdisplay>", ((Player) sender).getDisplayName())));
                }
            }

        }

        return true;

    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
