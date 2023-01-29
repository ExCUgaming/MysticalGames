package com.mysticalsurvival.core.parkour;

import com.mysticalsurvival.Games;
import com.mysticalsurvival.core.Game;
import com.mysticalsurvival.util.DataWriter;
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

    public Parkour() {
        super("parkour");
    }

    public static void init() {

        //make sure the datafile is made
        dw.makeDataFile(DATAFILE);

        //writes the ConfigSection for parkours
        if (dw.readConfigSection(DATAFILE, "parkours") ==  null) {
            dw.writeConfigSection(DATAFILE, "parkours");
        }

        //gets the location for the parkour lobby spawn
        parkourLobbyLoc = dw.readLocationData(DATAFILE, "lobby");

        //reads keys in key "parkours", each key is a ParkourMap
        dw.readConfigSection(DATAFILE, "parkours").getKeys(false).forEach(key -> {

            //gets the difficulty for the ParkourMap using String identifiers
            String diff = dw.readStringData(DATAFILE, "parkours." + key + ".difficulty");
            Difficulty df = switch (diff) {
                case "easy" -> Difficulty.EASY;
                case "normal" -> Difficulty.NORMAL;
                case "hard" -> Difficulty.HARD;
                default -> null;
            };

            //creates the parkourmap
            ParkourMap pm = new ParkourMap(key, df, false);

            //sets the locations for the ParkourMap
            pm.setStartLocation(dw.readLocationData(DATAFILE, "parkours."+key+".startloc"), false);
            pm.setEndLocation(dw.readLocationData(DATAFILE, "parkours."+key+".endloc"), false);
            pm.setSpawnLocation(dw.readLocationData(DATAFILE, "parkours."+key+".spawnloc"), false);
            pm.setDeathY(dw.readShortData(Parkour.DATAFILE, "parkours."+key+".deathy"), false);

            //checks to see if the parkour has checkpoints
            if (dw.readConfigSection(DATAFILE, "parkours."+key+".checkpoints") ==  null) {
                dw.writeConfigSection(DATAFILE, "parkours."+key+".checkpoints");
            }

            //gets checkpoints for the parkour
            dw.readConfigSection(DATAFILE, "parkours."+key+".checkpoints").getKeys(false).forEach(keyc ->
                    pm.createCheckpoint(Short.parseShort(keyc), dw.readLocationData(Parkour.DATAFILE, "parkours."+key+".checkpoints."+keyc+".blockloc"), dw.readLocationData(Parkour.DATAFILE, "parkours."+key+".checkpoints."+keyc+".tploc"),false));
        });
    }
    
    static void registerParkourMap(ParkourMap pm, String n, Difficulty difficulty) {
        //registers the ParkourMap

        //stores the ParkourMap instances
        parkourMapInstances.put(n, pm);

        //puts a ParkourMap in a list based on it's difficulty
        switch (difficulty) {

            case EASY -> easyParkours.add(pm);

            case NORMAL -> normalParkours.add(pm);

            case HARD -> hardParkours.add(pm);
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
