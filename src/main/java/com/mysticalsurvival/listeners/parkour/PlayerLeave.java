package com.mysticalsurvival.listeners.parkour;

import com.mysticalsurvival.core.parkour.Parkour;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void playerLeaveWhileParkour(PlayerQuitEvent e) {

        if (Parkour.getParkourRunInstance(e.getPlayer()) != null) {

            Parkour.getParkourRunInstance(e.getPlayer()).cancelParkour();

        }

    }

}
