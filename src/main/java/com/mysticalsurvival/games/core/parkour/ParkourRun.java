package com.mysticalsurvival.games.core.parkour;

import com.mysticalsurvival.games.Games;
import com.mysticalsurvival.games.core.GamePlayer;
import com.mysticalsurvival.games.effects.ParkourPlayerActionManager;
import com.mysticalsurvival.games.util.PlayerStatistics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
    private ItemStack[] playerInventoryContents;

    private final ParkourPlayerActionManager effectLayer = new ParkourPlayerActionManager();
    private Games main = Games.getInstance();

    /**
     * @param player The player that is running the parkour.
     * @param pm The ParkourMap the player is wanting to play.
     */
    public ParkourRun(Player player, ParkourMap pm) {

        //sets values
        this.player = player;
        parkourMap = pm;

        //registers
        registerParkourPlayer(player, this);

        //starts the timer
        startTimer();

        //saves player inventory
        playerInventoryContents = player.getInventory().getContents();

        //sets visibility
        if (!PlayerStatistics.getGamePlayer(player).getOtherPlayerVisibility()) {
            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(main, otherPlayer);
            }
        }

    }

    private void startTimer() {

        //creates scheduler
        BukkitScheduler scheduler = Bukkit.getScheduler();
        bt = scheduler.runTaskTimer(plugin, () -> {

            time = time.add(up);

            effectLayer.sendTimeOnActionBar(player, time);

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
        stopTimer();

        //unregisters the ParkourRun
        removeParkourPlayer(player, this);

        //teleports player back to parkour lobby
        player.teleport(getParkourLobbyLoc());

        //sets inventory back
        player.getInventory().clear();
        player.getInventory().setContents(playerInventoryContents);
        player.updateInventory();

        //sets visibility back
        if (!PlayerStatistics.getGamePlayer(player).getOtherPlayerVisibility()) {
            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                player.showPlayer(main, otherPlayer);
            }
        }
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
            GamePlayer gamePlayer = PlayerStatistics.getGamePlayer(player);

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
            removeParkourPlayer(gamePlayer.getPlayer(), this);

            //teleports player back to lobby
            gamePlayer.getPlayer().teleport(getParkourLobbyLoc());

            //sets inventory back
            player.getInventory().clear();
            player.getInventory().setContents(playerInventoryContents);
            player.updateInventory();

            //sets visibility back
            if (!PlayerStatistics.getGamePlayer(player).getOtherPlayerVisibility()) {
                for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(main, otherPlayer);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Resets the player to last checkpoint
     */
    public void parkourDeath() {

        //checks if the player never made it to a checkpoint
        if (reachedCheckpoint == 0) {
            player.teleport(parkourMap.getSpawnLocation());
        } else {
            //if the player has reached a checkpoint then tp the player to it
            player.teleport(Objects.requireNonNull(getParkourMap().getCheckpoint(reachedCheckpoint)).getTpLocation());
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
    public short getReachedCheckpointId() {
        return reachedCheckpoint;
    }

    public Checkpoint getReachedCheckpoint() {

        for (Checkpoint ck : getParkourMap().getCheckpoints()) {

            if (ck.getId() == reachedCheckpoint) {
                return ck;
            }

        }

        return null;

    }


}