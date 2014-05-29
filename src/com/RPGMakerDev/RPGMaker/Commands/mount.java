/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Commands;

import net.minecraft.server.v1_7_R3.World;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class mount implements CommandExecutor {

    private World world;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.getNearbyEntities(1.0, 1.0, 1.0).isEmpty()) {
                Entity mounted = p.getNearbyEntities(1.0, 1.0, 1.0).get(0);
                mounted.setPassenger((Entity) p);
                if (mounted instanceof Horse) {
                    Horse horse = (Horse) mounted;
                    horse.setJumpStrength(6.0);
                    horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
                    horse.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING, 1));
                    horse.setOwner((AnimalTamer) p);
                    horse.setTamed(true);
                }

            }
        }
        return true;
    }

}
