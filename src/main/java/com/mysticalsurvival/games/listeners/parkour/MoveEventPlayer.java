package com.mysticalsurvival.games.listeners.parkour;

import com.mysticalsurvival.games.core.parkour.Parkour;
import com.mysticalsurvival.games.effects.ParkourPlayerEffectLayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEventPlayer implements Listener {
    ParkourPlayerEffectLayer playerEffectLayer = new ParkourPlayerEffectLayer();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (Parkour.getParkourRunInstance(e.getPlayer()) != null) {
            if (e.getPlayer().getLocation().getY() < Parkour.getParkourRunInstance(e.getPlayer()).getParkourMap().getDeathY()) {
                if (Parkour.getParkourRunInstance(e.getPlayer()).getParkourMap().getSpawnLocation() != null) {
                    playerEffectLayer.playerDeath(e.getPlayer());
                }
            }
        }
    }
}
