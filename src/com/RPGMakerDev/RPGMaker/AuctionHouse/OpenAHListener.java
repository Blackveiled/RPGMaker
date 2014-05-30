/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.AuctionHouse;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.RPGMakerDev.RPGMaker.RPGMaker;

public class OpenAHListener implements Listener {
    private RPGMaker plugin;
    public OpenAHListener(RPGMaker plugin) {
        this.plugin = plugin;
    }
    /*
     * If a player right clicks a villager, start AH
     */
    @EventHandler
    public void openAHEvent(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        Entity npc = event.getRightClicked();
        
        if (npc instanceof Villager) {
            Villager ah = (Villager)npc;
            String name = ah.getCustomName();
            if (name != null && name.equals("Auction House")) {
                //player.sendMessage("Right clicked a npc");
                event.setCancelled(true);
                
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        AuctionHouse house = new AuctionHouse(player);
                        house.startAuctionHouse();
                    }
                }, 2);
            }
        }
    }
}
