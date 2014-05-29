/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.Graveyard;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.RPGMakerDev.RPGMaker.RPGMaker;

public class GraveyardListener implements Listener {
    private RPGMaker plugin;
    
    public GraveyardListener(RPGMaker plugin) {
        this.plugin = plugin;
    }
    
    /*
     * Finds the nearest graveyard in the list of set graveyards
     */
    private Location getNearestGraveyard(Location pLoc) {
        //System.out.println(pLoc);
        ArrayList<Location> graveyards = RPGMaker.gy.getGraveyards();
        if (graveyards.isEmpty()) {
            System.out.println(ChatColor.RED + "NO GRAVEYARDS HAVE BEEN SET");
        }
        else {
            Location minLoc = graveyards.get(0);
            double min = getDistance(graveyards.get(0), pLoc);
            for (int i = 0; i < graveyards.size(); i++) {
                double newMin = getDistance(graveyards.get(i), pLoc);
                if (min > newMin) {
                    min = newMin;
                    minLoc = graveyards.get(i);
                }
            }
            return minLoc;
        }
        //Safetly case, server messed up, so spawning where you died
        return pLoc;
    }
    
    /*
     * Pass in graveyard location and player location
     * Finds distance between locations
     */
    private double getDistance(Location tLoc, Location pLoc) {
        double x = tLoc.getX() - pLoc.getX();
        double y = tLoc.getY() - pLoc.getY();
        double z = tLoc.getZ() - pLoc.getZ();
        
        double distance = x*x + y*y + z*z;
        return distance;
    }
    
    @EventHandler
    public void ChangeSpawningEvent(PlayerRespawnEvent event) {
        event.setRespawnLocation(getNearestGraveyard(event.getPlayer().getLocation()));
        event.getPlayer().sendMessage("Respawning at nearest graveyard");
    }
}
