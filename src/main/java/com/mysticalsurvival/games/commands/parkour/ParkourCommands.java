package com.mysticalsurvival.games.commands.parkour;

import com.mysticalsurvival.games.effects.ParkourPlayerActionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParkourCommands implements CommandExecutor {
    ParkourPlayerActionManager playerEffectLayer = new ParkourPlayerActionManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("leaveparkour")) {
            if (sender instanceof Player) {
                playerEffectLayer.playerCancelParkourUsingCommand((Player) sender);
            }
        }
        return true;
    }
}