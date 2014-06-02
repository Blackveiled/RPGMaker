/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.EntityData.SpawnSystem;

import com.RPGMakerDev.RPGMaker.RPGMaker;
import com.RPGMakerDev.RPGMaker.StoredData.Database;
import java.sql.SQLException;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author matthew
 */
public class OreNode extends Node {
    private Material type;
    private ItemStack loot;
    public static HashMap<Integer, OreNode> ores = new HashMap<>();
    
    public static void getOresDatabase() {
        try {
            Database ore = new Database();
            ore.getConnection();
            String Query = "select * from `ore`";
            ore.Query = ore.connection.prepareStatement(Query);
            ore.Results = ore.Query.executeQuery();
            while(ore.Results.next()) {
                int id = ore.Results.getInt("id");
                String material = ore.Results.getString("material");
                int respawn = ore.Results.getInt("respawn");
                double x = ore.Results.getDouble("x");
                double y = ore.Results.getDouble("y");
                double z = ore.Results.getDouble("z");
                        
                ores.put(ore.Results.getInt("ID"), new OreNode(
                        Material.getMaterial(material), respawn,
                        Bukkit.getWorld("world"), x, y, z));
                Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
                loc.getBlock().setMetadata("oreID", new FixedMetadataValue(RPGMaker.getPlugin(RPGMaker.class), id));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public OreNode(Material type, long respawn, World world, double X, double Y, double Z) {
        super(world, X, Y, Z);
        this.type = type;
        setRespawnTime(respawn);
        //depending on type, set loot
    }

    public OreNode(Material type, long respawn, World world, double X, double Y, double Z, float PITCH, float YAW) {
        super(world, X, Y, Z, PITCH, YAW);
        this.type = type;
        setRespawnTime(respawn);
    }
    
    public void respawnEvent() {
        final Location loc = this.getLocation();
        Block b = loc.getBlock();
        final Material types = this.type;
        b.setType(Material.COBBLESTONE);   //What block turns into once broken
        
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(RPGMaker.getPlugin(RPGMaker.class), new Runnable() {
                    @Override
                    public void run() {
                        loc.getBlock().setType(types);
                    }
                }, getRespawnDuration());  //Respawn time
    }
    
    public void getLoot(Player player) {
        //gives the player his loot
        player.getInventory().addItem(loot);
    }
}
