/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker;

import com.RPGMakerDev.RPGMaker.Commands.CommandGuild;
import com.RPGMakerDev.RPGMaker.Commands.CommandRPGMaker;
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
    public static String serverName;
    public static String serverDescription;

    // MySQL Database Information
    public static String hostname;
    public static String username;
    public static String password;
    public static String port;
    public static String schema;
    private static boolean mysqlEnabled = false;

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
	this.getConfig();
	serverName = this.getConfig().getString("RPGMaker.Server.Name");
	serverDescription = this.getConfig().getString("RPGMaker.Server.Description");
	hostname = this.getConfig().getString("RPGMaker.Database.Host");
	username = this.getConfig().getString("RPGMaker.Database.User");
	password = this.getConfig().getString("RPGMaker.Database.Password");
	port = this.getConfig().getString("RPGMaker.Database.Port");
	schema = this.getConfig().getString("RPGMaker.Database.Schema");
	mysqlEnabled = this.getConfig().getBoolean("RPGMaker.Server.mysql-enabled");
	debugMode = this.getConfig().getBoolean("RPGMaker.Server.debug-enabled");
	this.getServer().getPluginManager().registerEvents(new com.RPGMakerDev.RPGMaker.Social.SocialManager(), this);
	this.getServer().getPluginManager().registerEvents(new com.RPGMakerDev.RPGMaker.Events.RPGPlayerJoinServer(), this);
	this.getCommand("rpgmaker").setExecutor(new CommandRPGMaker());
	this.getCommand("guild").setExecutor(new CommandGuild());
    }

    /**
     * Checks if the plugin is in Debug Mode. If the plugin is in debug mode,
     * messages that would normally not be sent to players will be. This is
     * mainly for testing purposes.
     *
     * @return True or False.
     */
    public static boolean debugMode() {
	return debugMode;
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
