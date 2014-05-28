/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpaccept implements CommandExecutor {

    CommandChatMenu tpa;

    public tpaccept() {
        tpa = new CommandChatMenu("TPAccept");
        tpa.setCommandUsage("/tpaccept");
        List<String> description = new ArrayList<String>();
        description.add("Teleport to the name of the player that you wish to teleport to.  This teleportation command runs on a timer, and will"
                + " not provide instant-trans.  Please wait a moment after your request has been sent.");
        tpa.setDescription(description);
        CommandChatMenu social = new CommandChatMenu("TPA");
        social.setCommandUsage("/tpa <name>");
        social.setQuickDescription("Teleport to the requested player.");
        tpa.addSubCommand(0, social);
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof Player) {
            Player p = (Player) s;

            return true;
        }
        s.sendMessage("You must be logged in to use this command.");
        return true;
    }
}
