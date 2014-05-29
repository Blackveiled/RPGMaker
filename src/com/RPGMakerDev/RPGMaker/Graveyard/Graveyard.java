/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.Graveyard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.RPGMakerDev.RPGMaker.RPGMaker;
import com.RPGMakerDev.RPGMaker.Util.*;

public class Graveyard {
    private MyConfigManager manager;
    private MyConfig graveyardLoc;
    private ArrayList<Location> graveyards;
    
    public Graveyard() {
        graveyards = new ArrayList<Location>();
        manager = new MyConfigManager(RPGMaker.getPlugin(RPGMaker.class));
        graveyardLoc = manager.getNewConfig("graveyards.yml");
        
        handleConfigs();
        populateGraveyards();
    }
    
    /*
     * Helper to set default config
     */
    private void handleConfigs() {
        if (!graveyardLoc.contains("GraveyardLocations")) {
            graveyardLoc.set("GraveyardLocations", savingLocation());
        }
        
        graveyardLoc.saveConfig();
    }
    
    /*
     * Turns the graveyards into a string for setting to config
     */
    private List<String> savingLocation() {
        List<String> list = new ArrayList<String>();
        int i = 0;
        for (Location loc : graveyards) {
            list.add(loc.getWorld().getName() + "," + loc.getBlockX() + "," + 
             loc.getBlockY() + "," + loc.getBlockZ());
            i++;
        }
        
        return list;
    }
    
    /*
     * Use when disabling the server
     * Saves the graveyards to the config
     */
    public void saveGraveyards() {
        graveyardLoc.set("GraveyardLocations", savingLocation());
        graveyardLoc.saveConfig();
    }
    
    /*
     * Use when enabling the server - Called when init graveyard.
     * Puts all the graveyards from config into arraylist
     */
    private void populateGraveyards() {
        ArrayList<String> list = new ArrayList<String>();
        list = (ArrayList<String>)graveyardLoc.getList("GraveyardLocations");
        
        for (String loc : list) {
            String[] parts = loc.split(",");
            World world = Bukkit.getServer().getWorld(parts[0]);
            if (world == null) {return;}
            
            try {
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                
                graveyards.add(new Location(world, x, y, z));
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("*ERROR* Graveyard location messed up");
            }
        }
        return;
    }
    
    /*
     * Returns the graveyard locations
     */
    public ArrayList<Location> getGraveyards() {
        return graveyards;
    }
    
    public void addGraveyard(Location loc) {
        graveyards.add(loc);
    }
}
