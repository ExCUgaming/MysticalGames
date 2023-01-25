package com.mysticalsurvival.games.commands;

import com.mysticalsurvival.games.Games;
import com.mysticalsurvival.games.util.MessagesConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class MainCommand implements TabExecutor {
    Games main = Games.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("ms.admin")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    MessagesConfig.init();
                    if (sender instanceof Player) {
                        sender.sendMessage(ChatColor.GREEN + "Successfully reloaded all configuration files!");
                        main.getLogger().log(Level.INFO, "Successfully reloaded all configuration files! (Performed by " +sender.getName()+")");
                    } else {
                        main.getLogger().log(Level.INFO, "Successfully reloaded all configuration files! (Performed by CONSOLE)");
                    }
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED+"Invalid usage of command! Usage:");
            sender.sendMessage(ChatColor.YELLOW+"/"+label+" reload");
            return true;
        } else {
            sender.sendMessage(ChatColor.DARK_RED+"You cannot run this command.");
            return true;
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("ms.admin")) {
            if (args.length == 1) {
                return StringUtil.copyPartialMatches(args[0], Arrays.asList("reload"), new ArrayList<>());
            }
        }
        return new ArrayList<>();
    }
}
