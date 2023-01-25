package com.mysticalsurvival.games.listeners.parkour;

import com.mysticalsurvival.games.core.parkour.Parkour;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ExtraListeners implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        if (e.getEntity() != null) {

            if (e.getEntity() instanceof Player) {

                if (Parkour.getParkourRunInstance(((Player) e.getEntity()).getPlayer()) != null) {

                    e.setCancelled(true);

                }

            }

        }

    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {

        if (e.getPlayer() != null) {

            if (Parkour.getParkourRunInstance(e.getPlayer()) != null) {

                e.setCancelled(true);

            }

        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {

        if (e.getPlayer() != null) {

            if (Parkour.getParkourRunInstance(e.getPlayer()) != null) {

                Parkour.getParkourRunInstance(e.getPlayer()).parkourDeath();

            }

        }

    }

    @EventHandler
    public void onFoodEvent(FoodLevelChangeEvent e) {

        if (e.getEntity() != null && e.getEntity() instanceof Player) {

            if (Parkour.getParkourRunInstance(((Player) e.getEntity())) != null) {

                e.setCancelled(true);

            }

        }

    }

}
