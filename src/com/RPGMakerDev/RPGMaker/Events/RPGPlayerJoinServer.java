/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Events;

import com.RPGMakerDev.RPGMaker.EntityData.RPGEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RPGPlayerJoinServer implements Listener {

    @EventHandler
    public void onRPGPlayerJoinServer(PlayerJoinEvent e) {
        //// add event actions later
        Player p = e.getPlayer();
        if (!RPGEntity.players.containsKey(e.getPlayer().getUniqueId())) {
            RPGEntity.players.put(e.getPlayer().getUniqueId(), new RPGEntity(p.getUniqueId()));
        }
    }
}
