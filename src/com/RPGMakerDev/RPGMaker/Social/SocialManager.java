/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Social;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class SocialManager implements Listener {

    public static Channel Global;
    public static List<String> chatLog = new ArrayList<String>();

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
        Global = new Channel(1, "Global");
    }

    @EventHandler
    public void smChatEvent(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Global.sendMessage(SocialPlayer.getStoredSocialPlayer(e.getPlayer().getUniqueId()), e.getMessage());
    }

    @EventHandler
    public void smJoinEvent(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("");
        e.getPlayer().sendMessage(ChatColor.DARK_GRAY + "▬▬▬▬▬  " + ChatColor.YELLOW + "" + ChatColor.BOLD + "RPGMaker" + ChatColor.DARK_GRAY + "  ▬▬▬▬▬");
        e.getPlayer().sendMessage(ChatColor.GRAY + "This server is powered by " + ChatColor.AQUA + "RPGMaker" + ChatColor.GRAY + "!  Minecraft's Most Advanced RPG Plugin!");
        e.getPlayer().sendMessage(ChatColor.GRAY + "If you encounter any bugs, please report them to the server's development team");
        e.getPlayer().sendMessage("");
        e.setJoinMessage("");
        if (e.getPlayer().isOp()) {
            e.setJoinMessage(ChatColor.DARK_GRAY + e.getPlayer().getName() + " has joined the server!");
        }
        if (SocialPlayer.getStoredSocialPlayer(e.getPlayer().getUniqueId()) == null) {

            SocialPlayer New = new SocialPlayer(e.getPlayer().getUniqueId());
            Global.joinChannel(New);
            SocialPlayer.socialPlayers.put(e.getPlayer().getUniqueId(), New);
        }
    }
}
