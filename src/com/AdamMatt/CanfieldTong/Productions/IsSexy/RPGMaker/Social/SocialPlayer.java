/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AdamMatt.CanfieldTong.Productions.IsSexy.RPGMaker.Social;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SocialPlayer {

    private UUID UUID;
    private boolean onlineStatus = true;

    /*  REPUTATION                      */
    private Integer karma = 250;
    private Integer likes = 0;
    private Integer dislikes = 0;

    /*  STATISTICS                          
     CALL FRIENDS FAGS   */
    //      PVP
    private Integer playerKills = 0;
    private Integer mobKills = 0;
    private Integer deaths = 0;

    //      MINING
    private Integer blocksBroken = 0;
    private Integer coalMined = 0;
    private Integer ironMined = 0;
    private Integer goldMined = 0;
    private Integer diamondMined = 0;
    private Integer emeraldMined = 0;

    //      CRAFTING
    public SocialPlayer(UUID UUID) {
        this.UUID = UUID;
    }

    /**
     * Sends a player the user's Social Profile.
     *
     * @param playerName The name of the player who will receive the message.
     */
    public void getPlayerStats(String playerName) {
        if (Bukkit.getPlayer(playerName) == null) {
            return;
        }
        Bukkit.getPlayer(playerName).sendMessage("");
        Bukkit.getPlayer(playerName).sendMessage(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + playerName + "'s Profile");
        Bukkit.getPlayer(playerName).sendMessage(ChatColor.GREEN + "▲" + " " + ChatColor.DARK_RED + "▼"
                + " ▌");
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(UUID);
    }
}
