package com.mysticalsurvival.games.listeners.lobby;

import com.mysticalsurvival.games.core.GamePlayer;
import com.mysticalsurvival.games.util.PlayerStatistics;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GamePlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        new GamePlayer(e.getPlayer());

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        PlayerStatistics.unregisterGamePlayer(PlayerStatistics.getGamePlayer(e.getPlayer()));

    }

}
