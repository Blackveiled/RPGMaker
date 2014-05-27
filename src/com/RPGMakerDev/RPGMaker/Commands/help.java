/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class help implements CommandExecutor {

    private CommandChatMenu rpgmaker = new CommandChatMenu("Server Help");

    public help() {
        rpgmaker.setCommandUsage("/help");
        List<String> description = new ArrayList<String>();
        description.add("This server is powered by " + ChatColor.AQUA + "RPGMaker" + ChatColor.GRAY
                + ".  Here is a list of commands you can use to make your gameplay experience even better!");
        rpgmaker.setDescription(description);
        CommandChatMenu account = new CommandChatMenu("Account");
        account.setCommandUsage("/account");
        account.setQuickDescription("View information about your account and characters.");
        rpgmaker.addSubCommand(0, account);
        CommandChatMenu guild = new CommandChatMenu("Guild");
        guild.setCommandUsage("/guild");
        guild.setQuickDescription("View, edit, or create a guild on the server.");
        rpgmaker.addSubCommand(1, guild);
        CommandChatMenu chat = new CommandChatMenu("Chat");
        chat.setCommandUsage("/chat");
        chat.setQuickDescription("Control how chat is displayed to you on the server.");
        rpgmaker.addSubCommand(2, chat);
        CommandChatMenu guide = new CommandChatMenu("Guide");
        guide.setCommandUsage("/guide");
        guide.setQuickDescription("Learn about how the server works, use the guide!");
        rpgmaker.addSubCommand(3, guide);
    }

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        // Display Command Help Menu
        List<String> output = rpgmaker.getChatMenu();
        for (int i = 0; i < output.size(); i++) {
            s.sendMessage(output.get(i));
        }
        return true;
    }
}
