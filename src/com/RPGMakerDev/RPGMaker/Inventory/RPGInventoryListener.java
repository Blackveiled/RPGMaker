/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Inventory;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class RPGInventoryListener implements Listener {

    @EventHandler
    public void preventItemDrops(PlayerDropItemEvent e) {
        e.getPlayer().sendMessage(ChatColor.RED + "Use the /destroy command to destroy items, dropping items is not allowed.");
        e.setCancelled(true);
    }

    @EventHandler
    public void preventItemPickups(PlayerPickupItemEvent e) {
        e.getPlayer().sendMessage(ChatColor.RED + "Something wrong occured, picking up items is not possible.");
        e.setCancelled(true);
    }
}
