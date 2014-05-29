/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData.LootingSystem;

import com.RPGMakerDev.RPGMaker.Inventory.RPGItem;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R3.block.CraftChest;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class LootingListener implements Listener {

    @EventHandler
    public void lootChestListener(InventoryOpenEvent e) {
        Location loc = ((CraftChest) e.getInventory().getHolder()).getLocation();
        if (LootChest.chests.containsKey(loc)) {
            LootChest loot = LootChest.chests.get(loc);
            if (loot.uuid.equals(e.getPlayer().getUniqueId())) {
                e.getView().close();
            }
        }
    }

    @EventHandler
    public void removeItemSpawns(ItemSpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void handleEntityDeaths(EntityDeathEvent e) {
        Entity entity = e.getEntity();

        if (ChatColor.stripColor(e.getEntity().getCustomName()).equalsIgnoreCase("Zombie Lv1")) {
            RPGItem sword = new RPGItem(2012, 1);
            sword.updateItemLore();
            sword.updateItemMeta();
            Loot WoodSword1 = new Loot(sword, 15.0);
            List<Loot> loots = new ArrayList<>();
            loots.add(WoodSword1);
            LootChest newChest = new LootChest(e.getEntity().getKiller().getUniqueId(), e.getEntity().getLocation(), loots);
        }
    }
}
