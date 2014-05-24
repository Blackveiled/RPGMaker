/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AdamMatt.CanfieldTong.Productions.IsSexy.RPGMaker;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Matthew Tong
 * @author Adam Canfield
 * @author John Smith
 *
 */
public class RPGMaker extends JavaPlugin {

    private static boolean debugMode = true;

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new com.AdamMatt.CanfieldTong.Productions.IsSexy.RPGMaker.Social.SocialManager(), this);
        this.getServer().getPluginManager().registerEvents(new com.AdamMatt.CanfieldTong.Productions.IsSexy.RPGMaker.Events.RPGPlayerJoinServer(), this);
        this.getCommand("rpgmaker").setExecutor(new com.AdamMatt.CanfieldTong.Productions.IsSexy.RPGMaker.Commands.rpgmaker());
    }

    /**
     * Checks if the plugin is in Debug Mode. If the plugin is in debug mode,
     * messages that would normally not be sent to players will be. This is
     * mainly for testing purposes.
     *
     * @return True or False.
     */
    public static boolean debugMode() {
        if (RPGMaker.debugMode) {
            return true;
        }
        return false;
    }

    /**
     * Debug message chat formatting
     *
     * @param Message The message being sent to the player.
     * @return Returns the message. If debugMode is not enabled, returns null;
     */
    public static String debugMessage(String Message) {
        if (RPGMaker.debugMode()) {
            return ChatColor.RED + "" + ChatColor.BOLD + "[Debug]" + ChatColor.DARK_GRAY + Message;
        }
        return null;
    }
}
