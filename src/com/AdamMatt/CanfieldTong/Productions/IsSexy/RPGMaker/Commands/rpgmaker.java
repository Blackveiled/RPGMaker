/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AdamMatt.CanfieldTong.Productions.IsSexy.RPGMaker.Commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class rpgmaker implements CommandExecutor {

    private CommandChatMenu rpgmaker = new CommandChatMenu("RPGMaker");

    public rpgmaker() {
        rpgmaker.setCommandUsage("/rpgmaker");
        List<String> description = new ArrayList<String>();
        description.add("RPGMaker is a massive plugin created by " + ChatColor.AQUA + "Blackveiled (Adam Canfield)"
                + ChatColor.DARK_GRAY + " and " + ChatColor.AQUA + "Juicyzbox (Matt Tong)" + ChatColor.DARK_GRAY
                + ".  RPGMaker has multiple systems to utilize for your server, below is a list of commands which provide information"
                + " about those systems.  Thank you for using RPGMaker!");
        rpgmaker.setDescription(description);
        CommandChatMenu social = new CommandChatMenu("SocialManager");
        social.setCommandUsage("/socialmanager");
        social.setQuickDescription("Controls all social aspects of the server, chat, guilds, parties, and more.");
        rpgmaker.addSubCommand(0, social);
        CommandChatMenu accounts = new CommandChatMenu("AccountManager");
        accounts.setCommandUsage("/accountmanager");
        accounts.setQuickDescription("View and edit a player's character or game data.");
        rpgmaker.addSubCommand(1, accounts);
    }

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s.hasPermission("rpgmaker.admin")) {
            // Display Command Help Menu
            List<String> output = rpgmaker.getChatMenu();
            for (int i = 0; i < output.size(); i++) {
                s.sendMessage(output.get(i));
            }
            return true;
        }
        s.sendMessage(ChatColor.RED + "You cannot use this command.");
        return true;
    }
}
