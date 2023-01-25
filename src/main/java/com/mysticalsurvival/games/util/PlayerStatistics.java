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
import java.util.List;
import java.util.logging.Level;

public class PlayerStatistics {

    private static BukkitTask bt;

    public static final File DATAFILE = new File(Games.getPluginDataFolder(), "playerstats.yml");
    private static final DataWriter dataWriter = new DataWriter();

    private static Games main = Games.getInstance();

    private static final HashMap<Player, GamePlayer> gamePlayers = new HashMap<>();

    public static void init() {
        dataWriter.makeDataFile(DATAFILE);

        BukkitScheduler scheduler = Bukkit.getScheduler();
        bt = scheduler.runTaskTimer(main, () -> {

            for (GamePlayer gp : gamePlayers.values()) {
                gp.save();
            }

            main.getLogger().log(Level.CONFIG, "Successfully saved player statistic data.");

        }, 0L, 6000);
    }

    public static void registerGamePlayer(GamePlayer gamePlayer) {

        gamePlayers.put(gamePlayer.getPlayer(), gamePlayer);
        if (dataWriter.readConfigSection(DATAFILE, "players."+gamePlayer.getPlayer().getUniqueId()+".parkour.completion") != null) {
            dataWriter.readConfigSection(DATAFILE, "players." + gamePlayer.getPlayer().getUniqueId() + ".parkour.completion").getKeys(false).forEach(key -> {
                gamePlayer.setBestParkourMapTime(Parkour.getParkourMapInstance(key), dataWriter.readDouble(DATAFILE, "players." + gamePlayer.getPlayer().getUniqueId() + ".parkour.completion." + key + ".best"), false);
                gamePlayer.setParkourFinishes(Parkour.getParkourMapInstance(key), dataWriter.readIntData(DATAFILE, "players." + gamePlayer.getPlayer().getUniqueId() + ".parkour.completion." + key + ".comps"), false);
            });
            gamePlayer.setTotalFinishes(dataWriter.readIntData(DATAFILE, "players." + gamePlayer.getPlayer().getUniqueId() + ".parkour.plays"));
        } else {
            dataWriter.writeConfigSection(DATAFILE, "players."+gamePlayer.getPlayer().getUniqueId()+".parkour.completion");
        }
    }

    public static void unregisterGamePlayer(GamePlayer gamePlayer) {

        gamePlayers.remove(gamePlayer.getPlayer());
        gamePlayer.save();

    }

    public static GamePlayer getGamePlayer(Player player) {

        return gamePlayers.get(player);

    }

    public static Collection<GamePlayer> getGameAllGamePlayers() {
        return gamePlayers.values();
    }

}