package com.RPGMakerDev.RPGMaker.EntityData.SpawnSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;

public class Node {

    /**
     * The Node class is intended to provide a base for different types of
     * Nodes, such as mob spawning nodes or block spawning nodes.
     */
    /**
     * Creates a Node object, which will track a location and spawn or respawn
     * whatever is in that location.
     *
     * @param X
     * @param Y
     * @param Z
     */
    public static List<Node> nodes = new ArrayList<>();

    protected float ID;                             // The identification number for this Node.
    private final World world;
    private final double X;                 // The X of the location.
    private final double Y;                 // The Y of the location.
    private final double Z;                 // The Z of the location.
    private float YAW;                         // Which way the object is rotated (if applicable).
    private float PITCH;                     // The degree the object is looking upward (if applicable).

    private long respawnDuration; // How long it takes for this Node to respawn.
    private long despawnTime;      // The time the Node became inactive.
    private boolean Active = true;    // If the Node is currently spawned, set to true.
    private boolean isEnabled = true; // To completely disable this node (stop respawning), set to false.
    
    public Node(World world, double X, double Y, double Z) {
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public Node(World world, double X, double Y, double Z, float PITCH, float YAW) {
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.PITCH = PITCH;
        this.YAW = YAW;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean b) {
        this.Active = b;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean b) {
        this.isEnabled = b;
    }

    public void despawn() {
        Date jd = new Date();
        this.despawnTime = jd.getTime();
    }

    public void respawn() {
        if (isActive()) {
            return;
        }
        setActive(true);
    }

    public void setRespawnTime(long l) {
        this.respawnDuration = l;
    }

    public void setDespawnTime(long l) {
        this.despawnTime = l;
    }

    public long getRespawnDuration() {
        return this.respawnDuration;
    }

    public long getDespawnTime() {
        return this.despawnTime;
    }

    public Location getLocation() {
        Location location = new Location(world, X, Y, Z);
        if (this.PITCH != 0) {
            location.setPitch(this.PITCH);
        }
        if (this.YAW != 0) {
            location.setYaw(this.PITCH);
        }
        return location;
    }
    
    public double getX() {
        return X;
    }
    
    public double getY() {
        return Y;
    }

    public double getZ() {
        return Z;
    }
    
    public float getYaw() {
        return YAW;
    }
    
    public float getPitch() {
        return PITCH;
    }
}
