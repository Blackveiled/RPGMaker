/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData;

import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class EntityListeners implements Listener {

    @EventHandler
    public void preventAnyHorseDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Horse || e.getEntity() instanceof Villager) {
            e.setCancelled(true);
        }
        if (e.getEntity().isInsideVehicle() && e.getCause().equals(DamageCause.FALL)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void preventHorseDeath2(EntityDamageByBlockEvent e) {
        if (e.getEntity() instanceof Horse) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void preventHorseDeath(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Horse) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (!e.getSpawnReason().equals(SpawnReason.CUSTOM)) {
            e.setCancelled(true);
        }
        if (e.getEntity() instanceof Horse) {
            Horse horse = (Horse) e.getEntity();
            horse.setJumpStrength(1.0);
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
            horse.getInventory().setArmor(new ItemStack(Material.IRON_BARDING, 1));

        }
    }
}
