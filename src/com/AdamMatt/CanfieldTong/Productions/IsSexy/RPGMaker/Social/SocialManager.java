/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AdamMatt.CanfieldTong.Productions.IsSexy.RPGMaker.Social;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SocialManager implements Listener {

    /**
     * Starts the SocialManager plugin. This plugin will cause other chat
     * plugins to not work.
     */
    public SocialManager() {

    }

    @EventHandler
    public void smChatEvent(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
    }
}