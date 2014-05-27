/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Social;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_7_R3.ChatClickable;
import net.minecraft.server.v1_7_R3.ChatHoverable;
import net.minecraft.server.v1_7_R3.ChatMessage;
import net.minecraft.server.v1_7_R3.ChatModifier;
import net.minecraft.server.v1_7_R3.EnumClickAction;
import net.minecraft.server.v1_7_R3.EnumHoverAction;
import net.minecraft.server.v1_7_R3.IChatBaseComponent;
import net.minecraft.server.v1_7_R3.MinecraftServer;
import net.minecraft.server.v1_7_R3.PlayerList;
import org.bukkit.ChatColor;

public class Channel {

    private IChatBaseComponent base;
    private IChatBaseComponent like;
    private IChatBaseComponent dislike;
    private IChatBaseComponent channel;
    private IChatBaseComponent name;
    private IChatBaseComponent message;

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
                        return ChatColor.DARK_GRAY + "Local";
                    }

                    public String getChannelAbbreviation() {
                        return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "L";
                    }
                }, // Talk to people around you, get out more lazy ass.
        GUILD {
                    public String getFullChannelName() {
                        return ChatColor.DARK_GREEN + "Guild";
                    }

                    public String getChannelAbbreviation() {
                        return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "G";
                    }
                }
    }
    private Integer channelIdentificationNumber = 0;
    private List<String> playersInChannel = new ArrayList<String>();
    private String channelName;
    private String channelDescription;
    private ChannelType channelType = ChannelType.GLOBAL;

    /**
     * Creates a chat channel using a pre-existing template. Chat is handled
     * differently based on the ChannelType that you choose.
     *
     * @param ChannelType Create a channel with a special ChannelType.
     */
    public Channel(int id, String channelName) {
        this.channelName = channelName;
    }

    public void joinChannel(SocialPlayer player) {
        if (playersInChannel.contains(player.getPlayer().getName())) {
            return;
        }
        playersInChannel.add(player.getPlayer().getName());
        return;
    }

    public void leaveChannel(SocialPlayer player) {
        if (playersInChannel.contains(player.getPlayer().getName())) {
            playersInChannel.remove(player.getPlayer().getName());
            return;
        }
        return;
    }

    public void sendMessage(SocialPlayer player, String msg) {
        SocialManager.chatLog.add(msg);
        int chatId = SocialManager.chatLog.size();
        for (int i = 0; i < playersInChannel.size(); i++) {
            // Likes
            base = new ChatMessage("");
            like = new ChatMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "▲");
            dislike = new ChatMessage(ChatColor.RED + "" + ChatColor.BOLD + "▼");
            channel = new ChatMessage(ChatColor.DARK_GRAY + " ▌ " + channelName + " ▌ ");
            name = new ChatMessage(player.getPlayer().getName());
            message = new ChatMessage(ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + msg);

            like.setChatModifier(new ChatModifier());
            like.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.RUN_COMMAND, "/like " + player.getPlayer().getUniqueId() + " " + chatId));
            like.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_TEXT, new ChatMessage(ChatColor.GREEN + "Like" + ChatColor.WHITE + " this message!")));

            dislike.setChatModifier(new ChatModifier());
            dislike.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.RUN_COMMAND, "/dislike " + player.getPlayer().getUniqueId() + " " + chatId));
            dislike.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_TEXT, new ChatMessage(ChatColor.RED + "Dislike" + ChatColor.WHITE + " this message!")));

            name.setChatModifier(new ChatModifier());
            name.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.SUGGEST_COMMAND, "/w " + player.getPlayer().getName() + " "));
            name.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_TEXT, new ChatMessage(ChatColor.AQUA + "Data currently unavailable")));

            base.a(like);
            base.a(dislike);
            base.a(channel);
            base.a(name);
            base.a(message);
            PlayerList list = MinecraftServer.getServer().getPlayerList();
            list.getPlayer(playersInChannel.get(i)).sendMessage(base);
        }
    }
}
