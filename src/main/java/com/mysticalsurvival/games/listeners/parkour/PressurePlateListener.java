package com.mysticalsurvival.games.listeners.parkour;

import com.mysticalsurvival.games.core.parkour.Checkpoint;
import com.mysticalsurvival.games.core.parkour.Parkour;
import com.mysticalsurvival.games.core.parkour.ParkourMap;
import com.mysticalsurvival.games.effects.ParkourPlayerEffectLayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PressurePlateListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ParkourPlayerEffectLayer playerEffectLayer = new ParkourPlayerEffectLayer();


        if (e.getClickedBlock() != null) {


            if (e.getClickedBlock().getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {


                if (e.getAction().equals(Action.PHYSICAL)) {


                    for (ParkourMap pm : Parkour.getAllParkourMapInstances().values()) {


                        if (pm.getStartLocation() != null && pm.getEndLocation() != null) {


                            if (pm.getStartLocation().equals(e.getClickedBlock().getLocation())) {


                                playerEffectLayer.playerStepOnStartPressurePlate(e.getPlayer(), pm, e.getClickedBlock().getLocation());
                                return;


                            } else if (pm.getEndLocation().equals(e.getClickedBlock().getLocation())) {

                                playerEffectLayer.playerStepOnEndPressurePlate(e.getPlayer(), pm, e.getClickedBlock().getLocation());
                                return;

                            }
                        }
                    }
                }
            } else if (e.getClickedBlock().getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) {


                if (e.getAction().equals(Action.PHYSICAL)) {


                    for (ParkourMap pm : Parkour.getAllParkourMapInstances().values()) {


                        if (pm.getCheckpoints() != null) {


                            for (Checkpoint ck : pm.getCheckpoints()) {


                                if (ck.getLocation().equals(e.getClickedBlock().getLocation())) {


                                    playerEffectLayer.playerStepCheckpoint(ck, e.getPlayer());
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
