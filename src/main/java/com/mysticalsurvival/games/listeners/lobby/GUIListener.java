package com.mysticalsurvival.games.listeners.lobby;

import com.mysticalsurvival.games.core.lobby.EasyParkourMapGUI;
import com.mysticalsurvival.games.core.lobby.HardParkourMapGUI;
import com.mysticalsurvival.games.core.lobby.NormalParkourMapGUI;
import com.mysticalsurvival.games.core.lobby.ParkourGUI;
import com.mysticalsurvival.games.core.parkour.Parkour;
import com.mysticalsurvival.games.util.MessagesConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getInventory() != null && e.getCurrentItem() != null && e.getView().getTitle().contains("Easy Parkours")) {

            int page = Integer.parseInt(e.getInventory().getItem(45).getItemMeta().getLocalizedName());

            if (e.getRawSlot() == 45 && e.getCurrentItem().getType().equals(Material.ARROW)) {

                new EasyParkourMapGUI((Player) e.getWhoClicked(), page - 1);

            } else if (e.getRawSlot() == 53 && e.getCurrentItem().getType().equals(Material.ARROW)) {

                new EasyParkourMapGUI((Player) e.getWhoClicked(), page + 1);

            } else if (e.getRawSlot() == 49 && e.getCurrentItem().getType().equals(Material.BARRIER)) {

                new ParkourGUI((Player) e.getWhoClicked());

            } else if (e.getCurrentItem().getType().equals(Material.LIME_WOOL)) {

                e.getWhoClicked().teleport(Parkour.getParkourMapInstance(e.getCurrentItem().getItemMeta().getLocalizedName()).getSpawnLocation());
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 10, 1);
                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.gui.teleport")))
                        .replaceAll("<player>", e.getWhoClicked().getName())
                        .replaceAll("<playerdisplay>", ((Player) e.getWhoClicked()).getDisplayName())
                        .replaceAll("<map>", Parkour.getParkourMapInstance(e.getCurrentItem().getItemMeta().getLocalizedName()).getName()));
            }
            e.setCancelled(true);

        } else if (e.getInventory() != null && e.getCurrentItem() != null && e.getView().getTitle().contains("Normal Parkours")) {

            int page = Integer.parseInt(e.getInventory().getItem(45).getItemMeta().getLocalizedName());

            if (e.getRawSlot() == 45 && e.getCurrentItem().getType().equals(Material.ARROW)) {

                new NormalParkourMapGUI((Player) e.getWhoClicked(), page - 1);

            } else if (e.getRawSlot() == 53 && e.getCurrentItem().getType().equals(Material.ARROW)) {

                new NormalParkourMapGUI((Player) e.getWhoClicked(), page + 1);

            } else if (e.getRawSlot() == 49 && e.getCurrentItem().getType().equals(Material.BARRIER)) {

                new ParkourGUI((Player) e.getWhoClicked());

            } else if (e.getCurrentItem().getType().equals(Material.YELLOW_WOOL)) {

                e.getWhoClicked().teleport(Parkour.getParkourMapInstance(e.getCurrentItem().getItemMeta().getLocalizedName()).getSpawnLocation());
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 10, 1);
                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.gui.teleport")))
                        .replaceAll("<player>", e.getWhoClicked().getName())
                        .replaceAll("<playerdisplay>", ((Player) e.getWhoClicked()).getDisplayName())
                        .replaceAll("<map>", Parkour.getParkourMapInstance(e.getCurrentItem().getItemMeta().getLocalizedName()).getName()));
            }
            e.setCancelled(true);


        } else if (e.getInventory() != null && e.getCurrentItem() != null && e.getView().getTitle().contains("Hard Parkours")) {

            int page = Integer.parseInt(e.getInventory().getItem(45).getItemMeta().getLocalizedName());

            if (e.getRawSlot() == 45 && e.getCurrentItem().getType().equals(Material.ARROW)) {

                new HardParkourMapGUI((Player) e.getWhoClicked(), page - 1);

            } else if (e.getRawSlot() == 53 && e.getCurrentItem().getType().equals(Material.ARROW)) {

                new HardParkourMapGUI((Player) e.getWhoClicked(), page + 1);

            } else if (e.getRawSlot() == 49 && e.getCurrentItem().getType().equals(Material.BARRIER)) {

                new ParkourGUI((Player) e.getWhoClicked());

            } else if (e.getCurrentItem().getType().equals(Material.RED_WOOL)) {

                e.getWhoClicked().teleport(Parkour.getParkourMapInstance(e.getCurrentItem().getItemMeta().getLocalizedName()).getSpawnLocation());
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 10, 1);
                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.gui.teleport")))
                        .replaceAll("<player>", e.getWhoClicked().getName())
                        .replaceAll("<playerdisplay>", ((Player) e.getWhoClicked()).getDisplayName())
                        .replaceAll("<map>", Parkour.getParkourMapInstance(e.getCurrentItem().getItemMeta().getLocalizedName()).getName()));

            }

            e.setCancelled(true);

        } else if (e.getInventory() != null && e.getCurrentItem() != null && e.getView().getTitle().contains("Difficulty Selection")) {

            if (e.getRawSlot() == 11) {

                new EasyParkourMapGUI((Player) e.getWhoClicked(), 1);

            } else if (e.getRawSlot() == 13) {

                new NormalParkourMapGUI((Player) e.getWhoClicked(), 1);

            } else if (e.getRawSlot() == 15) {

                new HardParkourMapGUI((Player) e.getWhoClicked(), 1);

            }

            e.setCancelled(true);

        }
    }
}