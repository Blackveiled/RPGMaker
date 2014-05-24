/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Social;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SocialManager implements Listener {

    public static enum ChatFormat {

        ERROR,
        DEBUG,
        CHATCOLOR,
        ENEMYCOLOR,
        ALLYCOLOR
    }

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
