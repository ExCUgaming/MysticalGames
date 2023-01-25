package com.mysticalsurvival.games.core.parkour;

import com.mysticalsurvival.games.Games;
import com.mysticalsurvival.games.core.GamePlayer;
import com.mysticalsurvival.games.effects.ParkourPlayerActionManager;
import com.mysticalsurvival.games.util.PlayerStatistics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.math.BigDecimal;
import java.util.Objects;

public class ParkourRun extends Parkour {
    private final Games plugin = Games.getInstance();

    private final Player player;
    private final ParkourMap parkourMap;
    private BukkitTask bt;
    private BigDecimal time = new BigDecimal("0");
    private final BigDecimal up = new BigDecimal("0.05");
    private short reachedCheckpoint = 0;

    private final ParkourPlayerActionManager effectLayer = new ParkourPlayerActionManager();

    /**
     *
     * @param p - The player that is running the parkour.
     * @param pm - The map the player is wanting to play.
     */
    public ParkourRun(Player p, ParkourMap pm) {
        player = p;
        parkourMap = pm;
        Parkour.registerParkourPlayer(p, this);
        startTimer(p);
    }

    private void startTimer(Player p) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        bt = scheduler.runTaskTimer(plugin, () -> {

            time = time.add(up);

            effectLayer.sendTimeOnActionBar(p, time);

        }, 0L, 1);
    }

    private void stopTimer() {
        bt.cancel();
    }

    /**
     * Resets the timer
     */
    public void resetTimer() {
        time = new BigDecimal("0");
        reachedCheckpoint = 0;
    }

    /**
     * Cancels the parkour
     */
    public void cancelParkour() {
        stopTimer();
        Parkour.removeParkourPlayer(player, this);
        player.teleport(Parkour.getParkourLobbyLoc());
    }

    /**
     * Finishes the parkour
     */
    public boolean finishParkour() {
        short highestVal = 0;
        for (Checkpoint ck : getParkourMap().getCheckpoints()) {
            if (ck.getId() > highestVal) {
                highestVal = ck.getId();
            }
        }

        if (highestVal == reachedCheckpoint) {
            GamePlayer gamePlayer = PlayerStatistics.getGamePlayer(player);


            //course best time
            gamePlayer.setBestParkourMapTime(parkourMap, time.doubleValue());

            //course comps
            if (gamePlayer.getParkourFinishes(parkourMap) != null) {
                gamePlayer.setParkourFinishes(parkourMap, gamePlayer.getParkourFinishes(parkourMap) + 1);
            } else {
                gamePlayer.setParkourFinishes(parkourMap, 1);
            }

            //overall plays
            if (gamePlayer.getTotalFinishes() != null) {
                gamePlayer.setTotalFinishes(gamePlayer.getTotalFinishes() + 1);
            } else {
                gamePlayer.setTotalFinishes(1);
            }

            stopTimer();
            Parkour.removeParkourPlayer(player, this);
            player.teleport(Parkour.getParkourLobbyLoc());
            return true;
        }
        return false;
    }

    /**
     * Resets the player to last checkpoint
     */
    public void parkourDeath() {
        if (reachedCheckpoint == 0) {
            player.teleport(parkourMap.getSpawnLocation());
            resetTimer();
        } else {
            player.teleport(Objects.requireNonNull(getParkourMap().getCheckpoint(reachedCheckpoint)).getTpLocation());
        }
    }

    /**
     *
     * Returns the ParkourMap the run is in.
     *
     * @return ParkourMap
     */
    public ParkourMap getParkourMap() {
        return parkourMap;
    }

    /**
     *
     * Returns the time in string form
     *
     * @return - The time in string form
     */
    public String getTime() {

        BigDecimal seconds = time.remainder(new BigDecimal("60"));
        BigDecimal minutes = time.subtract(time.remainder(new BigDecimal("60"))).divide(new BigDecimal("60"));

        return minutes.intValue()+":"+seconds;
    }

    public boolean reachCheckpoint(Checkpoint ck) {

        if (ck.getId() == reachedCheckpoint+1) {
            reachedCheckpoint += 1;
            return true;
        } else {
            return false;
        }
    }

    public short getReachedCheckpoint() {
        return reachedCheckpoint;
    }


}