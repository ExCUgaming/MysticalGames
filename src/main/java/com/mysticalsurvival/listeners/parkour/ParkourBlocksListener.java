package com.mysticalsurvival.listeners.parkour;

import com.mysticalsurvival.core.parkour.Checkpoint;
import com.mysticalsurvival.core.parkour.Parkour;
import com.mysticalsurvival.core.parkour.ParkourMap;
import com.mysticalsurvival.util.MessagesConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Objects;

public class ParkourBlocksListener implements Listener {

    @EventHandler
    public void playerBreakPlate(BlockBreakEvent e) {
        if (e.getPlayer().hasPermission("ms.admin")) {
            if (e.getBlock().getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
                for (ParkourMap pm : Parkour.getAllParkourMapInstances().values()) {


                    if (pm.getStartLocation() != null && pm.getEndLocation() != null) {


                        if (pm.getStartLocation().equals(e.getBlock().getLocation())) {
                            pm.setStartLocation(null);
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.plate-start.break")))
                                    .replaceAll("<player>", e.getPlayer().getName())
                                    .replaceAll("<playerdisplay>", e.getPlayer().getDisplayName())
                                    .replaceAll("<map>", pm.getName()));
                            return;
                        } else if (pm.getEndLocation().equals(e.getBlock().getLocation())) {
                            pm.setEndLocation(null);
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.plate-finish.break")))
                                    .replaceAll("<player>", e.getPlayer().getName())
                                    .replaceAll("<playerdisplay>", e.getPlayer().getDisplayName())
                                    .replaceAll("<map>", pm.getName()));
                            return;
                        }
                    }
                }
            } else if (e.getBlock().getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) {
                for (ParkourMap pm : Parkour.getAllParkourMapInstances().values()) {
                    if (pm.getCheckpoints() != null) {
                        for (Checkpoint ck : pm.getCheckpoints()) {
                            if (ck.getLocation().equals(e.getBlock().getLocation())) {
                                pm.deleteCheckpoint(ck.getId());
                                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.plate-checkpoint.break")))
                                        .replaceAll("<player>", e.getPlayer().getName())
                                        .replaceAll("<playerdisplay>", e.getPlayer().getDisplayName())
                                        .replaceAll("<map>", pm.getName())
                                        .replaceAll("<ckid>", String.valueOf(ck.getId())));
                                return;
                            }
                        }
                    }
                }
            }
        } else {
            e.setCancelled(true);
        }
    }
}
