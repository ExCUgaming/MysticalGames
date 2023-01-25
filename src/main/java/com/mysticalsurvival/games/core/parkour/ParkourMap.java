package com.mysticalsurvival.games.core.parkour;

import com.mysticalsurvival.games.util.DataWriter;
import org.bukkit.Location;
import org.bukkit.Material;

import javax.annotation.Nullable;
import java.util.HashSet;

public class ParkourMap extends Parkour {

    private String name;
    private Location startLocation;
    private Location endLocation;
    private Location spawnLocation;
    private short deathY;
    private final DataWriter dataWriter = new DataWriter();
    private final HashSet<Checkpoint> checkpoints = new HashSet<>();
    private Difficulty difficulty;

    /**
     *
     * Creates a ParkourMap instance.
     *
     * @param name1 - The name of the ParkourMap
     */
    public ParkourMap(String name1, Difficulty df) {
        super();
        if (Parkour.getParkourMapInstance(name1) == null) {
            name = name1;
            difficulty = df;
            Parkour.registerParkourMap(this, name1, df);
            dataWriter.writeConfigSection(Parkour.DATAFILE, "parkours."+name1);
            dataWriter.writeStringData(Parkour.DATAFILE, "parkours."+name1+".difficulty", difficulty.getId());
        }
    }

    public ParkourMap(String name1, Difficulty df, boolean write) {
        super();
        if (Parkour.getParkourMapInstance(name1) == null) {
            name = name1;
            difficulty = df;
            Parkour.registerParkourMap(this, name1, df);
            if (write) {
                dataWriter.writeConfigSection(Parkour.DATAFILE, "parkours." + name1);
            }
        }
    }

    /**
     *
     * Sets the y coordinate that the player gets reset at.
     *
     * @param deathY1 - The y coordinate that the player gets reset at.
     */
    public void setDeathY(short deathY1) {
        this.deathY = deathY1;
        dataWriter.writeShortData(Parkour.DATAFILE, "parkours."+name+".deathy", deathY);
    }

    public void setDeathY(short deathY1, boolean write) {
        this.deathY = deathY1;
        if (write) {
            dataWriter.writeShortData(Parkour.DATAFILE, "parkours." + name + ".deathy", deathY);
        }
    }

    /**
     *
     * Sets the location the player first spawns at when they teleport to the parkour.
     *
     * @param spawnLocation1 - The location to set.
     */
    public void setSpawnLocation(Location spawnLocation1) {
        this.spawnLocation = spawnLocation1;
        dataWriter.writeLocationData(Parkour.DATAFILE,"parkours."+name+".spawnloc", spawnLocation);
    }

    public void setSpawnLocation(Location spawnLocation1, boolean write) {
        this.spawnLocation = spawnLocation1;
        if (write) {
            dataWriter.writeLocationData(Parkour.DATAFILE, "parkours." + name + ".spawnloc", spawnLocation);
        }
    }

    /**
     *
     * Sets the location of the block the player steps on to start the parkour.
     *
     * @param startLocation1 - The location to set.
     */
    public void setStartLocation(Location startLocation1) {
        this.startLocation = startLocation1;
        dataWriter.writeLocationData(Parkour.DATAFILE,"parkours."+name+".startloc", startLocation);
    }

    public void setStartLocation(Location startLocation1, boolean write) {
        this.startLocation = startLocation1;
        if (write) {
            dataWriter.writeLocationData(Parkour.DATAFILE, "parkours." + name + ".startloc", startLocation);
        }
    }

    /**
     *
     * Sets the location of the block the player steps on to end the parkour.
     *
     * @param endLocation1 - The location to set.
     */
    public void setEndLocation(Location endLocation1) {
        this.endLocation = endLocation1;
        dataWriter.writeLocationData(Parkour.DATAFILE,"parkours."+name+".endloc", endLocation);
    }

    public void setEndLocation(Location endLocation1, boolean write) {
        this.endLocation = endLocation1;
        if (write) {
            dataWriter.writeLocationData(Parkour.DATAFILE, "parkours." + name + ".endloc", endLocation);
        }
    }

    /**
     * Deletes the ParkourMap instance.
     */
    public void delete() {
        Parkour.removeParkourMap(name, this);
        dataWriter.deleteConfigSection(Parkour.DATAFILE, "parkours."+name);
    }

    /**
     *
     * Gets the name of this ParkourMap.
     *
     * @return name - The name of the ParkourMap.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * Gets the startLocation of the ParkourMap.
     *
     * @return startLocation - The place where the start block is in the ParkourMap.
     */
    @Nullable
    public Location getStartLocation() {
        return startLocation;
    }

    /**
     *
     * Gets the endLocation of the ParkourMap.
     *
     * @return endLocation - The place where the end block is in the ParkourMap.
     */
    public Location getEndLocation() {
        return endLocation;
    }

    /**
     *
     * Gets the location where the player spawns in the ParkourMap.
     *
     * @return spawnLocation - The place where the player spawns in the parkour.
     */
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    /**
     *
     * Gets the location where the player cant go more down.
     *
     * @return - The Y level players can't be below in the ParkourMap.
     */
    public short getDeathY() {
        return deathY;
    }

    /**
     *
     * @param id - The checkpoint number
     * @return The checkpoint found
     */
    @Nullable
    public Checkpoint getCheckpoint(short id) {
        for (Checkpoint ck : checkpoints) {
            if (ck.getParkourMap().equals(this) && ck.getId() == id) {
                return ck;
            }
        }
        return null;
    }

    public boolean createCheckpoint(short id, Location loc, Location tpLoc) {
        for (Checkpoint ck : checkpoints) {
            if (ck.getId() == id) {
                return false;
            }
        }
        checkpoints.add(new Checkpoint(loc, this, id, tpLoc));
        return true;
    }

    public boolean createCheckpoint(short id, Location loc,Location tpLoc, boolean write) {
        for (Checkpoint ck : checkpoints) {
            if (ck.getId() == id) {
                return false;
            }
        }
        if (id >= 1 && id <=32000) {
            checkpoints.add(new Checkpoint(loc, this, id, tpLoc,write));
            return true;
        }
        return false;
    }

    public boolean deleteCheckpoint(short id) {
        for (Checkpoint ck: checkpoints) {
            if (ck.getId() == id) {
                ck.getLocation().getBlock().setType(Material.AIR);
                ck.selfDestruct();
                checkpoints.remove(ck);
                return true;
            }
        }
        return false;
    }

    public HashSet<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}
