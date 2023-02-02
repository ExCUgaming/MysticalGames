package com.mysticalsurvival.games.listeners.parkour;

import com.mysticalsurvival.games.core.parkour.Parkour;
import com.mysticalsurvival.games.effects.ParkourPlayerActionManager;
import com.mysticalsurvival.games.util.ParkourInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemListeners implements Listener {
    ParkourPlayerActionManager ppam = new ParkourPlayerActionManager();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMoveItem(InventoryClickEvent e) {

        if (Parkour.getParkourRunInstance((Player) e.getWhoClicked()) != null) {

            e.setCancelled(true);

        }

    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {

        if (Parkour.getParkourRunInstance(e.getPlayer()) != null) {

            e.setCancelled(true);

        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (Parkour.getParkourRunInstance(e.getPlayer()) != null) {

            if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {

                if (e.getItem() != null) {

                    if (e.getItem().equals(ParkourInventory.getInventory().getItem(0))) {

                        ppam.playerGotoLastCheckpoint(e.getPlayer());

                    } else if (e.getItem().equals(ParkourInventory.getInventory().getItem(2))) {

                        ppam.playerResetParkourUsingCommand(e.getPlayer());

                    } else if (e.getItem().equals(ParkourInventory.getInventory().getItem(8))) {

                        ppam.playerCancelParkourUsingCommand(e.getPlayer());

                    } else if (e.getItem().equals(ParkourInventory.getVisItem1()) || e.getItem().equals(ParkourInventory.getVisItem2())) {

                        ppam.playerToggleOtherPlayerVisibilityInParkourMap(e.getPlayer());

                    }

                }

                e.setCancelled(true);

            }

        }

    }

}
