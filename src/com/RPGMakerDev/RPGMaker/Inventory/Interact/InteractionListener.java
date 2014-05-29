/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Inventory.Interact;

import java.util.Date;
import net.minecraft.server.v1_7_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InteractionListener implements Listener {

    private World world;

    @EventHandler
    public void playerQuitDespawnMount(PlayerQuitEvent e) {
        if (Mount.activeMounts.containsKey(e.getPlayer().getUniqueId())) {
            Mount.activeMounts.get(e.getPlayer().getUniqueId()).despawnOld();
            Mount.activeMounts.remove(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void InteractWithEntity(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Horse) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (Mount.activeMounts.containsKey(p.getUniqueId())) {
                    if (Mount.activeMounts.get(p.getUniqueId()).mountUUID.equals(e.getRightClicked().getUniqueId()) && !Mount.activeMounts.get(p.getUniqueId()).uuid.equals(e.getPlayer().getUniqueId())) {
                        e.getPlayer().sendMessage(ChatColor.RED + "That mount does not belong to you!");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void Interact(PlayerInteractEvent e) {
        if (!(e.getItem() == null)) {
            if (e.getItem().hasItemMeta()) {
                if (ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Summon Mount")) {
                    Mount save = new Mount(e.getPlayer().getUniqueId());
                    Date timecheck = new Date();
                    long currentTime = timecheck.getTime();

                    if (Mount.activeMounts.containsKey(e.getPlayer().getUniqueId())) {
                        long lastTime = Mount.activeMounts.get(e.getPlayer().getUniqueId()).timeSpawned;
                        if (currentTime <= lastTime) {
                            int minutesLeft = (int) ((lastTime - currentTime) / 60000);
                            Bukkit.getPlayer(e.getPlayer().getUniqueId()).sendMessage(ChatColor.RED + "You cannot summon another mount for another " + minutesLeft + " minutes.");
                            return;
                        } else {
                            save = Mount.activeMounts.get(e.getPlayer().getUniqueId());
                            save.despawnOld();
                            save.spawnMount();
                            save.timeSpawned = new Date().getTime() + 600000;

                            Mount.activeMounts.put(e.getPlayer().getUniqueId(), save);
                        }
                    } else {
                        save.spawnMount();
                        Mount.activeMounts.put(e.getPlayer().getUniqueId(), save);
                    }
                }
            }
        }
    }
}
