/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData.SpawnSystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class NodeChecker {

    private Plugin plugin;
    private final BukkitScheduler nodeScheduler = Bukkit.getScheduler();

    public NodeChecker(Plugin plugin) {
        nodeScheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {

            }
        }, 0L, 5000L);
    }
}
