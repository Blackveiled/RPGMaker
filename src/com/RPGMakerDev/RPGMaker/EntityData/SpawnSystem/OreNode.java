/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.EntityData.SpawnSystem;

import com.RPGMakerDev.RPGMaker.RPGMaker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author matthew
 */
public class OreNode extends Node {
    private Material type;
    private ItemStack loot;
    
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
    
    public void respawnEvent(OreNode node) {
        final Location loc = node.getLocation();
        Block b = loc.getBlock();
        final Material type = node.type;
        b.setType(Material.COBBLESTONE);   //What block turns into once broken
        
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(RPGMaker.getPlugin(RPGMaker.class), new Runnable() {
                    @Override
                    public void run() {
                        loc.getBlock().setType(type);
                    }
                }, 10 * 20);  //Respawn time
    }
    
    public void getLoot(Player player) {
        //gives the player his loot
        player.getInventory().addItem(loot);
    }
}
