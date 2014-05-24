/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Social;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public class Channel {

    // This channel can be configured to require permissions to chat in.
    public static enum ChannelType {

        /**
         * The global channel is heard everywhere, regardless the situation.
         * Perfect for making announcements, and you can disable chat to players
         * who lack the permission required to talk in Global chat.
         */
        GLOBAL {
                    public String getFullChannelName() {
                        return ChatColor.GOLD + "Global";
                    }

                    public String getChannelAbbreviation() {
                        return ChatColor.GOLD + "" + ChatColor.BOLD + "G";
                    }
                },
        /**
         * The lobby chat is a private chat channel, it mutes the outside world
         * and only directs sent messages to players inside of that lobby.
         */
        LOBBY {
                    public String getFullChannelName() {
                        return ChatColor.AQUA + "Lobby";
                    }

                    public String getChannelAbbreviation() {
                        return ChatColor.AQUA + "" + ChatColor.BOLD + "L";
                    }
                },
        /**
         * The match chat works the same as Lobby chat.
         */
        MATCH {
                    public String getFullChannelName() {
                        return ChatColor.GOLD + "Match";
                    }

                    public String getChannelAbbreviation() {
                        return ChatColor.GOLD + "" + ChatColor.BOLD + "M";
                    }
                },
        /**
         * A Support Channel where chat is limited by regular users, and server
         * helpers are able to provide effective support to server players
         * without dealing with troublemakers.
         */
        HELPME {
                    public String getFullChannelName() {
                        return ChatColor.GREEN + "HelpMe";
                    }

                    public String getChannelAbbreviation() {
                        return ChatColor.GREEN + "" + ChatColor.BOLD + "H" + ChatColor.DARK_RED + ChatColor.BOLD + "!";
                    }
                },
        /**
         * Users can privately report rulebreakers in-game and all
         * moderator-level staff members will be notified. Users will be
         * notified if no staff are currently available to provide support.
         */
        REPORT {
                    public String getFullChannelName() {
                        return ChatColor.DARK_RED + "Report";
                    }

                    public String getChannelAbbreviation() {
                        return ChatColor.DARK_RED + "" + ChatColor.BOLD + "R" + ChatColor.BLACK + ChatColor.BOLD + "!";
                    }
                },
        /**
         * Local chat. You hear only hear shit from people near you. Cool huh?!
         */
        LOCAL {
                    public String getFullChannelName() {
                        return ChatColor.DARK_GRAY + "Local Chat";
                    }

                    public String getChannelAbbreviation() {
                        return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "L";
                    }
                }, // Talk to people around you, get out more lazy ass.
    }
    private Integer channelIdentificationNumber = 0;
    private List<String> playersInChannel = new ArrayList<String>();
    private ChannelType channelType = ChannelType.GLOBAL;

    /**
     * Creates a chat channel using a pre-existing template. Chat is handled
     * differently based on the ChannelType that you choose.
     *
     * @param ChannelType Create a channel with a special ChannelType.
     */
    public Channel(int id, ChannelType channelType) {
        this.channelType = channelType;
    }
}
