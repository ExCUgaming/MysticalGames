package com.mysticalsurvival.games.util;

import com.mysticalsurvival.games.Games;
import com.mysticalsurvival.games.core.GamePlayer;
import com.mysticalsurvival.games.core.parkour.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerStatistics {

    private static BukkitTask bt;

    public static final File DATAFILE = new File(Games.getPluginDataFolder(), "playerstats.yml");
    private static final DataWriter dataWriter = new DataWriter();

    private static final Games main = Games.getInstance();

    private static final HashMap<UUID, GamePlayer> gamePlayers = new HashMap<>();

    public static void init() {
        //makes sure the datafile is there
        dataWriter.makeDataFile(DATAFILE);

        //starts save loop in case of server crashes
        BukkitScheduler scheduler = Bukkit.getScheduler();
        bt = scheduler.runTaskTimer(main, () -> {

            for (GamePlayer gp : gamePlayers.values()) {
                gp.save();
            }

            main.getLogger().log(Level.CONFIG, "Successfully saved player statistic data.");

        }, 0L, 6000);
    }

    public static void registerGamePlayer(GamePlayer gamePlayer) {
        //registers a GamePlayer

        //puts GamePlayer in a HashMap for easy identification
        gamePlayers.put(gamePlayer.getPlayer().getUniqueId(), gamePlayer);

        //checks if the player's statistics are null
        if (dataWriter.readConfigSection(DATAFILE, "players."+gamePlayer.getPlayer().getUniqueId()) != null) {

            //does something for each key or for each ParkourMap played by the player
            dataWriter.readConfigSection(DATAFILE, "players." + gamePlayer.getPlayer().getUniqueId() + ".parkour.completion").getKeys(false).forEach(key -> {

                //sets the values
                gamePlayer.setBestParkourMapTime(Parkour.getParkourMapInstance(key), dataWriter.readDouble(DATAFILE, "players." + gamePlayer.getPlayer().getUniqueId() + ".parkour.completion." + key + ".best"), false);
                gamePlayer.setParkourFinishes(Parkour.getParkourMapInstance(key), dataWriter.readIntData(DATAFILE, "players." + gamePlayer.getPlayer().getUniqueId() + ".parkour.completion." + key + ".comps"), false);

            });

            //sets the value for total plays
            gamePlayer.setTotalFinishes(dataWriter.readIntData(DATAFILE, "players." + gamePlayer.getPlayer().getUniqueId() + ".parkour.plays"));
            if (dataWriter.readBooleanData(PlayerStatistics.DATAFILE, "players." + gamePlayer.getPlayer().getUniqueId() + ".othervisible") != null) {
                gamePlayer.setOtherPlayerVisibility(dataWriter.readBooleanData(PlayerStatistics.DATAFILE, "players." + gamePlayer.getPlayer().getUniqueId() + ".othervisible"));
            }

        } else {

            //if the player stats are null, it will make a ConfigSection for its stats
            dataWriter.writeConfigSection(DATAFILE, "players."+gamePlayer.getPlayer().getUniqueId()+".parkour.completion");

        }
    }

    public static void unregisterGamePlayer(GamePlayer gamePlayer) {

        gamePlayers.remove(gamePlayer.getPlayer().getUniqueId());
        gamePlayer.save();

    }

    public static GamePlayer getGamePlayer(Player player) {

        return gamePlayers.get(player.getUniqueId());

    }

    public static Collection<GamePlayer> getAllGamePlayers() {
        return gamePlayers.values();
    }

}