/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker;

import com.RPGMakerDev.RPGMaker.Commands.CommandGuild;
import com.RPGMakerDev.RPGMaker.Commands.CommandRPGMaker;
import com.RPGMakerDev.RPGMaker.Commands.SetGraveyard;
import com.RPGMakerDev.RPGMaker.Commands.help;
import com.RPGMakerDev.RPGMaker.Commands.item;
import com.RPGMakerDev.RPGMaker.Commands.socialManager;
import com.RPGMakerDev.RPGMaker.EntityData.CustomEntity;
import com.RPGMakerDev.RPGMaker.EntityData.EntityDatas;
import com.RPGMakerDev.RPGMaker.EntityData.RPGEntity;
import com.RPGMakerDev.RPGMaker.Events.RPGPlayerJoinServer;
import com.RPGMakerDev.RPGMaker.Graveyard.Graveyard;
import com.RPGMakerDev.RPGMaker.Social.SocialManager;
import static com.RPGMakerDev.RPGMaker.Social.SocialManager.Global;
import com.RPGMakerDev.RPGMaker.Social.SocialPlayer;
import com.RPGMakerDev.RPGMaker.StoredData.Database;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

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
    
    //Graveyard plugin
    public static Graveyard gy;
    
    // MySQL Database Information
    public static String hostname;
    public static String username;
    public static String password;
    public static String port;
    public static String schema;
    private static boolean mysqlEnabled = false;

    @Override
    public void onDisable() {
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "RPGMaker" + ChatColor.DARK_GRAY + "] The system is reloading, please relog upon completion.");
        CustomEntity.unregisterEntities();

        //For graveyard plugin
        gy.saveGraveyards();
    }

    @Override
    public void onEnable() {
        CustomEntity.registerEntities();
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "RPGMaker" + ChatColor.DARK_GRAY + "] Reload completed.  To avoid any complications, please relog.");
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
        this.saveConfig();
        this.getServer().getPluginManager().registerEvents(new SocialManager(), this);
        this.getServer().getPluginManager().registerEvents(new RPGPlayerJoinServer(), this);
        this.getCommand("rpgmaker").setExecutor(new CommandRPGMaker());
        this.getCommand("socialmanager").setExecutor(new socialManager());
        this.getCommand("guild").setExecutor(new CommandGuild());
        this.getCommand("help").setExecutor(new help());
        this.getCommand("setgy").setExecutor(new SetGraveyard());
        
        try {
            Database getEntityData = new Database();
            String Query = "SELECT * FROM `creatures`";
            getEntityData.getConnection();
            getEntityData.Query = getEntityData.connection.prepareStatement(Query);
            getEntityData.Results = getEntityData.Query.executeQuery();
            while (getEntityData.Results.next()) {
                EntityDatas datas = new EntityDatas();
                datas.id = getEntityData.Results.getInt("ID");
                datas.name = getEntityData.Results.getString("NAME");
                datas.damage = getEntityData.Results.getInt("DAMAGE");
                datas.moveSpeed = getEntityData.Results.getInt("MOVESPEED");
                datas.entityType = getEntityData.Results.getString("ENTITYTYPE");
                EntityDatas.entityData.put(getEntityData.Results.getInt("ID"), datas);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {

            for (Player p : Bukkit.getOnlinePlayers()) {
                SocialPlayer New = new SocialPlayer(p.getUniqueId());
                Global.joinChannel(New);
                SocialPlayer.socialPlayers.put(p.getUniqueId(), New);

                RPGEntity.players.put(p.getUniqueId(), new RPGEntity(p.getUniqueId()));
            }

            BukkitScheduler Scheduler = Bukkit.getScheduler();
            Scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    for (RPGEntity entity : RPGEntity.players.values()) {
                        if (entity.state.requestDuration > 0) {
                            entity.state.requestDuration = entity.state.requestDuration - 1;
                        }
                        if (entity.state.teleportCooldown > 0) {
                            entity.state.teleportCooldown = entity.state.teleportCooldown - 1;
                            if (entity.state.teleportCooldown == 0) {
                            }
                        }
                    }
                }
            }, 0L, 20L);
        }
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
