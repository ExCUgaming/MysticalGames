package com.mysticalsurvival.core.parkour;

import com.mysticalsurvival.util.DataWriter;
import org.bukkit.Location;

public class Checkpoint {

    private final Location location;
    private final ParkourMap parkourMap;
    private final short id;
    private final DataWriter dw = new DataWriter();
    private Location tpLocation;

     protected Checkpoint(Location location, ParkourMap parkourMap, short id, Location tpLocation) {

         //sets values
        this.location = location;
        this.parkourMap = parkourMap;
        this.id = id;
        this.tpLocation = tpLocation;

        //saves data
        dw.writeLocationData(Parkour.DATAFILE,"parkours."+this.parkourMap.getName()+".checkpoints."+id+".blockloc",this.location);
        dw.writeLocationData(Parkour.DATAFILE,"parkours."+this.parkourMap.getName()+".checkpoints."+id+".tploc",this.tpLocation);
    }

    protected Checkpoint(Location location, ParkourMap parkourMap, short id,Location tpLocation ,boolean write) {

         //sets values
        this.location = location;
        this.parkourMap = parkourMap;
        this.id = id;
        this.tpLocation = tpLocation;

        //saves data
        if (write) {
            dw.writeLocationData(Parkour.DATAFILE,"parkours."+this.parkourMap.getName()+".checkpoints."+id+".blockloc",this.location);
            dw.writeLocationData(Parkour.DATAFILE,"parkours."+this.parkourMap.getName()+".checkpoints."+id+".tploc",this.tpLocation);
        }
    }

    public Location getLocation() {
        return location;
    }

    public ParkourMap getParkourMap() {
        return parkourMap;
    }

    public short getId() {
        return id;
    }

    public Location getTpLocation() {
        return tpLocation;
    }

    public void setTpLocation(Location tpLocation) {
        this.tpLocation = tpLocation;
        dw.writeLocationData(Parkour.DATAFILE,"parkours."+this.parkourMap.getName()+".checkpoints."+id+".tploc",this.tpLocation);
    }

    void selfDestruct() {
    dw.deleteConfigSection(Parkour.DATAFILE, "parkours."+parkourMap.getName()+".checkpoints."+id);
    }
}
