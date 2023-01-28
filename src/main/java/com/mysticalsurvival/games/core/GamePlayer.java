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

    public GamePlayer(Player player) {
        this.playerUUID = player.getUniqueId();
        PlayerStatistics.registerGamePlayer(this);
    }

    public double getBestParkourMapTime(ParkourMap pm) {
        return bestParkourMapTimes.get(pm);
    }

    public boolean setBestParkourMapTime(ParkourMap pm, double timeInSeconds) {
        if (bestParkourMapTimes.get(pm) == null) {
            bestParkourMapTimes.put(pm, timeInSeconds);
            newBestParkourMapTimes.put(pm, timeInSeconds);
            return true;
        } else if (bestParkourMapTimes.get(pm) > timeInSeconds) {
            bestParkourMapTimes.put(pm, timeInSeconds);
            newBestParkourMapTimes.put(pm, timeInSeconds);
            return true;
        }
        return false;
    }

    public Integer getParkourFinishes(ParkourMap pm) {
        return parkourFinishes.get(pm);
    }

    public void setParkourFinishes(ParkourMap pm, int amount) {
        parkourFinishes.put(pm, amount);
        newParkourFinishes.put(pm, amount);
    }

    public void setTotalFinishes(int amount) {
        totalFinishes = amount;
    }

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

    public void setParkourFinishes(ParkourMap pm, int amount, boolean write) {
        if (write) {
            newParkourFinishes.put(pm, amount);
        }
        parkourFinishes.put(pm, amount);
    }

    public Integer getTotalFinishes() {
        return totalFinishes;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    public void save() {

        // get values from hashmap and write to file
        for (ParkourMap pm : newBestParkourMapTimes.keySet()) {
            dw.writeObjectData(PlayerStatistics.DATAFILE, "players."+playerUUID+".parkour.completion."+pm.getName()+".best", newBestParkourMapTimes.get(pm));
        }

        for (ParkourMap pm : newParkourFinishes.keySet()) {
            dw.writeObjectData(PlayerStatistics.DATAFILE, "players."+playerUUID+".parkour.completion."+pm.getName()+".comps", newParkourFinishes.get(pm));
        }

        dw.writeObjectData(PlayerStatistics.DATAFILE, "players."+playerUUID+".parkour.plays", totalFinishes);

        // clear all hashmaps
        newBestParkourMapTimes.clear();
        newParkourFinishes.clear();

    }

}