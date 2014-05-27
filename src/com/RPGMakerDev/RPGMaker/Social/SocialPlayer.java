/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Social;

import com.RPGMakerDev.RPGMaker.RPGMaker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SocialPlayer {

    public static Map<UUID, SocialPlayer> socialPlayers = new HashMap<UUID, SocialPlayer>();
    public Integer cooldown = 0;

    private UUID UUID;
    private boolean onlineStatus = true;

    /*  REPUTATION                      */
    private Integer karma = 100;
    private double likes = 1;
    private double dislikes = 1;

    // This value will only go up to 60.  Once it reaches 60, Karma will go up by 10 and the minutesPlayed will be reset.
    private Integer minutesPlayed = 0;

    /*  STATISTICS TO ADD IN THE FUTURE
     /*                      
     //      COMBAT
     private Integer mobKills = 0;
     private Integer playerKills = 0;
     private double damageDealt = 0;
     private double healthHealed = 0;
     private Integer dungeonMobsKilled = 0;
     private Integer dungeonBossesKilled = 0;
     private Integer raidMobsKilled = 0;
     private Integer raidBossesKilled = 0;
     private Integer deaths = 0;

     //      MINING
     private Integer blocksBroken = 0;
     private Integer coalMined = 0;
     private Integer ironMined = 0;
     private Integer goldMined = 0;
     private Integer diamondMined = 0;
     private Integer emeraldMined = 0;
     */
    //      CRAFTING
    public SocialPlayer(UUID UUID) {
        this.UUID = UUID;
    }

    /**
     * Sends a player the user's Social Profile.
     *
     * @param playerName The name of the player who will receive the message.
     * @return List<String>
     */
    public List<String> getPlayerStats(String playerName) {
        List<String> list = new ArrayList<String>();
        list.add("");
        list.add(getPlayer().getDisplayName() + "'s Profile");
        if (isOnline()) {
            list.add("Online Status: " + ChatColor.GREEN + "Online");
        } else {
            list.add("Online Status: " + ChatColor.RED + "Offline");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "Reputation");
        return list;
    }

    /**
     * Gets the player's current Like : Dislike ratio as a double value.
     *
     * @return double value
     */
    public double getReputationRatio() {
        double ratio = 1.00;
        if (likes == 0) {
            if (dislikes == 0) {
                return ratio;
            }
        }
        ratio = likes / dislikes;
        return ratio;
    }

    /**
     * Save's the player's social data in the static hashmap.
     *
     * @return boolean
     */
    private static boolean storeSocialPlayer(UUID UUID) {
        if (!SocialPlayer.socialPlayers.containsKey(UUID)) {
            if (RPGMaker.debugMode()) {
                RPGMaker.debugMessage(ChatColor.AQUA + "Null: " + ChatColor.DARK_GRAY + UUID + ChatColor.AQUA + ".  Class: "
                        + ChatColor.DARK_GRAY + "SocialPlayer.storeSocialPlayer" + ChatColor.DARK_GRAY + " ▌" + ChatColor.RED
                        + "Your social stats failed to save in the server.  " + ChatColor.DARK_GRAY + "Your recent data may have been lost."
                        + "  Log off the server and log back in to fix this issue.");
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the player's currently stored SocialPlayer saved in the static
     * hashmap.
     *
     * @return SocialPlayer or null
     */
    public static SocialPlayer getStoredSocialPlayer(UUID UUID) {
        if (SocialPlayer.socialPlayers.containsKey(UUID)) {
            return SocialPlayer.socialPlayers.get(UUID);
        }
        if (RPGMaker.debugMode()) {
            RPGMaker.debugMessage(ChatColor.AQUA + "Null: " + ChatColor.DARK_GRAY + UUID + ChatColor.AQUA + ".  Class: "
                    + ChatColor.DARK_GRAY + "SocialPlayer.getStoredSocialPlayer" + ChatColor.DARK_GRAY + " ▌" + ChatColor.RED
                    + "Your social stats failed to load.  " + ChatColor.DARK_GRAY + "Your recent data cannot save."
                    + "  Log off the server and log back in to fix this issue.");
        }
        return null;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(UUID);
    }

    /**
     * Gives the player their hourly karma for reaching 60 minutes of game time.
     * This will only happen if they have one hour of game time.
     *
     * This method will NOT change their Karma unless they have 60 minutes of
     * gametime.
     */
    public void giveHourlyKarma() {
        if (RPGMaker.debugMode()) {
            getPlayer().sendMessage(RPGMaker.debugMessage("+10 Karma for " + ChatColor.GREEN + "one hour" + ChatColor.DARK_GRAY
                    + " of game time."));
        }
        if (minutesPlayed >= 60) {
            minutesPlayed = 0;
            karma = karma + 10;
        }
        return;
    }

    /**
     * Gets the Integer value of the player's karma.
     *
     * @return Integer value
     */
    public Integer getKarma() {
        return karma;
    }

    /**
     * Sets the player's karma to the inputted value.
     *
     * @param i
     */
    protected void setKarma(int i) {
        karma = i;
    }

    /**
     * Gets the Integer value of the player's karma.
     *
     * @return Integer value
     */
    public Integer getMinutesPlayed() {
        return minutesPlayed;
    }

    /**
     * Sets the minutes played for the player.
     *
     * @param i Integer
     */
    protected void setMinutesPlayed(int i) {
        minutesPlayed = i;
    }

    /**
     * Returns true if the player is online.
     *
     * @return boolean
     */
    public boolean isOnline() {
        if (onlineStatus) {
            return true;
        }
        return false;
    }

    public double getLikes() {
        return likes;
    }

    public double getDislikes() {
        return dislikes;
    }

    public void addLike() {
        likes++;
    }

    public void addDislike() {
        dislikes++;
    }

    public String getReputationName() {
        if (this.getReputationRatio() >= 1.60) {
            return ChatColor.GREEN + getPlayer().getName();
        }
        if (this.getReputationRatio() <= 1.59) {
            if (this.getReputationRatio() >= 1.10) {
                return ChatColor.DARK_GREEN + getPlayer().getName();
            }
        }

        if (this.getReputationRatio() <= 1.09) {
            if (this.getReputationRatio() >= 0.90) {
                return ChatColor.GRAY + getPlayer().getName();
            }
        }

        if (this.getReputationRatio() <= 0.89) {
            if (this.getReputationRatio() >= 0.60) {
                return ChatColor.RED + getPlayer().getName();
            }
        }

        if (this.getReputationRatio() <= 0.59) {
            return ChatColor.DARK_RED + getPlayer().getName();
        }
        return ChatColor.GRAY + getPlayer().getName();
    }

}
