package com.mysticalsurvival.games.core;

import com.mysticalsurvival.games.core.parkour.ParkourMap;
import com.mysticalsurvival.games.util.DataWriter;
import com.mysticalsurvival.games.util.PlayerStatistics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class GamePlayer {

    DataWriter dw = new DataWriter();

    private final UUID playerUUID;
    private final HashMap<ParkourMap, Double> bestParkourMapTimes = new HashMap<>();
    private final HashMap<ParkourMap, Integer> parkourFinishes = new HashMap<>();
    private int totalFinishes;

    private final HashMap<ParkourMap, Double> newBestParkourMapTimes = new HashMap<>();
    private final HashMap<ParkourMap, Integer> newParkourFinishes = new HashMap<>();
    private boolean otherPlayerVisibilty = true;

    /**
     *
     * Creates a GamePlayer with all of a certain Player's statistics
     *
     * @param playerUUID The player the GamePlayer will be based off of
     */
    public GamePlayer(UUID playerUUID) {

        //sets values and registers
        this.playerUUID = playerUUID;
        PlayerStatistics.registerGamePlayer(this);
    }

    /**
     *
     * @param pm The ParkourMap that the player has gotten a time on
     * @return The best time that a GamePlayer has gotten on a ParkourMap
     */
    public Double getBestParkourMapTime(ParkourMap pm) {
        return bestParkourMapTimes.get(pm);
    }

    /**
     *
     * Sets the best time a GamePlayer has gotten on a certain ParkourMap
     *
     * @param pm The ParkourMap that the time will be recorded with
     * @param timeInSeconds The time
     * @return False means that the time was not the best and not saved. True means that the time was saved.
     */
    public boolean setBestParkourMapTime(ParkourMap pm, double timeInSeconds) {

        //checks if the best parkour map time is null
        if (bestParkourMapTimes.get(pm) == null) {
            bestParkourMapTimes.put(pm, timeInSeconds);
            newBestParkourMapTimes.put(pm, timeInSeconds);
            return true;
        } else if (bestParkourMapTimes.get(pm) > timeInSeconds) { //checks if the old time is bigger than the new time

            bestParkourMapTimes.put(pm, timeInSeconds);
            newBestParkourMapTimes.put(pm, timeInSeconds);
            return true;
        }

        //if no time is changed
        return false;
    }

    /**
     * @param pm The ParkourMap that the player has finished
     * @return Returns how many times the player has played a certain ParkourMap
     */
    public Integer getParkourFinishes(ParkourMap pm) {
        return parkourFinishes.get(pm);
    }

    /**
     *
     * Sets the ParkourFinishes for the GamePlayer
     *
     * @param pm The ParkourMap that the player has finished
     * @param amount The value to be put
     */
    public void setParkourFinishes(ParkourMap pm, int amount) {
        parkourFinishes.put(pm, amount);
        newParkourFinishes.put(pm, amount);
    }

    /**
     *
     * Sets the total finishes a player has.
     *
     * @param amount The total amount of finishes the GamePlayer will have
     */
    public void setTotalFinishes(int amount) {
        totalFinishes = amount;
    }

    /**
     *
     * Sets the best time a GamePlayer has gotten on a certain ParkourMap
     *
     * @param pm The ParkourMap that the time will be recorded with
     * @param timeInSeconds The time
     * @param write If false, does not save data
     * @return False means that the time was not the best and not saved. True means that the time was saved.
     */
    public boolean setBestParkourMapTime(ParkourMap pm, double timeInSeconds, boolean write) {
        if (bestParkourMapTimes.get(pm) == null) {
            bestParkourMapTimes.put(pm, timeInSeconds);
            if (write) {
                newBestParkourMapTimes.put(pm, timeInSeconds);
            }
            return true;
        } else if (bestParkourMapTimes.get(pm) > timeInSeconds) {
            bestParkourMapTimes.put(pm, timeInSeconds);
            if (write) {
                newBestParkourMapTimes.put(pm, timeInSeconds);
            }
            return true;
        }
        return false;
    }

    /**
     *
     * Sets the ParkourFinishes for the GamePlayer
     *
     * @param pm The ParkourMap that the player has finished
     * @param amount The value to be put
     * @param write If false, does not save data
     */
    public void setParkourFinishes(ParkourMap pm, int amount, boolean write) {
        if (write) {
            newParkourFinishes.put(pm, amount);
        }
        parkourFinishes.put(pm, amount);
    }

    /**
     * @return The GamePlayer's total finishes
     */
    public Integer getTotalFinishes() {
        return totalFinishes;
    }

    /**
     * @return The player associated with the GamePlayer
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    public boolean getOtherPlayerVisibility() {
        return otherPlayerVisibilty;
    }

    public void setOtherPlayerVisibility(boolean val) {
        otherPlayerVisibilty = val;
    }

    /**
     * Saves the GamePlayer's data
     */
    public void save() {

        // get values from hashmap and write to file
        for (ParkourMap pm : newBestParkourMapTimes.keySet()) {
            dw.writeObjectData(PlayerStatistics.DATAFILE, "players."+playerUUID+".parkour.completion."+pm.getName()+".best", newBestParkourMapTimes.get(pm));
        }

        for (ParkourMap pm : newParkourFinishes.keySet()) {
            dw.writeObjectData(PlayerStatistics.DATAFILE, "players."+playerUUID+".parkour.completion."+pm.getName()+".comps", newParkourFinishes.get(pm));
        }

        dw.writeObjectData(PlayerStatistics.DATAFILE, "players."+playerUUID+".parkour.plays", totalFinishes);
        dw.writeBooleanData(PlayerStatistics.DATAFILE, "players."+playerUUID+".othervisible", otherPlayerVisibilty);

        // clear all hashmaps
        newBestParkourMapTimes.clear();
        newParkourFinishes.clear();

    }

}