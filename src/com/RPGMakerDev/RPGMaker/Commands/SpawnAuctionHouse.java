/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class SpawnAuctionHouse implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
     String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length == 0) {
                World world = Bukkit.getWorld("world");
                Entity npc = world.spawnEntity(player.getLocation(), EntityType.VILLAGER);
                ((Villager)npc).setCustomName("Auction House");
                ((Villager)npc).setRemoveWhenFarAway(false);
                player.sendMessage("Spawned auction house npc");
                
                return true;
            }
        }
        return false;
    }
}
