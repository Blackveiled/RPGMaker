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
import org.bukkit.event.player.PlayerQuitEvent;

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
        e.getPlayer().sendMessage(ChatColor.GRAY + "This server has a lot of " + ChatColor.AQUA + "" + ChatColor.BOLD + "unique" + ChatColor.GRAY + " features, so feel free to ask around for help, or use the built-in " + ChatColor.YELLOW + "" + ChatColor.ITALIC + "/guide" + ChatColor.GRAY + " command to learn about this server, a completely new experience which Minecraft has never seen!");
        e.getPlayer().sendMessage("");
        e.setJoinMessage("");
        if (!e.getPlayer().hasPlayedBefore()) {
            e.setJoinMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + e.getPlayer().getName() + "" + ChatColor.DARK_GRAY + " has joined the server for the first time!  Welcome them, or join them on their adventure.");
        }
        if (e.getPlayer().isOp()) {
            e.setJoinMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + e.getPlayer().getName() + ChatColor.DARK_GRAY + " has joined the server!");
        }
        if (SocialPlayer.getStoredSocialPlayer(e.getPlayer().getUniqueId()) == null) {
            
            SocialPlayer New = new SocialPlayer(e.getPlayer().getUniqueId());
            Global.joinChannel(New);
            SocialPlayer.socialPlayers.put(e.getPlayer().getUniqueId(), New);
        } else {
            Global.joinChannel(SocialPlayer.getStoredSocialPlayer(e.getPlayer().getUniqueId()));
        }
    }
    
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        SocialManager.Global.leaveChannel(SocialPlayer.socialPlayers.get(e.getPlayer().getUniqueId()));
        e.setQuitMessage("");
        if (e.getPlayer().isOp()) {
            e.setQuitMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + e.getPlayer().getName() + "" + ChatColor.DARK_GRAY + " has left the server!");
        }
    }
}
