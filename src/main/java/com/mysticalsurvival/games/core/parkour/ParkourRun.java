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
import java.util.UUID;

public class ParkourRun extends Parkour {
    private final Games plugin = Games.getInstance();

    private final UUID playerUUID;
    private final ParkourMap parkourMap;
    private BukkitTask bt;
    private BigDecimal time = new BigDecimal("0");
    private final BigDecimal up = new BigDecimal("0.05");
    private short reachedCheckpoint = 0;

    private final ParkourPlayerActionManager effectLayer = new ParkourPlayerActionManager();

    /**
     * @param p The player that is running the parkour.
     * @param pm The ParkourMap the player is wanting to play.
     */
    public ParkourRun(Player p, ParkourMap pm) {

        //sets values
        playerUUID = p.getUniqueId();
        parkourMap = pm;

        //registers
        Parkour.registerParkourPlayer(p, this);

        //starts the timer
        startTimer(p);
    }

    private void startTimer(Player p) {

        //creates scheduler
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

        //sets time and checkpoint to 0
        time = new BigDecimal("0");
        reachedCheckpoint = 0;

    }

    /**
     * Cancels the parkour
     */
    public void cancelParkour() {

        //gets the player
        Player p = Bukkit.getPlayer(playerUUID);

        stopTimer();

        //unregisters the ParkourRun
        Parkour.removeParkourPlayer(p, this);

        //teleports player back to parkour lobby
        p.teleport(Parkour.getParkourLobbyLoc());
    }

    /**
     * Finishes the parkour
     */
    public boolean finishParkour() {

        //initialize variable
        short highestVal = 0;

        //gets the last checkpoint
        for (Checkpoint ck : getParkourMap().getCheckpoints()) {
            if (ck.getId() > highestVal) {
                highestVal = ck.getId();
            }
        }

        //checks if the player reached the last checkpoint
        if (highestVal == reachedCheckpoint) {
            GamePlayer gamePlayer = PlayerStatistics.getGamePlayer(Bukkit.getPlayer(playerUUID));

            //course best time
            gamePlayer.setBestParkourMapTime(parkourMap, time.doubleValue());

            //course comps logic
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

            //stops the time
            stopTimer();

            //unregisters ParkourRun
            Parkour.removeParkourPlayer(gamePlayer.getPlayer(), this);

            //teleports player back to lobby
            gamePlayer.getPlayer().teleport(Parkour.getParkourLobbyLoc());
            return true;
        }
        return false;
    }

    /**
     * Resets the player to last checkpoint
     */
    public void parkourDeath() {
        Player p = Bukkit.getPlayer(playerUUID);

        //checks if the player never made it to a checkpoint
        if (reachedCheckpoint == 0) {
            p.teleport(parkourMap.getSpawnLocation());
        } else {
            //if the player has reached a checkpoint then tp the player to it
            p.teleport(Objects.requireNonNull(getParkourMap().getCheckpoint(reachedCheckpoint)).getTpLocation());
        }
    }

    /**
     * @return The ParkourMap that this ParkourRun is running on
     */
    public ParkourMap getParkourMap() {
        return parkourMap;
    }

    /**
     * @return The time in string form
     */
    public String getTime() {

        //math to figure out the time

        //[ TIME % 60 = SECONDS ]
        BigDecimal seconds = time.remainder(new BigDecimal("60"));

        //[ (TIME - SECONDS) / 60 = MINUTES ]
        BigDecimal minutes = time.subtract(seconds).divide(new BigDecimal("60"));

        return minutes.intValue()+":"+seconds;
    }

    /**
     *
     * If a player reaches a certain checkpoint, this method is called.
     *
     * @param ck The checkpoint the player reached
     * @return If true checkpoint was reached, if false checkpoint not reached
     */
    public boolean reachCheckpoint(Checkpoint ck) {

        //if the checkpoint reached equals the last reached checkpointId + 1, then the checkpoint is valid
        if (ck.getId() == reachedCheckpoint+1) {
            reachedCheckpoint += 1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return The latest checkpoint the player has reached
     */
    public short getReachedCheckpoint() {
        return reachedCheckpoint;
    }


}