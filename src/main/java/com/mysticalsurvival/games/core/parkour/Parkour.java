package com.mysticalsurvival.games.core.parkour;

import com.mysticalsurvival.games.Games;
import com.mysticalsurvival.games.core.Game;
import com.mysticalsurvival.games.util.DataWriter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

public class Parkour extends Game {

    public static final File DATAFILE = new File(Games.getPluginDataFolder(), "parkour.yml");
    private static final DataWriter dw = new DataWriter();
    private static final HashMap<String, ParkourMap> parkourMapInstances = new HashMap<>();
    private static final HashMap<Player, ParkourRun> parkourPlayers = new HashMap<>();
    private static final Set<ParkourMap> easyParkours= new HashSet<>();
    private static final Set<ParkourMap> normalParkours= new HashSet<>();
    private static final Set<ParkourMap> hardParkours= new HashSet<>();
    private static Location parkourLobbyLoc;

    public static final String completion = "completion";
    public static final String plays = "plays";
    private static Parkour instance;

    public Parkour() {
        super("parkour");
        instance = this;
    }

    public static void init() {
        dw.makeDataFile(DATAFILE);
        if (dw.readConfigSection(DATAFILE, "parkours") ==  null) {
            dw.writeConfigSection(DATAFILE, "parkours");
        }
        parkourLobbyLoc = dw.readLocationData(DATAFILE, "lobby");

        dw.readConfigSection(DATAFILE, "parkours").getKeys(false).forEach(key -> {

            String diff = dw.readStringData(DATAFILE, "parkours." + key + ".difficulty");
            Difficulty df = Difficulty.EASY;
            switch (diff) {
                case "normal":
                    df = Difficulty.NORMAL;
                    break;

                case "hard":
                    df = Difficulty.HARD;
                    break;
            }
            ParkourMap pm = new ParkourMap(key, df, false);
            pm.setStartLocation(dw.readLocationData(DATAFILE, "parkours."+key+".startloc"), false);
            pm.setEndLocation(dw.readLocationData(DATAFILE, "parkours."+key+".endloc"), false);
            pm.setSpawnLocation(dw.readLocationData(DATAFILE, "parkours."+key+".spawnloc"), false);
            pm.setDeathY(dw.readShortData(Parkour.DATAFILE, "parkours."+key+".deathy"), false);

            if (dw.readConfigSection(DATAFILE, "parkours."+key+".checkpoints") ==  null) {
                dw.writeConfigSection(DATAFILE, "parkours."+key+".checkpoints");
            }
            dw.readConfigSection(DATAFILE, "parkours."+key+".checkpoints").getKeys(false).forEach(keyc ->
                    pm.createCheckpoint(Short.parseShort(keyc), dw.readLocationData(Parkour.DATAFILE, "parkours."+key+".checkpoints."+keyc+".blockloc"), dw.readLocationData(Parkour.DATAFILE, "parkours."+key+".checkpoints."+keyc+".tploc"),false));
        });
    }

    public static Parkour getInstance() {
        return instance;
    }

    static void registerParkourMap(ParkourMap pm, String n, Difficulty difficulty) {
        parkourMapInstances.put(n, pm);
        switch (difficulty) {

            case EASY -> {
                easyParkours.add(pm);
                break;
            }

            case NORMAL -> {
                normalParkours.add(pm);
                break;
            }

            case HARD -> {
                hardParkours.add(pm);
                break;
            }

        }

    }

    static void removeParkourMap(String n, ParkourMap pm) {
        parkourMapInstances.remove(n, pm);

    }

    @Nullable
    public static ParkourMap getParkourMapInstance(String identifier) {
        return parkourMapInstances.get(identifier);
    }

    public static HashMap<String, ParkourMap> getAllParkourMapInstances() {
        return parkourMapInstances;
    }

    public static void registerParkourPlayer(Player player, ParkourRun parkourRun) {
        parkourPlayers.put(player, parkourRun);
    }

    public static void removeParkourPlayer(Player player, ParkourRun parkourRun) {
        parkourPlayers.remove(player, parkourRun);
    }

    public static HashMap<Player, ParkourRun> getAllParkourPlayers() {
        return parkourPlayers;
    }

    public static ParkourRun getParkourRunInstance(Player player) {
        return parkourPlayers.get(player);
    }

    public static Location getParkourLobbyLoc() {
        return parkourLobbyLoc;
    }

    public static void setParkourLobbyLoc(Location parkourLobbyLoc) {
        Parkour.parkourLobbyLoc = parkourLobbyLoc;
        dw.writeLocationData(DATAFILE, "lobby", parkourLobbyLoc);
    }

    public static Set<ParkourMap> getParkourMapInstances(Difficulty df) {
        switch (df) {
            case EASY -> {
                return easyParkours;
            }
            case NORMAL -> {
                return normalParkours;
            }
            case HARD -> {
                return hardParkours;
            }
        }
        return null;
    }
}
